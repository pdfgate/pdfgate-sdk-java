package com.pdfgate;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public abstract class PdfGateResponseParser<T> implements Callback {

    protected PDFGateCallback<T> callback;

    @Override public void onFailure(@NotNull Call call, @NotNull IOException e) {
        callback.onFailure(call, PdfGateException.fromException(e));
    }

    @Override public void onResponse(@NotNull Call call, @NotNull Response response) {
        try (Response r = response) {
            if (!r.isSuccessful()) {
                callback.onFailure(call, PdfGateException.fromResponse(r));
                return;
            }
            ResponseBody responseBody = r.body();
            T parsedResponse = parseResponse(responseBody);
            callback.onSuccess(call, parsedResponse);
        } catch (IOException e) {
            callback.onFailure(call, PdfGateException.fromException(e));
        } catch (Exception e) {
            callback.onFailure(call, e);
        }
    }

    public abstract T parseResponse(ResponseBody body) throws IOException;
}
