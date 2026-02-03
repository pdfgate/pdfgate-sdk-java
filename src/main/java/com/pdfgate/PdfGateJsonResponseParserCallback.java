package com.pdfgate;

import java.io.IOException;
import okhttp3.Response;

/**
 * Parses JSON responses into {@link PdfGateDocument} instances.
 */
public class PdfGateJsonResponseParserCallback
    extends PdfGateResponseParserCallback<PdfGateDocument> {

  /**
   * Creates a response parser callback for JSON document payloads.
   *
   * @param callback callback invoked with parsed responses.
   */
  public PdfGateJsonResponseParserCallback(PdfGateCallback<PdfGateDocument> callback) {
    this.callback = callback;
  }

  @Override
  public PdfGateDocument parseResponse(Response response) throws IOException {
    return PdfGateResponseParser.parseJson(response);
  }
}
