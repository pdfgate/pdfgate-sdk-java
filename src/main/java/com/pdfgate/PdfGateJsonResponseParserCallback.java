package com.pdfgate;

import java.io.IOException;
import okhttp3.Response;

public class PdfGateJsonResponseParserCallback
    extends PdfGateResponseParserCallback<PdfGateDocument> {

  public PdfGateJsonResponseParserCallback(PdfGateCallback<PdfGateDocument> callback) {
    this.callback = callback;
  }

  @Override
  public PdfGateDocument parseResponse(Response response) throws IOException {
    return PdfGateResponseParser.parseJson(response);
  }
}
