package com.pdfgate;

import okhttp3.Call;

public interface PDFGateCallback<T> {
    void onSuccess(Call call, T value);
    void onFailure(Call call, Throwable t);
}
