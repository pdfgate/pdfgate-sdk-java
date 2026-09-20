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
  private String recipientId;
  private boolean embedded;
  private DocumentRecipientStatus status;
  private Instant signedAt;
  private Instant viewedAt;
  private Integer signingOrder;
  private Instant activatedAt;
  private List<EnvelopeFieldResponse> fields;
  private String signingLink;
  private String previewLink;

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
   * Returns the id of the stored recipient, if present.
   *
   * @return the id of the stored recipient, if present.
   */
  public Optional<String> getRecipientId() {
    return Optional.ofNullable(recipientId);
  }

  /**
   * Returns whether the recipient signs through embedded signing. Embedded
   * recipients receive no emails and have no signing link.
   *
   * @return whether the recipient signs through embedded signing.
   */
  public boolean isEmbedded() {
    return embedded;
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
   * Returns the signing order of the recipient, if present.
   *
   * @return the signing order of the recipient, if present.
   */
  public Optional<Integer> getSigningOrder() {
    return Optional.ofNullable(signingOrder);
  }

  /**
   * Returns the time it became the recipient's turn to sign, if present.
   *
   * <p>Empty until the recipient is activated.
   *
   * @return the time it became the recipient's turn to sign, if present.
   */
  public Optional<Instant> getActivatedAt() {
    return Optional.ofNullable(activatedAt);
  }

  /**
   * Returns the fields assigned to the recipient.
   *
   * @return the fields assigned to the recipient.
   */
  public List<EnvelopeFieldResponse> getFields() {
    return fields;
  }

  /**
   * Returns the signing link for the recipient, if present.
   *
   * <p>Present while the recipient still needs to sign.
   *
   * @return the signing link, if present.
   */
  public Optional<String> getSigningLink() {
    return Optional.ofNullable(signingLink);
  }

  /**
   * Returns the preview link for the recipient, if present.
   *
   * <p>Present once the recipient has signed.
   *
   * @return the preview link, if present.
   */
  public Optional<String> getPreviewLink() {
    return Optional.ofNullable(previewLink);
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
        && Objects.equals(recipientId, that.recipientId)
        && embedded == that.embedded
        && status == that.status
        && Objects.equals(signedAt, that.signedAt)
        && Objects.equals(viewedAt, that.viewedAt)
        && Objects.equals(signingOrder, that.signingOrder)
        && Objects.equals(activatedAt, that.activatedAt)
        && Objects.equals(fields, that.fields)
        && Objects.equals(signingLink, that.signingLink)
        && Objects.equals(previewLink, that.previewLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, recipientId, embedded, status, signedAt, viewedAt, signingOrder,
        activatedAt, fields, signingLink, previewLink);
  }
}
