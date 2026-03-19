package com.pdfgate;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PdfGateAcceptanceTest {
  private static final byte[] WATERMARK_IMAGE = Base64.getDecoder().decode(
      "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR4nGNgYAAAAAMAASsJTYQAAAAASUVORK5CYII="
  );
  private static PdfGate client;
  private static String documentId;
  private static String documentIdWithForm;

  @BeforeAll
  static void beforeAll() throws IOException {
    setUpClient();
    setUpFiles();
  }

  static void setUpClient() {
    String apiKey = System.getenv("PDFGATE_API_KEY");

    Assumptions.assumeTrue(!Strings.isBlank(apiKey), "PDFGATE_API_KEY not set");
    Assumptions.assumeTrue(apiKey.startsWith("test_"), "PDFGATE_API_KEY must be a sandbox key");

    client = new PdfGate(apiKey);
  }

  static PdfGateDocument createDocument(String html) throws IOException {
    GeneratePdfParams params = GeneratePdfParams.builder()
        .html(html)
        .enableFormFields(true)
        .build();

    PdfGateDocument document = client.generatePdf(params);

    return document;
  }

  static void setUpFiles() throws IOException {
    PdfGateDocument document = createDocument("<html><body><h1>Hello, PDFGate!</h1></body></html>");
    documentId = document.getId();
    String htmlWithForm = "<form>"
        + "<input type='text' name='first_name' value='John'/>"
        + "<input type='text' name='last_name' value='Doe'/>"
        + "</form>";
    PdfGateDocument documentWithForm = createDocument(htmlWithForm);
    documentIdWithForm = documentWithForm.getId();

  }

  private void assertIsValidPdf(byte[] content) {
    Assertions.assertNotNull(content, "pdf bytes should be present");
    Assertions.assertTrue(content.length > 0, "pdf bytes should not be empty");
    String header = new String(content, 0, Math.min(content.length, 4),
        java.nio.charset.StandardCharsets.US_ASCII);
    Assertions.assertEquals("%PDF", header, "pdf bytes should start with %PDF");
  }

  @Test
  public void generatePdfWithJsonResponse() throws Exception {
    GeneratePdfParams params = GeneratePdfParams.builder()
        .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
        .build();

    PdfGateDocument document = client.generatePdf(params);
    Assertions.assertNotNull(document.getId(), "document id should be present");
    Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(),
        "document status should be present");
    Assertions.assertNotNull(document.getCreatedAt(), "document createdAt should be present");
  }

  @Test
  public void generatePdfSettingEnumParamsWithJsonResponse() throws Exception {
    GeneratePdfParams params = GeneratePdfParams.builder()
        .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
        .margin(new GeneratePdfParams.PdfPageMargin("10px", "10px", "10px", "10px"))
        .pageSizeType(GeneratePdfParams.PageSizeType.A3)
        .build();

    PdfGateDocument document = client.generatePdf(params);
    Assertions.assertNotNull(document.getId(), "document id should be present");
    Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(),
        "document status should be present");
    Assertions.assertNotNull(document.getCreatedAt(), "document createdAt should be present");
  }

  @Test
  public void flattenPdfByDocumentId() throws Exception {
    FlattenPdfParams flattenParams = FlattenPdfParams.builder()
        .documentId(documentId)
        .build();

    PdfGateDocument flattenedDocument = client.flattenPdf(flattenParams);
    Assertions.assertNotNull(flattenedDocument.getId(), "document id should be present");
    Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, flattenedDocument.getStatus(),
        "document status should be completed");
    Assertions.assertNotNull(flattenedDocument.getCreatedAt(),
        "document createdAt should be present");
    Assertions.assertEquals(documentId,
        flattenedDocument.getDerivedFrom().orElseThrow(AssertionError::new));
  }

  @Test
  public void getDocumentById() throws Exception {
    GetDocumentParams params = GetDocumentParams.builder()
        .documentId(documentId)
        .build();

    PdfGateDocument document = client.getDocument(params);
    Assertions.assertEquals(documentId, document.getId(), "document id should match");
    Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(),
        "document status should be completed");
    Assertions.assertNotNull(document.getCreatedAt(), "document createdAt should be present");
  }

  @Test
  public void getFileById() throws Exception {
    GetFileParams params = GetFileParams.builder()
        .documentId(documentId)
        .build();

    byte[] result = client.getFile(params);
    assertIsValidPdf(result);
  }

  @Test
  public void uploadFileWithMultipart() throws Exception {
    byte[] fileBytes = client.getFile(GetFileParams.builder()
        .documentId(documentId)
        .build());

    UploadFileParams params = UploadFileParams.builder()
        .file(new FileParam("upload.pdf", fileBytes, "application/pdf"))
        .build();

    PdfGateDocument document = client.uploadFile(params);
    Assertions.assertNotNull(document.getId(), "document id should be present");
    Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(),
        "document status should be completed");
    Assertions.assertNotNull(document.getCreatedAt(), "document createdAt should be present");
  }

  @Test
  public void getFileByIdMissing() {
    String missingDocumentId = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    GetFileParams params = GetFileParams.builder()
        .documentId(missingDocumentId)
        .build();

    PdfGateException exception =
        Assertions.assertThrows(PdfGateException.class, () -> client.getFile(params));
    Assertions.assertEquals(404, exception.getStatusCode(), "status code should be 404");
  }

  @Test
  public void getDocumentByIdMissing() {
    String missingDocumentId = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    GetDocumentParams params = GetDocumentParams.builder()
        .documentId(missingDocumentId)
        .build();

    PdfGateException exception =
        Assertions.assertThrows(PdfGateException.class, () -> client.getDocument(params));
    Assertions.assertEquals(404, exception.getStatusCode(), "status code should be 404");
  }

  /**
   * Extracts form data using a stored document id.
   */
  @Test
  public void extractPdfFormDataByDocumentId() throws Exception {
    ExtractPdfFormDataParams extractParams = ExtractPdfFormDataParams.builder()
        .documentId(documentIdWithForm)
        .build();

    JsonObject response = client.extractPdfFormData(extractParams);
    Assertions.assertEquals("John", response.get("first_name").getAsString());
    Assertions.assertEquals("Doe", response.get("last_name").getAsString());
  }

  @Test
  public void watermarkPdfByDocumentIdWithJsonResponse() throws Exception {
    WatermarkPdfParams params = WatermarkPdfParams.builder()
        .documentId(documentId)
        .type(WatermarkPdfParams.WatermarkType.TEXT)
        .text("CONFIDENTIAL")
        .build();

    PdfGateDocument document = client.watermarkPdf(params);
    Assertions.assertNotNull(document.getId(), "document id should be present");
    Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(),
        "document status should be completed");
    Assertions.assertNotNull(document.getCreatedAt(), "document createdAt should be present");
  }

  @Test
  public void watermarkPdfWithImageWatermark() throws Exception {
    WatermarkPdfParams params = WatermarkPdfParams.builder()
        .documentId(documentId)
        .type(WatermarkPdfParams.WatermarkType.IMAGE)
        .watermark(new FileParam("watermark.png", WATERMARK_IMAGE, "image/png"))
        .build();

    PdfGateDocument document = client.watermarkPdf(params);
    Assertions.assertNotNull(document.getId(), "document id should be present");
    Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(),
        "document status should be completed");
    Assertions.assertNotNull(document.getCreatedAt(), "document createdAt should be present");
    Assertions.assertEquals(documentId,
        document.getDerivedFrom().orElseThrow(AssertionError::new));
  }

  @Test
  public void protectPdfByDocumentIdWithJsonResponse() throws Exception {
    ProtectPdfParams params = ProtectPdfParams.builder()
        .documentId(documentId)
        .userPassword(UUID.randomUUID().toString())
        .ownerPassword(UUID.randomUUID().toString())
        .build();

    PdfGateDocument document = client.protectPdf(params);
    Assertions.assertNotNull(document.getId(), "document id should be present");
    Assertions.assertNotEquals(documentId, document.getId(), "document id should not match source");
    Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(),
        "document status should be completed");
  }

  @Test
  public void compressPdfByDocumentIdWithJsonResponse() throws Exception {
    CompressPdfParams params = CompressPdfParams.builder()
        .documentId(documentId)
        .build();

    PdfGateDocument document = client.compressPdf(params);
    Assertions.assertNotNull(document.getId(), "document id should be present");
    Assertions.assertNotEquals(documentId, document.getId(), "document id should not match source");
    Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(),
        "document status should be completed");
    Assertions.assertEquals(PdfGateDocument.DocumentType.COMPRESSED, document.getType(),
        "document type should be compressed");
  }

}
