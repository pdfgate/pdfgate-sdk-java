package com.pdfgate;

/**
 * Parameters for requesting raw PDF bytes from {@link PdfGate#protectPdf(ProtectPdfFileParams)}.
 *
 * <p>Build with {@link ProtectPdfParams.Builder#buildWithFileResponse()} to request a file stream
 * instead of JSON metadata.
 */
public final class ProtectPdfFileParams extends ProtectPdfParams {
  /**
   * Initializes file response parameters from the builder.
   *
   * @param builder builder with configured values.
   */
  ProtectPdfFileParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for protect PDF file response parameters.
   *
   * @return the builder for protect PDF file response parameters.
   */
  public static Builder builder() {
    return ProtectPdfParams.builder();
  }
}
