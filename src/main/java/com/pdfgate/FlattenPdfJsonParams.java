package com.pdfgate;

/**
 * Parameters for requesting a JSON document response from flattenPdf.
 */
public final class FlattenPdfJsonParams extends FlattenPdfParams {
    FlattenPdfJsonParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return FlattenPdfParams.builder();
    }
}
