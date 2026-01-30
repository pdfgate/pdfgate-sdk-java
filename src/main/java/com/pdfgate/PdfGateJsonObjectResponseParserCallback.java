package com.pdfgate;

import java.io.IOException;

import com.google.gson.JsonObject;
import okhttp3.Response;

/**
 * Parses JSON responses into {@link JsonObject} instances.
 */
public class PdfGateJsonObjectResponseParserCallback extends PdfGateResponseParserCallback<JsonObject> {

    /**
     * Creates a response parser callback for JSON object payloads.
     */
    public PdfGateJsonObjectResponseParserCallback(PDFGateCallback<JsonObject> callback) {
        this.callback = callback;
    }

    @Override
    public JsonObject parseResponse(Response response) throws IOException {
        return PdfGateResponseParser.parseJsonObject(response);
    }
}
