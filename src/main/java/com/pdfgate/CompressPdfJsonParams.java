package com.pdfgate;

/**
 * Parameters for requesting a JSON document response from
 * {@link PdfGate#compressPdf(CompressPdfJsonParams)}.
 *
 * <p>Build with {@link CompressPdfParams.Builder#buildWithJsonResponse()} to request
 * {@link PdfGateDocument} metadata instead of raw bytes.
 */
public final class CompressPdfJsonParams extends CompressPdfParams {
  CompressPdfJsonParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for compress PDF JSON response parameters.
   */
  public static Builder builder() {
    return CompressPdfParams.builder();
  }
}
