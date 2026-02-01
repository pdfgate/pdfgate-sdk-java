package com.pdfgate;

/**
 * Parameters for requesting a JSON document response from compressPdf.
 */
public final class CompressPdfJsonParams extends CompressPdfParams {
  CompressPdfJsonParams(Builder builder) {
    super(builder);
  }

  public static Builder builder() {
    return CompressPdfParams.builder();
  }
}
