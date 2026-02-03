package com.pdfgate;

/**
 * Parameters for requesting a JSON document response from
 * {@link PdfGate#generatePdf(GeneratePdfJsonParams)}.
 *
 * <p>Build with {@link GeneratePdfParams.Builder#buildWithJsonResponse()} to request a
 * {@link PdfGateDocument} instead of raw bytes.
 */
public final class GeneratePdfJsonParams extends GeneratePdfParams {
  /**
   * Initializes JSON response parameters from the builder.
   *
   * @param builder builder with configured values.
   */
  GeneratePdfJsonParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for generate PDF JSON response parameters.
   *
   * @return the builder for generate PDF JSON response parameters.
   */
  public static Builder builder() {
    return GeneratePdfParams.builder();
  }
}
