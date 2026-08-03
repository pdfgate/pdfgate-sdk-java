package com.pdfgate;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

/**
 * Marker class for calls that expect an empty (no content) response.
 *
 * <p>Use it as a normal {@link Call}.
 */
final class PdfGateVoidCall extends PdfGateCall implements CallVoid {
  PdfGateVoidCall(Call delegate) {
    super(delegate);
  }

  @NotNull
  @Override
  public Call clone() {
    return new PdfGateVoidCall(cloneDelegate());
  }
}
