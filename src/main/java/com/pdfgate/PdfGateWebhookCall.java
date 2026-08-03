package com.pdfgate;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

/**
 * Marker class for calls that require a {@link PdfGateWebhookResponse} response.
 *
 * <p>Use it as a normal {@link Call}.
 */
final class PdfGateWebhookCall extends PdfGateCall implements CallWebhook {
  PdfGateWebhookCall(Call delegate) {
    super(delegate);
  }

  @NotNull
  @Override
  public Call clone() {
    return new PdfGateWebhookCall(cloneDelegate());
  }
}
