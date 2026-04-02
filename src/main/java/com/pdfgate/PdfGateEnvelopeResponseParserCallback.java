package com.pdfgate;

import java.io.IOException;
import okhttp3.Response;

/**
 * Parses JSON responses into {@link PDFGateEnvelope} instances.
 */
public class PdfGateEnvelopeResponseParserCallback
    extends PdfGateResponseParserCallback<PDFGateEnvelope> {

  /**
   * Creates a response parser callback for envelope payloads.
   *
   * @param callback callback invoked with parsed responses.
   */
  public PdfGateEnvelopeResponseParserCallback(PdfGateCallback<PDFGateEnvelope> callback) {
    this.callback = callback;
  }

  @Override
  public PDFGateEnvelope parseResponse(Response response) throws IOException {
    return PdfGateResponseParser.parseEnvelope(response);
  }
}
