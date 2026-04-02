package com.pdfgate;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Document metadata returned for an envelope.
 */
public final class EnvelopeDocumentResponse {
  private String sourceDocumentId;
  private String signedDocumentId;
  private List<EnvelopeRecipientResponse> recipients;
  private EnvelopeDocumentStatus status;
  private Instant completedAt;

  /**
   * Creates an empty envelope document response for JSON deserialization.
   */
  public EnvelopeDocumentResponse() {
  }

  /**
   * Returns the source document ID.
   *
   * @return the source document ID.
   */
  public String getSourceDocumentId() {
    return sourceDocumentId;
  }

  /**
   * Returns the signed document ID, if present.
   *
   * @return the signed document ID, if present.
   */
  public Optional<String> getSignedDocumentId() {
    return Optional.ofNullable(signedDocumentId);
  }

  /**
   * Returns the recipient responses for the document.
   *
   * @return the recipient responses for the document.
   */
  public List<EnvelopeRecipientResponse> getRecipients() {
    return recipients;
  }

  /**
   * Returns the document status.
   *
   * @return the document status.
   */
  public EnvelopeDocumentStatus getStatus() {
    return status;
  }

  /**
   * Returns when the document was completed, if present.
   *
   * @return when the document was completed, if present.
   */
  public Optional<Instant> getCompletedAt() {
    return Optional.ofNullable(completedAt);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EnvelopeDocumentResponse that = (EnvelopeDocumentResponse) o;
    return Objects.equals(sourceDocumentId, that.sourceDocumentId)
        && Objects.equals(signedDocumentId, that.signedDocumentId)
        && Objects.equals(recipients, that.recipients)
        && status == that.status
        && Objects.equals(completedAt, that.completedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceDocumentId, signedDocumentId, recipients, status, completedAt);
  }
}
