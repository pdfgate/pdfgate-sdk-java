package com.pdfgate;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okio.Timeout;
import org.jetbrains.annotations.NotNull;

abstract class PdfGateCall implements Call {
    private final Call delegate;

    protected PdfGateCall(Call delegate) {
        this.delegate = delegate;
    }

    @NotNull
    @Override
    public Request request() {
        return delegate.request();
    }

    @NotNull
    @Override
    public Response execute() throws IOException {
        return delegate.execute();
    }

    @Override
    public void enqueue(@NotNull Callback responseCallback) {
        delegate.enqueue(responseCallback);
    }

    @Override
    public void cancel() {
        delegate.cancel();
    }

    @Override
    public boolean isExecuted() {
        return delegate.isExecuted();
    }

    @Override
    public boolean isCanceled() {
        return delegate.isCanceled();
    }

    @NotNull
    @Override
    public Timeout timeout() {
        return delegate.timeout();
    }

    protected Call cloneDelegate() {
        return delegate.clone();
    }

    @NotNull
    @Override
    public abstract Call clone();

    @Override
    public String toString() {
        return delegate.toString();
    }
}
