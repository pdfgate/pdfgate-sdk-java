package com.fer;

import com.pdfgate.GeneratePdfFileParams;
import com.pdfgate.GeneratePdfParams;
import com.pdfgate.PdfGate;
import com.pdfgate.PdfGateException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Demonstrates a basic PDF generation request using the PDFGate SDK.
 */
public final class Example {
  private Example() {
    // Utility class.
  }

  /**
   * Runs a sample request to generate a PDF and saves it to disk.
   *
   * @param args command-line arguments (unused).
   */
  public static void main(String[] args) {
    String apiKey = System.getenv("PDFGATE_API_KEY");
    if (apiKey == null || apiKey.isBlank()) {
      System.err.println("Set PDFGATE_API_KEY to run this example.");
      return;
    }

    PdfGate client = new PdfGate(apiKey);
    GeneratePdfFileParams params = GeneratePdfParams.builder()
        .html("<html><body><h1>Hello from PDFGate!</h1></body></html>")
        .buildWithFileResponse();

    try {
      byte[] pdfBytes = client.generatePdf(params);
      Path outputPath = Path.of("output.pdf");
      Files.write(outputPath, pdfBytes);
      System.out.println("Saved PDF to " + outputPath.toAbsolutePath());
    } catch (PdfGateException e) {
      System.err.println("PDFGate error (status " + e.getStatusCode() + "): " + e.getMessage());
    } catch (IOException e) {
      System.err.println("I/O error: " + e.getMessage());
    }
  }
}
