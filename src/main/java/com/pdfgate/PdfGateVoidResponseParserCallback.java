package com.pdfgate;

import java.io.IOException;
import okhttp3.Response;

/**
 * Verifies that an empty (no content) response succeeded.
 */
public class PdfGateVoidResponseParserCallback extends PdfGateResponseParserCallback<Void> {

  /**
   * Creates a response parser callback for empty payloads.
   *
   * @param callback callback invoked when the response succeeds.
   */
  public PdfGateVoidResponseParserCallback(PdfGateCallback<Void> callback) {
    this.callback = callback;
  }

  @Override
  public Void parseResponse(Response response) throws IOException {
    return null;
  }
}
