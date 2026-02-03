package com.pdfgate;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;


/**
 * Marker class for callbacks that require a File response
 *
 * <p>Use it as a normal {@link Call}.
 */
final class PdfGateFileCall extends PdfGateCall implements CallFile {
  PdfGateFileCall(Call delegate) {
    super(delegate);
  }

  @NotNull
  @Override
  public Call clone() {
    return new PdfGateFileCall(cloneDelegate());
  }
}
