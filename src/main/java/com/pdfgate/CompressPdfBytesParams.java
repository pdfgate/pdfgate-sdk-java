package com.pdfgate;

/**
 * Parameters for requesting raw bytes from compressPdf.
 */
public final class CompressPdfBytesParams extends CompressPdfParams {
    CompressPdfBytesParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return CompressPdfParams.builder();
    }
}
