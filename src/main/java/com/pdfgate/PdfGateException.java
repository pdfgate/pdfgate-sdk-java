package com.pdfgate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Exception raised for PDFGate API errors and transport failures.
 */
public final class PdfGateException extends IOException {
  private final int statusCode;
  private final String responseBody;
  private final Headers headers;

  /**
   * Creates a new exception with the provided response details.
   *
   * @param message message describing the error.
   * @param statusCode HTTP status code or {@code -1} when unavailable.
   * @param responseBody raw response body.
   * @param headers response headers.
   */
  public PdfGateException(String message, int statusCode, String responseBody, Headers headers) {
    super(message);
    this.statusCode = statusCode;
    this.responseBody = responseBody;
    this.headers = headers;
  }

  /**
   * Creates a {@link PdfGateException} from an HTTP response.
   *
   * @param response the HTTP response, possibly {@code null}.
   * @return a populated {@link PdfGateException} describing the failure.
   * @throws IOException when reading the response body fails.
   */
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

  /**
   * Creates a {@link PdfGateException} from an IO failure.
   *
   * @param e the underlying failure.
   * @return a populated {@link PdfGateException} describing the failure.
   */
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

  /**
   * Returns the HTTP status code or {@code -1} when unavailable.
   */
  public int getStatusCode() {
    return statusCode;
  }

  /**
   * Returns the raw response body.
   */
  public String getResponseBody() {
    return responseBody;
  }

  /**
   * Returns the HTTP response headers.
   */
  public Headers getHeaders() {
    return headers;
  }
}
