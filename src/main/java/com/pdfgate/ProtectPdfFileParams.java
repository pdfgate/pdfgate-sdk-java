package com.pdfgate;

/**
 * Parameters for requesting raw bytes from protectPdf.
 */
public final class ProtectPdfFileParams extends ProtectPdfParams {
    ProtectPdfFileParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return ProtectPdfParams.builder();
    }
}
