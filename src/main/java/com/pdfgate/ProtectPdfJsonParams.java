package com.pdfgate;

/**
 * Parameters for requesting a JSON document response from protectPdf.
 */
public final class ProtectPdfJsonParams extends ProtectPdfParams {
    ProtectPdfJsonParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return ProtectPdfParams.builder();
    }
}
