package com.pdfgate;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/**
 * Base callback that parses HTTP responses and dispatches results to a {@link PdfGateCallback}.
 *
 * @param <T> response type produced by the parser.
 */
public abstract class PdfGateResponseParserCallback<T> implements Callback {

  /**
   * Callback invoked with parsed responses or errors.
   */
  protected PdfGateCallback<T> callback;

  /**
   * Creates a response parser callback.
   */
  protected PdfGateResponseParserCallback() {
  }

  @Override
  public void onFailure(@NotNull Call call, @NotNull IOException e) {
    callback.onFailure(call, PdfGateException.fromException(e));
  }

  @Override
  public void onResponse(@NotNull Call call, @NotNull Response response) {
    try (Response r = response) {
      PdfGateResponseParser.ensureSuccess(r);
      T parsedResponse = parseResponse(r);
      callback.onSuccess(call, parsedResponse);
    } catch (PdfGateException e) {
      callback.onFailure(call, e);
    } catch (IOException e) {
      callback.onFailure(call, PdfGateException.fromException(e));
    } catch (Exception e) {
      callback.onFailure(call, e);
    }
  }

  /**
   * Parses the HTTP response into the expected payload type.
   *
   * @param response HTTP response to parse.
   * @return the parsed response payload.
   * @throws IOException when parsing fails.
   */
  public abstract T parseResponse(Response response) throws IOException;
}
