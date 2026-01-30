package com.pdfgate;

import com.google.gson.JsonObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class PdfGateAcceptanceTest {
    private static PdfGate client;
    private static String documentId;
    private static long documentSize;
    private static String documentIdWithForm;
    private static byte[] fileWithForm;
    private static final byte[] WATERMARK_IMAGE = Base64.getDecoder().decode(
            "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR4nGNgYAAAAAMAASsJTYQAAAAASUVORK5CYII="
    );

    @BeforeAll
    static void beforeAll() throws IOException {
        setUpClient();
        setUpFiles();
    }

    static void setUpClient() {
        String apiKey = System.getenv("PDFGATE_API_KEY");

        Assumptions.assumeTrue(apiKey != null && !apiKey.isBlank(), "PDFGATE_API_KEY not set");
        Assumptions.assumeTrue(apiKey.startsWith("test_"), "PDFGATE_API_KEY must be a sandbox key");

        client = new PdfGate(apiKey);
    }

    static PdfGateDocument createDocument(String html) throws IOException {
        GeneratePdfJsonParams params = GeneratePdfParams.builder()
                .html(html)
                .enableFormFields(true)
                .buildJson();

        PdfGateDocument document = client.generatePdf(params);

        return document;
    }

    static void setUpFiles() throws IOException {
        PdfGateDocument document = createDocument("<html><body><h1>Hello, PDFGate!</h1></body></html>");
        documentId = document.getId();
        Long size = document.getSize();
        Assumptions.assumeTrue(size != null && size > 0, "document size should be present");
        documentSize = size;

        String htmlWithForm = "<form>"
                + "<input type='text' name='first_name' value='John'/>"
                + "<input type='text' name='last_name' value='Doe'/>"
                + "</form>";
        PdfGateDocument documentWithForm = createDocument(htmlWithForm);
        documentIdWithForm = documentWithForm.getId();

        GeneratePdfBytesParams params = GeneratePdfParams.builder()
                .html(htmlWithForm)
                .enableFormFields(true)
                .buildBytes();

        fileWithForm = client.generatePdf(params);
    }

    private void assertIsValidPdf(byte[] content) {
        Assertions.assertNotNull(content, "pdf bytes should be present");
        Assertions.assertTrue(content.length > 0, "pdf bytes should not be empty");
        String header = new String(content, 0, Math.min(content.length, 4), java.nio.charset.StandardCharsets.US_ASCII);
        Assertions.assertEquals("%PDF", header, "pdf bytes should start with %PDF");
    }

    @Test
    public void generatePdfWithJsonResponse() throws Exception {
        GeneratePdfJsonParams params = GeneratePdfParams.builder()
                .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                .buildJson();

        PdfGateDocument document = client.generatePdf(params);
        Assertions.assertNotNull(document.getId(), "document id should be present");
        Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(), "document status should be present");
        Assertions.assertNotNull(document.getCreatedAt(), "document createdAt should be present");
    }

    @Test
    public void generatePdfWithBytesResponse() throws Exception {
        GeneratePdfBytesParams params = GeneratePdfParams.builder()
                .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                .buildBytes();

        byte[] result = client.generatePdf(params);
        assertIsValidPdf(result);
    }

    @Test
    public void flattenPdfByFile() throws Exception {
        GeneratePdfBytesParams generateParams = GeneratePdfParams.builder()
                .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                .buildBytes();
        byte[] pdfBytes = client.generatePdf(generateParams);

        FileParam fileParam = new FileParam("input.pdf", pdfBytes, "application/pdf");
        FlattenPdfJsonParams flattenParams = FlattenPdfJsonParams.builder()
                .file(fileParam)
                .buildJson();

        PdfGateDocument flattenedDocument = client.flattenPdf(flattenParams);
        Assertions.assertNotNull(flattenedDocument.getId(), "document id should be present");
        Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, flattenedDocument.getStatus(), "document status should be completed");
        Assertions.assertNotNull(flattenedDocument.getCreatedAt(), "document createdAt should be present");
    }

    @Test
    public void flattenPdfByDocumentId() throws Exception {
        FlattenPdfJsonParams flattenParams = FlattenPdfJsonParams.builder()
                .documentId(documentId)
                .buildJson();

        PdfGateDocument flattenedDocument = client.flattenPdf(flattenParams);
        Assertions.assertNotNull(flattenedDocument.getId(), "document id should be present");
        Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, flattenedDocument.getStatus(), "document status should be completed");
        Assertions.assertNotNull(flattenedDocument.getCreatedAt(), "document createdAt should be present");
        Assertions.assertEquals(documentId, flattenedDocument.getDerivedFrom().orElseThrow());
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

    /**
     * Extracts form data using a PDF file upload.
     */
    @Test
    public void extractPdfFormDataByFile() throws Exception {
        ExtractPdfFormDataParams extractParams = ExtractPdfFormDataParams.builder()
                .file(new FileParam("input.pdf", fileWithForm, "application/pdf"))
                .build();

        JsonObject response = client.extractPdfFormData(extractParams);

        Assertions.assertEquals("John", response.get("first_name").getAsString());
        Assertions.assertEquals("Doe", response.get("last_name").getAsString());
    }

    @Test
    public void watermarkPdfByFileWithJsonResponse() throws Exception {
        WatermarkPdfJsonParams params = WatermarkPdfParams.builder()
                .file(new FileParam("input.pdf", fileWithForm, "application/pdf"))
                .type(WatermarkPdfParams.WatermarkType.TEXT)
                .text("CONFIDENTIAL")
                .buildJson();

        PdfGateDocument document = client.watermarkPdf(params);
        Assertions.assertNotNull(document.getId(), "document id should be present");
        Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(), "document status should be completed");
        Assertions.assertNotNull(document.getCreatedAt(), "document createdAt should be present");
    }

    @Test
    public void watermarkPdfByDocumentIdWithFileResponse() throws Exception {
        WatermarkPdfBytesParams params = WatermarkPdfParams.builder()
                .documentId(documentId)
                .type(WatermarkPdfParams.WatermarkType.TEXT)
                .text("CONFIDENTIAL")
                .buildBytes();

        byte[] watermarkedFile = client.watermarkPdf(params);
        assertIsValidPdf(watermarkedFile);
    }

    @Test
    public void watermarkPdfWithImageWatermark() throws Exception {
        WatermarkPdfJsonParams params = WatermarkPdfParams.builder()
                .documentId(documentId)
                .type(WatermarkPdfParams.WatermarkType.IMAGE)
                .watermark(new FileParam("watermark.png", WATERMARK_IMAGE, "image/png"))
                .buildJson();

        PdfGateDocument document = client.watermarkPdf(params);
        Assertions.assertNotNull(document.getId(), "document id should be present");
        Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(), "document status should be completed");
        Assertions.assertNotNull(document.getCreatedAt(), "document createdAt should be present");
        Assertions.assertEquals(documentId, document.getDerivedFrom().orElseThrow());
    }

    @Test
    public void protectPdfByDocumentIdWithJsonResponse() throws Exception {
        ProtectPdfJsonParams params = ProtectPdfParams.builder()
                .documentId(documentId)
                .userPassword(UUID.randomUUID().toString())
                .ownerPassword(UUID.randomUUID().toString())
                .buildJson();

        PdfGateDocument document = client.protectPdf(params);
        Assertions.assertNotNull(document.getId(), "document id should be present");
        Assertions.assertNotEquals(documentId, document.getId(), "document id should not match source");
        Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(), "document status should be completed");
    }

    @Test
    public void protectPdfByFileWithFileResponse() throws Exception {
        String userPassword = UUID.randomUUID().toString();
        String ownerPassword = UUID.randomUUID().toString();
        ProtectPdfBytesParams params = ProtectPdfParams.builder()
                .file(new FileParam("input.pdf", fileWithForm, "application/pdf"))
                .userPassword(userPassword)
                .ownerPassword(ownerPassword)
                .buildBytes();

        byte[] protectedFile = client.protectPdf(params);
        assertIsValidPdf(protectedFile);
        Assertions.assertThrows(InvalidPasswordException.class, () -> {
            try (PDDocument ignored = PDDocument.load(protectedFile)) {
                // Should not reach here because the PDF is encrypted.
            }
        });
        try (PDDocument document = PDDocument.load(protectedFile, userPassword)) {
            Assertions.assertTrue(document.isEncrypted(), "pdf should be encrypted");
        }
    }

    @Test
    public void compressPdfByFileWithJsonResponse() throws Exception {
        CompressPdfJsonParams params = CompressPdfParams.builder()
                .file(new FileParam("input.pdf", fileWithForm, "application/pdf"))
                .buildJson();

        PdfGateDocument document = client.compressPdf(params);
        Assertions.assertNotNull(document.getId(), "document id should be present");
        Assertions.assertNotEquals(documentId, document.getId(), "document id should not match source");
        Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, document.getStatus(), "document status should be completed");
        Assertions.assertEquals(PdfGateDocument.DocumentType.COMPRESSED, document.getType(), "document type should be compressed");
    }

    @Test
    public void compressPdfByDocumentIdWithBytesResponse() throws Exception {
        CompressPdfBytesParams params = CompressPdfParams.builder()
                .documentId(documentId)
                .buildBytes();

        byte[] compressedFile = client.compressPdf(params);
        assertIsValidPdf(compressedFile);
        Assertions.assertTrue(
                compressedFile.length < documentSize,
                "compressed pdf should be smaller than the original"
        );
    }

}
