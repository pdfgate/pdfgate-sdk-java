package com.pdfgate;

/**
 * Parameters for requesting raw PDF bytes from {@link PdfGate#compressPdf(CompressPdfFileParams)}.
 *
 * <p>Build with {@link CompressPdfParams.Builder#buildWithFileResponse()} to request a file stream
 * instead of JSON metadata.
 */
public final class CompressPdfFileParams extends CompressPdfParams {
  CompressPdfFileParams(Builder builder) {
    super(builder);
  }

  /**
   * Creates a new builder for compress PDF file response parameters.
   */
  public static Builder builder() {
    return CompressPdfParams.builder();
  }
}
