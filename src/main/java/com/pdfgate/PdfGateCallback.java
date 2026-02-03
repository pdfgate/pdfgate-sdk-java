package com.pdfgate;

import okhttp3.Call;

/**
 * Callback for asynchronous PDFGate requests.
 *
 * @param <T> the response payload type.
 */
public interface PdfGateCallback<T> {
  /**
   * Called when the request completes successfully.
   *
   * @param call the underlying OkHttp call.
   * @param value the parsed response payload.
   */
  void onSuccess(Call call, T value);

  /**
   * Called when the request fails or parsing throws an exception.
   *
   * @param call the underlying OkHttp call.
   * @param t the failure cause.
   */
  void onFailure(Call call, Throwable t);
}
