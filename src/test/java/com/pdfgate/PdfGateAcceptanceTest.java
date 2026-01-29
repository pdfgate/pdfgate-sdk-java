package com.pdfgate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PdfGateAcceptanceTest {
    private static PdfGate client;
    private static String documentId;

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

    static PdfGateDocument createDocument() throws IOException {
        GeneratePdfJsonParams params = GeneratePdfParams.builder()
                .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                .buildJson();

        PdfGateDocument document = client.generatePdf(params);

        return document;
    }

    static void setUpFiles() throws IOException {
        PdfGateDocument document = createDocument();
        documentId = document.getId();
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
        Assertions.assertNotNull(result, "pdf bytes should be present");
        Assertions.assertTrue(result.length > 0, "pdf bytes should not be empty");
        String header = new String(result, 0, Math.min(result.length, 4), java.nio.charset.StandardCharsets.US_ASCII);
        Assertions.assertEquals("%PDF", header, "pdf bytes should start with %PDF");
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
}
