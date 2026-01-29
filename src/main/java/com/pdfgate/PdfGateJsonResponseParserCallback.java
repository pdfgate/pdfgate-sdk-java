package com.pdfgate;

import okhttp3.Response;

import java.io.IOException;

public class PdfGateJsonResponseParserCallback extends PdfGateResponseParserCallback {

    public PdfGateJsonResponseParserCallback(PDFGateCallback<PdfGateDocument> callback) {
        this.callback = callback;
    }

    @Override public PdfGateDocument parseResponse(Response response) throws IOException {
        return PdfGateResponseParser.parseJson(response);
    }
}
