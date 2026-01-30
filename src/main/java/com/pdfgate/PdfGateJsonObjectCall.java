package com.pdfgate;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

/**
 * Call wrapper for JSON object responses.
 */
final class PdfGateJsonObjectCall extends PdfGateCall implements CallJsonObject {
    PdfGateJsonObjectCall(Call delegate) {
        super(delegate);
    }

    @NotNull
    @Override
    public Call clone() {
        return new PdfGateJsonObjectCall(cloneDelegate());
    }
}
