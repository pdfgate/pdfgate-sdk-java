package com.pdfgate;

import java.io.IOException;
import okhttp3.Response;

/**
 * Parses JSON responses into {@link PdfGateEmbedLinkResponse} instances.
 */
public class PdfGateEmbedLinkResponseParserCallback
    extends PdfGateResponseParserCallback<PdfGateEmbedLinkResponse> {

  /**
   * Creates a response parser callback for embed link payloads.
   *
   * @param callback callback invoked with parsed responses.
   */
  public PdfGateEmbedLinkResponseParserCallback(
      PdfGateCallback<PdfGateEmbedLinkResponse> callback) {
    this.callback = callback;
  }

  @Override
  public PdfGateEmbedLinkResponse parseResponse(Response response) throws IOException {
    return PdfGateResponseParser.parseEmbedLink(response);
  }
}
