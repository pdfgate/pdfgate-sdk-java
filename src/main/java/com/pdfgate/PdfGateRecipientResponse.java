package com.pdfgate;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Stored recipient metadata returned by the PDFGate API.
 */
public final class PdfGateRecipientResponse {
  private String id;
  private String email;
  private String name;
  private Map<String, Object> metadata;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant lastUsedAt;

  /**
   * Creates an empty recipient response for JSON deserialization.
   */
  public PdfGateRecipientResponse() {
  }

  /**
   * Returns the recipient identifier.
   *
   * @return the recipient identifier.
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the recipient email address (stored lowercased).
   *
   * @return the recipient email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns the recipient display name, if present.
   *
   * @return the recipient display name, if present.
   */
  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  /**
   * Returns metadata attached to the recipient, if present.
   *
   * @return metadata attached to the recipient, if present.
   */
  public Optional<Map<String, Object>> getMetadata() {
    return Optional.ofNullable(metadata);
  }

  /**
   * Returns when the recipient was created, if present.
   *
   * @return when the recipient was created, if present.
   */
  public Optional<Instant> getCreatedAt() {
    return Optional.ofNullable(createdAt);
  }

  /**
   * Returns when the recipient was last updated, if present.
   *
   * @return when the recipient was last updated, if present.
   */
  public Optional<Instant> getUpdatedAt() {
    return Optional.ofNullable(updatedAt);
  }

  /**
   * Returns when the recipient was last used in an envelope, if present.
   *
   * @return when the recipient was last used, if present.
   */
  public Optional<Instant> getLastUsedAt() {
    return Optional.ofNullable(lastUsedAt);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PdfGateRecipientResponse that = (PdfGateRecipientResponse) o;
    return Objects.equals(id, that.id)
        && Objects.equals(email, that.email)
        && Objects.equals(name, that.name)
        && Objects.equals(metadata, that.metadata)
        && Objects.equals(createdAt, that.createdAt)
        && Objects.equals(updatedAt, that.updatedAt)
        && Objects.equals(lastUsedAt, that.lastUsedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, name, metadata, createdAt, updatedAt, lastUsedAt);
  }
}
