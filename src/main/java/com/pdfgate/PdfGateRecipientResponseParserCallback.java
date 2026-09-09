package com.pdfgate;

import java.io.IOException;
import okhttp3.Response;

/**
 * Parses JSON responses into {@link PdfGateRecipientResponse} instances.
 */
public class PdfGateRecipientResponseParserCallback
    extends PdfGateResponseParserCallback<PdfGateRecipientResponse> {

  /**
   * Creates a response parser callback for recipient payloads.
   *
   * @param callback callback invoked with parsed responses.
   */
  public PdfGateRecipientResponseParserCallback(
      PdfGateCallback<PdfGateRecipientResponse> callback) {
    this.callback = callback;
  }

  @Override
  public PdfGateRecipientResponse parseResponse(Response response) throws IOException {
    return PdfGateResponseParser.parseRecipient(response);
  }
}
