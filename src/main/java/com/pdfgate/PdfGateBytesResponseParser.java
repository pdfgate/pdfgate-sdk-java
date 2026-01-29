package com.pdfgate;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PdfGateBytesResponseParser extends PdfGateResponseParser {

    public PdfGateBytesResponseParser(PDFGateCallback<byte[]> callback) {
        this.callback = callback;
    }

    @Override public byte[] parseResponse(ResponseBody responseBody) throws IOException {
        byte[] bytes = responseBody == null ? new byte[0] : responseBody.bytes();

        return bytes;
    }
}
