package com.pdfgate;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

/**
 * Marker class for callbacks that require a {@link PdfGateDocument} response
 *
 * <p>Use it as a normal {@link Call}.
 */
final class PdfGateJsonCall extends PdfGateCall implements CallJson {
  PdfGateJsonCall(Call delegate) {
    super(delegate);
  }

  @NotNull
  @Override
  public Call clone() {
    return new PdfGateJsonCall(cloneDelegate());
  }
}
