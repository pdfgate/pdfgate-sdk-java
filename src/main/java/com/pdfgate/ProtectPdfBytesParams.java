package com.pdfgate;

/**
 * Parameters for requesting raw bytes from protectPdf.
 */
public final class ProtectPdfBytesParams extends ProtectPdfParams {
    ProtectPdfBytesParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return ProtectPdfParams.builder();
    }
}
