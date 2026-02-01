package com.pdfgate;

/**
 * Parameters for requesting a JSON document response from
 * {@link PdfGate#watermarkPdf(WatermarkPdfJsonParams)}.
 *
 * <p>Build with {@link WatermarkPdfParams.Builder#buildWithJsonResponse()} to request
 * {@link PdfGateDocument} metadata instead of raw bytes.
 */
public final class WatermarkPdfJsonParams extends WatermarkPdfParams {
  WatermarkPdfJsonParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for watermark PDF JSON response parameters.
   */
  public static Builder builder() {
    return WatermarkPdfParams.builder();
  }
}
