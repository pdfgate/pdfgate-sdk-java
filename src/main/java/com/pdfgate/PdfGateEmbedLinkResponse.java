package com.pdfgate;

import java.time.Instant;
import java.util.Objects;

/**
 * Embedded signing link returned by the PDFGate API.
 */
public final class PdfGateEmbedLinkResponse {
  private String url;
  private Instant expiresAt;

  /**
   * Creates an empty embed link response for JSON deserialization.
   */
  public PdfGateEmbedLinkResponse() {
  }

  /**
   * Returns the embedded signing URL to load in an iframe.
   *
   * @return the embedded signing URL to load in an iframe.
   */
  public String getUrl() {
    return url;
  }

  /**
   * Returns when the embed link expires.
   *
   * @return when the embed link expires.
   */
  public Instant getExpiresAt() {
    return expiresAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PdfGateEmbedLinkResponse that = (PdfGateEmbedLinkResponse) o;
    return Objects.equals(url, that.url)
        && Objects.equals(expiresAt, that.expiresAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, expiresAt);
  }
}
