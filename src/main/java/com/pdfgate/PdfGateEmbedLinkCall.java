package com.pdfgate;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

/**
 * Marker class for calls that require a {@link PdfGateEmbedLinkResponse} response.
 *
 * <p>Use it as a normal {@link Call}.
 */
final class PdfGateEmbedLinkCall extends PdfGateCall implements CallEmbedLink {
  PdfGateEmbedLinkCall(Call delegate) {
    super(delegate);
  }

  @NotNull
  @Override
  public Call clone() {
    return new PdfGateEmbedLinkCall(cloneDelegate());
  }
}
