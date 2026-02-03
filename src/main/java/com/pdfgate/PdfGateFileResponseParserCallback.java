package com.pdfgate;

import java.io.IOException;
import okhttp3.Response;

/**
 * Parses byte responses for file payloads.
 */
public class PdfGateFileResponseParserCallback extends PdfGateResponseParserCallback<byte[]> {

  /**
   * Creates a response parser callback for file payloads.
   *
   * @param callback callback invoked with parsed responses.
   */
  public PdfGateFileResponseParserCallback(PdfGateCallback<byte[]> callback) {
    this.callback = callback;
  }

  @Override
  public byte[] parseResponse(Response response) throws IOException {
    return PdfGateResponseParser.parseBytes(response);
  }
}
