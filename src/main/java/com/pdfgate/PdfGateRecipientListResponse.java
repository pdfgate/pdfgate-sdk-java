package com.pdfgate;

import java.util.List;
import java.util.Objects;

/**
 * List of stored recipients returned by the PDFGate API, oldest first.
 */
public final class PdfGateRecipientListResponse {
  private List<PdfGateRecipientResponse> recipients;

  /**
   * Creates an empty recipient list response for JSON deserialization.
   */
  public PdfGateRecipientListResponse() {
  }

  /**
   * Returns the stored recipients matching the lookup, oldest first.
   *
   * @return the stored recipients matching the lookup, oldest first.
   */
  public List<PdfGateRecipientResponse> getRecipients() {
    return recipients;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PdfGateRecipientListResponse that = (PdfGateRecipientListResponse) o;
    return Objects.equals(recipients, that.recipients);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recipients);
  }
}
