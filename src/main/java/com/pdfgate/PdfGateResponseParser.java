package com.pdfgate;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

final class PdfGateResponseParser {
    private PdfGateResponseParser() {}

    static PdfGateDocument parseJson(Response response) throws IOException {
        ensureSuccess(response);
        ResponseBody body = response.body();
        String json = body == null ? "" : body.string();
        return PdfGateJson.gson().fromJson(json, PdfGateDocument.class);
    }

    static byte[] parseBytes(Response response) throws IOException {
        ensureSuccess(response);
        ResponseBody body = response.body();
        return body == null ? new byte[0] : body.bytes();
    }

    static void ensureSuccess(Response response) throws IOException {
        if (!response.isSuccessful()) {
            throw PdfGateException.fromResponse(response);
        }
    }
}
