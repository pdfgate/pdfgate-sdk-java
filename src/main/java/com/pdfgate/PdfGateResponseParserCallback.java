package com.pdfgate;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public abstract class PdfGateResponseParserCallback<T> implements Callback {

  protected PdfGateCallback<T> callback;

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

  public abstract T parseResponse(Response response) throws IOException;
}
