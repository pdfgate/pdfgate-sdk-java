package com.pdfgate;

import okhttp3.ResponseBody;

import java.io.IOException;

public class PdfGateJsonResponseParser extends PdfGateResponseParser {

    public PdfGateJsonResponseParser(PDFGateCallback<PdfGateDocument> callback) {
        this.callback = callback;
    }

    @Override public PdfGateDocument parseResponse(ResponseBody responseBody) throws IOException {
        String responseJson = responseBody == null ? "" : responseBody.string();

        return PdfGateJson.gson().fromJson(responseJson, PdfGateDocument.class);
    }
}
