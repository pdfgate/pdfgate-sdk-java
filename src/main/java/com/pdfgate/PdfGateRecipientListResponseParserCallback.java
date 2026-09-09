package com.pdfgate;

import java.io.IOException;
import okhttp3.Response;

/**
 * Parses JSON responses into {@link PdfGateRecipientListResponse} instances.
 */
public class PdfGateRecipientListResponseParserCallback
    extends PdfGateResponseParserCallback<PdfGateRecipientListResponse> {

  /**
   * Creates a response parser callback for recipient list payloads.
   *
   * @param callback callback invoked with parsed responses.
   */
  public PdfGateRecipientListResponseParserCallback(
      PdfGateCallback<PdfGateRecipientListResponse> callback) {
    this.callback = callback;
  }

  @Override
  public PdfGateRecipientListResponse parseResponse(Response response) throws IOException {
    return PdfGateResponseParser.parseRecipientList(response);
  }
}
