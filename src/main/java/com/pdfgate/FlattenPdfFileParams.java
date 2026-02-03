package com.pdfgate;

/**
 * Parameters for requesting raw PDF bytes from {@link PdfGate#flattenPdf(FlattenPdfFileParams)}.
 *
 * <p>Build with {@link FlattenPdfParams.Builder#buildWithFileResponse()} to request a file stream
 * instead of JSON metadata.
 */
public final class FlattenPdfFileParams extends FlattenPdfParams {
  /**
   * Initializes file response parameters from the builder.
   *
   * @param builder builder with configured values.
   */
  FlattenPdfFileParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for flatten PDF file response parameters.
   *
   * @return the builder for flatten PDF file response parameters.
   */
  public static Builder builder() {
    return FlattenPdfParams.builder();
  }
}
