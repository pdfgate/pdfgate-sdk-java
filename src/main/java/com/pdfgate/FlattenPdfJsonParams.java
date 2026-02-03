package com.pdfgate;

/**
 * Parameters for requesting a JSON document response from
 * {@link PdfGate#flattenPdf(FlattenPdfJsonParams)}.
 *
 * <p>Build with {@link FlattenPdfParams.Builder#buildWithJsonResponse()} to request
 * {@link PdfGateDocument} metadata instead of raw bytes.
 */
public final class FlattenPdfJsonParams extends FlattenPdfParams {
  FlattenPdfJsonParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for flatten PDF JSON parameters.
   */
  public static Builder builder() {
    return FlattenPdfParams.builder();
  }
}
