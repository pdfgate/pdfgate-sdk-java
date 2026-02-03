package com.pdfgate;

/**
 * Parameters for requesting a JSON document response from
 * {@link PdfGate#compressPdf(CompressPdfJsonParams)}.
 *
 * <p>Build with {@link CompressPdfParams.Builder#buildWithJsonResponse()} to request
 * {@link PdfGateDocument} metadata instead of raw bytes.
 */
public final class CompressPdfJsonParams extends CompressPdfParams {
  /**
   * Initializes JSON response parameters from the builder.
   *
   * @param builder builder with configured values.
   */
  CompressPdfJsonParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for compress PDF JSON parameters.
   *
   * @return the builder for compress PDF JSON parameters.
   */
  public static Builder builder() {
    return CompressPdfParams.builder();
  }
}
