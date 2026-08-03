package com.pdfgate;

import com.google.gson.JsonObject;
import java.io.IOException;
import okhttp3.Response;
import okhttp3.ResponseBody;

final class PdfGateResponseParser {
  private PdfGateResponseParser() {
  }

  static PdfGateDocument parseJson(Response response) throws IOException {
    return parseJson(response, PdfGateDocument.class);
  }

  static PDFGateEnvelope parseEnvelope(Response response) throws IOException {
    return parseJson(response, PDFGateEnvelope.class);
  }

  static PdfGateWebhookResponse parseWebhook(Response response) throws IOException {
    return parseJson(response, PdfGateWebhookResponse.class);
  }

  /**
   * Parses a JSON response into a {@link JsonObject}.
   */
  static JsonObject parseJsonObject(Response response) throws IOException {
    ensureSuccess(response);
    ResponseBody body = response.body();
    String json = body == null ? "" : body.string();
    try {
      return PdfGateJson.gson().fromJson(json, JsonObject.class);
    } catch (RuntimeException e) {
      throw PdfGateException.fromParseFailure(response, json, e);
    }
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

  private static <T> T parseJson(Response response, Class<T> responseType) throws IOException {
    ensureSuccess(response);
    ResponseBody body = response.body();
    String json = body == null ? "" : body.string();
    try {
      return PdfGateJson.gson().fromJson(json, responseType);
    } catch (RuntimeException e) {
      throw PdfGateException.fromParseFailure(response, json, e);
    }
  }
}
