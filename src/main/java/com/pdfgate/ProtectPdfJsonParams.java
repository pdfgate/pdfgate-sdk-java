package com.pdfgate;

/**
 * Parameters for requesting a JSON document response from
 * {@link PdfGate#protectPdf(ProtectPdfJsonParams)}.
 *
 * <p>Build with {@link ProtectPdfParams.Builder#buildWithJsonResponse()} to request
 * {@link PdfGateDocument} metadata instead of raw bytes.
 */
public final class ProtectPdfJsonParams extends ProtectPdfParams {
  ProtectPdfJsonParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for protect PDF JSON response parameters.
   */
  public static Builder builder() {
    return ProtectPdfParams.builder();
  }
}
