package com.pdfgate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

public final class PdfGateException extends IOException {
  private final int statusCode;
  private final String responseBody;
  private final Headers headers;

  public PdfGateException(String message, int statusCode, String responseBody, Headers headers) {
    super(message);
    this.statusCode = statusCode;
    this.responseBody = responseBody;
    this.headers = headers;
  }

  public static PdfGateException fromResponse(Response response) throws IOException {
    if (response == null) {
      return new PdfGateException(
          "PdfGate API request failed: no response.",
          -1,
          "",
          new Headers.Builder().build()
      );
    }
    int statusCode = response.code();
    ResponseBody body = response.body();
    String bodyText = body == null ? "" : body.string();
    String errorDetail = parseErrorMessageFromBody(bodyText);
    if (errorDetail == null || errorDetail.isEmpty()) {
      errorDetail = bodyText;
    }
    String message = "PdfGate API request failed with status " + statusCode + ": " + errorDetail;
    return new PdfGateException(message, statusCode, bodyText, response.headers());
  }

  public static PdfGateException fromException(IOException e) {
    String message = e == null ? null : e.getMessage();
    if (message == null || message.isBlank()) {
      message = e == null ? "IOException" : e.getClass().getSimpleName();
    }
    PdfGateException exception = new PdfGateException(
        "PdfGate API request failed: " + message,
        -1,
        "",
        new Headers.Builder().build()
    );
    if (e != null) {
      exception.initCause(e);
    }
    return exception;
  }

  private static String parseErrorMessageFromBody(String bodyText) {
    if (bodyText == null || bodyText.isEmpty()) {
      return null;
    }
    try {
      JsonElement element = JsonParser.parseString(bodyText);
      if (!element.isJsonObject()) {
        return null;
      }
      JsonObject obj = element.getAsJsonObject();
      if (!obj.has("message")) {
        return null;
      }
      JsonElement messageElement = obj.get("message");
      if (messageElement == null || messageElement.isJsonNull()) {
        return null;
      }
      return messageElement.isJsonPrimitive() ? messageElement.getAsString() :
          messageElement.toString();
    } catch (JsonSyntaxException ignored) {
      return null;
    }
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public Headers getHeaders() {
    return headers;
  }
}
