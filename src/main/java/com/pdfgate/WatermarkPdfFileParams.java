package com.pdfgate;

/**
 * Parameters for requesting raw bytes from watermarkPdf.
 */
public final class WatermarkPdfFileParams extends WatermarkPdfParams {
    WatermarkPdfFileParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return WatermarkPdfParams.builder();
    }
}
