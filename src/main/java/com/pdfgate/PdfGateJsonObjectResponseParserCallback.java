package com.pdfgate;

import com.google.gson.JsonObject;
import java.io.IOException;
import okhttp3.Response;

/**
 * Parses JSON responses into {@link JsonObject} instances.
 */
public class PdfGateJsonObjectResponseParserCallback
    extends PdfGateResponseParserCallback<JsonObject> {

  /**
   * Creates a response parser callback for JSON object payloads.
   */
  public PdfGateJsonObjectResponseParserCallback(PdfGateCallback<JsonObject> callback) {
    this.callback = callback;
  }

  @Override
  public JsonObject parseResponse(Response response) throws IOException {
    return PdfGateResponseParser.parseJsonObject(response);
  }
}
