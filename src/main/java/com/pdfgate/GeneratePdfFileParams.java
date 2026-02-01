package com.pdfgate;

/**
 * Parameters for requesting raw PDF bytes from {@link PdfGate#generatePdf(GeneratePdfFileParams)}.
 *
 * <p>Build with {@link GeneratePdfParams.Builder#buildWithFileResponse()} to ensure the
 * API returns a file stream instead of JSON metadata.
 */
public final class GeneratePdfFileParams extends GeneratePdfParams {
  GeneratePdfFileParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for generate PDF file response parameters.
   */
  public static Builder builder() {
    return GeneratePdfParams.builder();
  }
}
