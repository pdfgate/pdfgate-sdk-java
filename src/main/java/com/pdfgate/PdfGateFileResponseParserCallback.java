package com.pdfgate;

import java.io.IOException;
import okhttp3.Response;

public class PdfGateFileResponseParserCallback extends PdfGateResponseParserCallback<byte[]> {

  public PdfGateFileResponseParserCallback(PdfGateCallback<byte[]> callback) {
    this.callback = callback;
  }

  @Override
  public byte[] parseResponse(Response response) throws IOException {
    return PdfGateResponseParser.parseBytes(response);
  }
}
