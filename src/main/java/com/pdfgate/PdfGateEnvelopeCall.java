package com.pdfgate;

import okhttp3.Call;

final class PdfGateEnvelopeCall extends PdfGateCall implements CallEnvelope {
  PdfGateEnvelopeCall(Call delegate) {
    super(delegate);
  }

  @Override
  public Call clone() {
    return new PdfGateEnvelopeCall(cloneDelegate());
  }
}
