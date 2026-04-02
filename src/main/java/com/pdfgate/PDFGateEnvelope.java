package com.pdfgate;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Envelope metadata returned by JSON responses from the PDFGate API.
 */
public final class PDFGateEnvelope {
  private String id;
  private EnvelopeStatus status;
  private List<EnvelopeDocumentResponse> documents;
  private Instant createdAt;
  private Instant completedAt;
  private Instant expiredAt;
  private Map<String, Object> metadata;

  /**
   * Creates an empty envelope instance for JSON deserialization.
   */
  public PDFGateEnvelope() {
  }

  /**
   * Returns the envelope identifier.
   *
   * @return the envelope identifier.
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the envelope status.
   *
   * @return the envelope status.
   */
  public EnvelopeStatus getStatus() {
    return status;
  }

  /**
   * Returns the document entries included in the envelope.
   *
   * @return the document entries included in the envelope.
   */
  public List<EnvelopeDocumentResponse> getDocuments() {
    return documents;
  }

  /**
   * Returns when the envelope was created.
   *
   * @return when the envelope was created.
   */
  public Instant getCreatedAt() {
    return createdAt;
  }

  /**
   * Returns when the envelope was completed, if present.
   *
   * @return when the envelope was completed, if present.
   */
  public Optional<Instant> getCompletedAt() {
    return Optional.ofNullable(completedAt);
  }

  /**
   * Returns when the envelope expired, if present.
   *
   * @return when the envelope expired, if present.
   */
  public Optional<Instant> getExpiredAt() {
    return Optional.ofNullable(expiredAt);
  }

  /**
   * Returns metadata attached to the envelope, if present.
   *
   * @return metadata attached to the envelope, if present.
   */
  public Optional<Map<String, Object>> getMetadata() {
    return Optional.ofNullable(metadata);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PDFGateEnvelope that = (PDFGateEnvelope) o;
    return Objects.equals(id, that.id)
        && status == that.status
        && Objects.equals(documents, that.documents)
        && Objects.equals(createdAt, that.createdAt)
        && Objects.equals(completedAt, that.completedAt)
        && Objects.equals(expiredAt, that.expiredAt)
        && Objects.equals(metadata, that.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, documents, createdAt, completedAt, expiredAt, metadata);
  }
}
