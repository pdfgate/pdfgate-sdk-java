package com.pdfgate;

/**
 * Parameters for requesting raw bytes from flattenPdf.
 */
public final class FlattenPdfFileParams extends FlattenPdfParams {
    FlattenPdfFileParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return FlattenPdfParams.builder();
    }
}
