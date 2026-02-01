package com.pdfgate;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

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
