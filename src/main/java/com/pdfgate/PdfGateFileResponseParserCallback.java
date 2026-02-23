package com.pdfgate;

import java.io.IOException;
import okhttp3.Response;

/**
 * Parses file responses into raw bytes.
 */
public class PdfGateFileResponseParserCallback extends PdfGateResponseParserCallback<byte[]> {

  /**
   * Creates a callback for file responses.
   *
   * @param callback callback invoked with parsed bytes.
   */
  public PdfGateFileResponseParserCallback(PdfGateCallback<byte[]> callback) {
    this.callback = callback;
  }

  @Override
  public byte[] parseResponse(Response response) throws IOException {
    return PdfGateResponseParser.parseBytes(response);
  }
}
