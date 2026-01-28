package com.pdfgate;

/**
 * Parameters for requesting raw bytes from flattenPdf.
 */
public final class FlattenPdfBytesParams extends FlattenPdfParams {
    FlattenPdfBytesParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return FlattenPdfParams.builder();
    }
}
