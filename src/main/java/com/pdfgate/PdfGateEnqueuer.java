package com.pdfgate;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import okhttp3.Call;

/**
 * Handles enqueuing calls and adapting responses for async usage.
 */
final class PdfGateEnqueuer {
  /**
   * Enqueues a JSON response call and maps the response to {@link PdfGateDocument}.
   */
  public void enqueue(CallJson call, PdfGateCallback<PdfGateDocument> callback) {
    call.enqueue(new PdfGateJsonResponseParserCallback(callback));
  }

  /**
   * Enqueues a JSON response call and maps the response to {@link PDFGateEnvelope}.
   */
  public void enqueue(CallEnvelope call, PdfGateCallback<PDFGateEnvelope> callback) {
    call.enqueue(new PdfGateEnvelopeResponseParserCallback(callback));
  }

  /**
   * Enqueues a bytes response call and returns the raw response bytes.
   */
  public void enqueue(CallFile call, PdfGateCallback<byte[]> callback) {
    call.enqueue(new PdfGateFileResponseParserCallback(callback));
  }

  /**
   * Enqueues a JSON response call and maps the response to {@link JsonObject}.
   */
  public void enqueue(CallJsonObject call, PdfGateCallback<JsonObject> callback) {
    call.enqueue(new PdfGateJsonObjectResponseParserCallback(callback));
  }

  /**
   * Enqueues a JSON response call and maps the response to {@link PdfGateWebhookResponse}.
   */
  public void enqueue(CallWebhook call, PdfGateCallback<PdfGateWebhookResponse> callback) {
    call.enqueue(new PdfGateWebhookResponseParserCallback(callback));
  }

  /**
   * Enqueues an empty (no content) response call.
   */
  public void enqueue(CallVoid call, PdfGateCallback<Void> callback) {
    call.enqueue(new PdfGateVoidResponseParserCallback(callback));
  }

  /**
   * Enqueues a JSON response call and returns a {@link CompletableFuture}.
   */
  public CompletableFuture<PdfGateDocument> enqueueAsFuture(CallJson call) {
    return enqueueAsFuture(call, this::enqueue);
  }

  /**
   * Enqueues a JSON response call and returns a {@link CompletableFuture}.
   */
  public CompletableFuture<PDFGateEnvelope> enqueueAsFuture(CallEnvelope call) {
    return enqueueAsFuture(call, this::enqueue);
  }

  /**
   * Enqueues a JSON response call and returns a {@link CompletableFuture}.
   */
  public CompletableFuture<JsonObject> enqueueAsFuture(CallJsonObject call) {
    return enqueueAsFuture(call, this::enqueue);
  }

  /**
   * Enqueues a bytes response call and returns a {@link CompletableFuture}.
   */
  public CompletableFuture<byte[]> enqueueAsFuture(CallFile call) {
    return enqueueAsFuture(call, this::enqueue);
  }

  /**
   * Enqueues a webhook response call and returns a {@link CompletableFuture}.
   */
  public CompletableFuture<PdfGateWebhookResponse> enqueueAsFuture(CallWebhook call) {
    return enqueueAsFuture(call, this::enqueue);
  }

  /**
   * Enqueues an empty (no content) response call and returns a {@link CompletableFuture}.
   */
  public CompletableFuture<Void> enqueueAsFuture(CallVoid call) {
    return enqueueAsFuture(call, this::enqueue);
  }

  private <T, C extends Call> CompletableFuture<T> enqueueAsFuture(
      C call,
      BiConsumer<C, PdfGateCallback<T>> enqueuer) {
    CompletableFuture<T> future = new CompletableFuture<>();
    enqueuer.accept(call, new PdfGateCallback<T>() {
      @Override
      public void onSuccess(Call call, T value) {
        future.complete(value);
      }

      @Override
      public void onFailure(Call call, Throwable t) {
        future.completeExceptionally(wrapAsyncThrowable(t));
      }
    });
    future.whenComplete((r, t) -> {
      if (future.isCancelled()) {
        call.cancel();
      }
    });
    return future;
  }

  /**
   * Wraps async exceptions in {@link PdfGateException} where appropriate.
   */
  private Throwable wrapAsyncThrowable(Throwable t) {
    if (t instanceof PdfGateException) {
      return t;
    }
    if (t instanceof IOException) {
      return PdfGateException.fromException((IOException) t);
    }
    return t;
  }
}
