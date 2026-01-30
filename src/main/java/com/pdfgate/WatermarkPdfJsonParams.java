package com.pdfgate;

/**
 * Parameters for requesting a JSON document response from watermarkPdf.
 */
public final class WatermarkPdfJsonParams extends WatermarkPdfParams {
    WatermarkPdfJsonParams(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return WatermarkPdfParams.builder();
    }
}
