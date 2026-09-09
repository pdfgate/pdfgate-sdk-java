package com.pdfgate;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

/**
 * Marker class for calls that require a {@link PdfGateRecipientListResponse} response.
 *
 * <p>Use it as a normal {@link Call}.
 */
final class PdfGateRecipientListCall extends PdfGateCall implements CallRecipientList {
  PdfGateRecipientListCall(Call delegate) {
    super(delegate);
  }

  @NotNull
  @Override
  public Call clone() {
    return new PdfGateRecipientListCall(cloneDelegate());
  }
}
