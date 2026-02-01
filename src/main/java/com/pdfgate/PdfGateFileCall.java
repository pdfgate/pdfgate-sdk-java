package com.pdfgate;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

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
