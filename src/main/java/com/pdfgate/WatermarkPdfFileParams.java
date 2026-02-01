package com.pdfgate;

/**
 * Parameters for requesting raw PDF bytes from {@link PdfGate#watermarkPdf(WatermarkPdfFileParams)}.
 *
 * <p>Build with {@link WatermarkPdfParams.Builder#buildWithFileResponse()} to request a file stream
 * instead of JSON metadata.
 */
public final class WatermarkPdfFileParams extends WatermarkPdfParams {
  WatermarkPdfFileParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for watermark PDF file response parameters.
   */
  public static Builder builder() {
    return WatermarkPdfParams.builder();
  }
}
