package com.pdfgate;

/**
 * Parameters for requesting a JSON document response from generatePdf.
 */
public final class GeneratePdfJsonParams extends GeneratePdfParams {
    GeneratePdfJsonParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return GeneratePdfParams.builder();
    }
}
