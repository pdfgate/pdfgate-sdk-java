package com.pdfgate;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Webhook metadata returned by the PDFGate API.
 */
public final class PdfGateWebhookResponse {
  private String id;
  private String url;
  private List<WebhookEventType> eventTypes;
  private WebhookStatus status;
  private String description;
  private String secret;
  private Instant createdAt;
  private Instant updatedAt;

  /**
   * Creates an empty webhook response for JSON deserialization.
   */
  public PdfGateWebhookResponse() {
  }

  /**
   * Returns the webhook identifier.
   *
   * @return the webhook identifier.
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the webhook endpoint URL.
   *
   * @return the webhook endpoint URL.
   */
  public String getUrl() {
    return url;
  }

  /**
   * Returns the events the webhook is subscribed to.
   *
   * @return the subscribed event types.
   */
  public List<WebhookEventType> getEventTypes() {
    return eventTypes;
  }

  /**
   * Returns the webhook status.
   *
   * @return the webhook status.
   */
  public WebhookStatus getStatus() {
    return status;
  }

  /**
   * Returns the webhook description, if present.
   *
   * @return the webhook description, if present.
   */
  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  /**
   * Returns the signing secret, if present.
   *
   * <p>Only returned once, when the webhook is created.
   *
   * @return the signing secret, if present.
   */
  public Optional<String> getSecret() {
    return Optional.ofNullable(secret);
  }

  /**
   * Returns when the webhook was created.
   *
   * @return when the webhook was created.
   */
  public Instant getCreatedAt() {
    return createdAt;
  }

  /**
   * Returns when the webhook was last updated, if present.
   *
   * @return when the webhook was last updated, if present.
   */
  public Optional<Instant> getUpdatedAt() {
    return Optional.ofNullable(updatedAt);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PdfGateWebhookResponse that = (PdfGateWebhookResponse) o;
    return Objects.equals(id, that.id)
        && Objects.equals(url, that.url)
        && Objects.equals(eventTypes, that.eventTypes)
        && status == that.status
        && Objects.equals(description, that.description)
        && Objects.equals(secret, that.secret)
        && Objects.equals(createdAt, that.createdAt)
        && Objects.equals(updatedAt, that.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, url, eventTypes, status, description, secret, createdAt, updatedAt);
  }
}
