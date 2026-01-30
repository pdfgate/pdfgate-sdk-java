package com.pdfgate;

/**
 * Parameters for requesting raw bytes from watermarkPdf.
 */
public final class WatermarkPdfBytesParams extends WatermarkPdfParams {
    WatermarkPdfBytesParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return WatermarkPdfParams.builder();
    }
}
