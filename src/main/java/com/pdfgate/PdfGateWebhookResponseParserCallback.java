package com.pdfgate;

import java.io.IOException;
import okhttp3.Response;

/**
 * Parses JSON responses into {@link PdfGateWebhookResponse} instances.
 */
public class PdfGateWebhookResponseParserCallback
    extends PdfGateResponseParserCallback<PdfGateWebhookResponse> {

  /**
   * Creates a response parser callback for webhook payloads.
   *
   * @param callback callback invoked with parsed responses.
   */
  public PdfGateWebhookResponseParserCallback(PdfGateCallback<PdfGateWebhookResponse> callback) {
    this.callback = callback;
  }

  @Override
  public PdfGateWebhookResponse parseResponse(Response response) throws IOException {
    return PdfGateResponseParser.parseWebhook(response);
  }
}
