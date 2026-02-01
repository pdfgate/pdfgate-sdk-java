package com.pdfgate;

/**
 * Parameters for requesting raw bytes from generatePdf.
 */
public final class GeneratePdfFileParams extends GeneratePdfParams {
    GeneratePdfFileParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return GeneratePdfParams.builder();
    }
}
