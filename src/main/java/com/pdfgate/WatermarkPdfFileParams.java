package com.pdfgate;

/**
 * Parameters for requesting raw PDF bytes from {@link PdfGate#watermarkPdf(WatermarkPdfFileParams)}.
 *
 * <p>Build with {@link WatermarkPdfParams.Builder#buildWithFileResponse()} to request a file stream
 * instead of JSON metadata.
 */
public final class WatermarkPdfFileParams extends WatermarkPdfParams {
  /**
   * Initializes file response parameters from the builder.
   *
   * @param builder builder with configured values.
   */
  WatermarkPdfFileParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for watermark PDF file response parameters.
   *
   * @return the builder for watermark PDF file response parameters.
   */
  public static Builder builder() {
    return WatermarkPdfParams.builder();
  }
}
