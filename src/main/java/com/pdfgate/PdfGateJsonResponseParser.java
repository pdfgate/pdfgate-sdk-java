package com.pdfgate;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PdfGateJsonResponseParser extends PdfGateResponseParser {

    public PdfGateJsonResponseParser(PDFGateCallback<PdfGateDocument> callback) {
        this.callback = callback;
    }

    @Override public PdfGateDocument parseResponse(ResponseBody responseBody) throws IOException {
        String responseJson = responseBody == null ? "" : responseBody.string();
        PdfGateDocument document = PdfGateJson.gson().fromJson(responseJson, PdfGateDocument.class);

        return document;
    }
}
