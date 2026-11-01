package com.pdfgate;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * Automatic signing. The reusable signature a recipient stored through the
 * enrolment link, or the state of the enrolment.
 */
public final class PdfGateRecipientStoredSignature {
  private StoredSignatureStatus status;
  private Instant requestedAt;
  private SignatureAdoptionMethod adoptionMethod;
  private Instant enrolledAt;
  private Instant revokedAt;
  private Instant lastUsedAt;
  private int useCount;
  private String image;

  /**
   * Creates an empty stored signature for JSON deserialization.
   */
  public PdfGateRecipientStoredSignature() {
  }

  /**
   * Returns the state of the stored signature.
   *
   * <p>{@link StoredSignatureStatus#PENDING} while the enrolment link was sent but
   * not completed, {@link StoredSignatureStatus#ACTIVE} once the signature can be
   * applied, {@link StoredSignatureStatus#REVOKED} after it was removed.
   *
   * @return the state of the stored signature.
   */
  public StoredSignatureStatus getStatus() {
    return status;
  }

  /**
   * Returns when the enrolment link was last sent, if present.
   *
   * @return when the enrolment link was last sent, if present.
   */
  public Optional<Instant> getRequestedAt() {
    return Optional.ofNullable(requestedAt);
  }

  /**
   * Returns how the recipient created the signature, if present.
   *
   * @return how the recipient created the signature, if present.
   */
  public Optional<SignatureAdoptionMethod> getAdoptionMethod() {
    return Optional.ofNullable(adoptionMethod);
  }

  /**
   * Returns when the recipient completed the enrolment, if present.
   *
   * @return when the recipient completed the enrolment, if present.
   */
  public Optional<Instant> getEnrolledAt() {
    return Optional.ofNullable(enrolledAt);
  }

  /**
   * Returns when the signature was removed, if present.
   *
   * @return when the signature was removed, if present.
   */
  public Optional<Instant> getRevokedAt() {
    return Optional.ofNullable(revokedAt);
  }

  /**
   * Returns when the signature was last applied to a document, if present.
   *
   * @return when the signature was last applied to a document, if present.
   */
  public Optional<Instant> getLastUsedAt() {
    return Optional.ofNullable(lastUsedAt);
  }

  /**
   * Returns how many documents the signature was applied to.
   *
   * @return how many documents the signature was applied to.
   */
  public int getUseCount() {
    return useCount;
  }

  /**
   * Returns the signature as a PNG data URL ({@code data:image/png;base64,...}),
   * if present.
   *
   * <p>Returned only by {@link PdfGate#getRecipient(GetRecipientParams)} while the
   * signature is active; never included in {@code listRecipients}.
   *
   * @return the signature as a PNG data URL, if present.
   */
  public Optional<String> getImage() {
    return Optional.ofNullable(image);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PdfGateRecipientStoredSignature that = (PdfGateRecipientStoredSignature) o;
    return status == that.status
        && Objects.equals(requestedAt, that.requestedAt)
        && adoptionMethod == that.adoptionMethod
        && Objects.equals(enrolledAt, that.enrolledAt)
        && Objects.equals(revokedAt, that.revokedAt)
        && Objects.equals(lastUsedAt, that.lastUsedAt)
        && useCount == that.useCount
        && Objects.equals(image, that.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, requestedAt, adoptionMethod, enrolledAt, revokedAt, lastUsedAt,
        useCount, image);
  }
}
