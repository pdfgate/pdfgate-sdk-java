package com.pdfgate;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

/**
 * Marker class for calls that require a {@link PdfGateRecipientResponse} response.
 *
 * <p>Use it as a normal {@link Call}.
 */
final class PdfGateRecipientCall extends PdfGateCall implements CallRecipient {
  PdfGateRecipientCall(Call delegate) {
    super(delegate);
  }

  @NotNull
  @Override
  public Call clone() {
    return new PdfGateRecipientCall(cloneDelegate());
  }
}
