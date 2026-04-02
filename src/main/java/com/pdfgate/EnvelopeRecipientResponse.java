package com.pdfgate;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Recipient metadata returned for a document inside an envelope.
 */
public final class EnvelopeRecipientResponse {
  private String email;
  private DocumentRecipientStatus status;
  private Instant signedAt;
  private Instant viewedAt;
  private List<EnvelopeFieldResponse> fields;

  /**
   * Creates an empty recipient response for JSON deserialization.
   */
  public EnvelopeRecipientResponse() {
  }

  /**
   * Returns the recipient email address.
   *
   * @return the recipient email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns the recipient status.
   *
   * @return the recipient status.
   */
  public DocumentRecipientStatus getStatus() {
    return status;
  }

  /**
   * Returns when the recipient signed, if present.
   *
   * @return when the recipient signed, if present.
   */
  public Optional<Instant> getSignedAt() {
    return Optional.ofNullable(signedAt);
  }

  /**
   * Returns when the recipient viewed the document, if present.
   *
   * @return when the recipient viewed the document, if present.
   */
  public Optional<Instant> getViewedAt() {
    return Optional.ofNullable(viewedAt);
  }

  /**
   * Returns the fields assigned to the recipient.
   *
   * @return the fields assigned to the recipient.
   */
  public List<EnvelopeFieldResponse> getFields() {
    return fields;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EnvelopeRecipientResponse that = (EnvelopeRecipientResponse) o;
    return Objects.equals(email, that.email)
        && status == that.status
        && Objects.equals(signedAt, that.signedAt)
        && Objects.equals(viewedAt, that.viewedAt)
        && Objects.equals(fields, that.fields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, status, signedAt, viewedAt, fields);
  }
}
