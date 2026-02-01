package com.pdfgate;

import okhttp3.Response;

import java.io.IOException;

public class PdfGateBytesResponseParserCallback extends PdfGateResponseParserCallback<byte[]> {

    public PdfGateBytesResponseParserCallback(PdfGateCallback<byte[]> callback) {
        this.callback = callback;
    }

    @Override public byte[] parseResponse(Response response) throws IOException {
        return PdfGateResponseParser.parseBytes(response);
    }
}
