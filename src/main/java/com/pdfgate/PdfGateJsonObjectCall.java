package com.pdfgate;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

/**
 * Marker class for callbacks that require a {@link com.google.gson.JsonObject} response
 *
 * <p>Use it as a normal {@link Call}.
 */
final class PdfGateJsonObjectCall extends PdfGateCall implements CallJsonObject {
  PdfGateJsonObjectCall(Call delegate) {
    super(delegate);
  }

  @NotNull
  @Override
  public Call clone() {
    return new PdfGateJsonObjectCall(cloneDelegate());
  }
}
