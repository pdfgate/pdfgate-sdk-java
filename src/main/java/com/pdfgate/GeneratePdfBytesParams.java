package com.pdfgate;

/**
 * Parameters for requesting raw bytes from generatePdf.
 */
public final class GeneratePdfBytesParams extends GeneratePdfParams {
    GeneratePdfBytesParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return GeneratePdfParams.builder().jsonResponse(false);
    }
}
