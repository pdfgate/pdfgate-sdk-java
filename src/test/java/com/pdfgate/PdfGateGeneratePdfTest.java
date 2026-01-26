package com.pdfgate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PdfGateGeneratePdfTest {
    private static PdfGate client;

    @BeforeAll
    static void setUpApiKey() {
        String apiKey = System.getenv("PDFGATE_API_KEY");

        Assumptions.assumeTrue(apiKey != null && !apiKey.isBlank(), "PDFGATE_API_KEY not set");
        Assumptions.assumeTrue(apiKey.startsWith("test_"), "PDFGATE_API_KEY must be a sandbox key");

        client = new PdfGate(apiKey);
    }

    @Test
    public void generatePdfWithJsonResponse() throws Exception {
        GeneratePdfParams params = GeneratePdfParams.builder()
                .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                .jsonResponse(true)
                .build();

        PdfGateDocument response = client.generatePdf(params);

        Assertions.assertNotNull(response.getId(), "document id should be present");
        Assertions.assertEquals(PdfGateDocument.DocumentStatus.COMPLETED, response.getStatus(), "document status should be present");
        Assertions.assertNotNull(response.getCreatedAt(), "document createdAt should be present");
    }
}
