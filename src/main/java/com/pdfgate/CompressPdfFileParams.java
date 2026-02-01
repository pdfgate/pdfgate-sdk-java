package com.pdfgate;

/**
 * Parameters for requesting raw bytes from compressPdf.
 */
public final class CompressPdfFileParams extends CompressPdfParams {
  CompressPdfFileParams(Builder builder) {
    super(builder);
  }

  public static Builder builder() {
    return CompressPdfParams.builder();
  }
}
