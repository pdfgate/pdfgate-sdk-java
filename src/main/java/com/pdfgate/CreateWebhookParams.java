package com.pdfgate;

import java.util.List;

/**
 * Parameters for registering a webhook endpoint.
 *
 * <p>The webhook URL must be publicly accessible (localhost is not supported). The response
 * includes a {@code secret} that is only returned once, at creation time.
 */
public final class CreateWebhookParams {
  private final String url;
  private final List<WebhookEventType> eventTypes;
  private final String description;

  private CreateWebhookParams(Builder builder) {
    this.url = builder.url;
    this.eventTypes = builder.eventTypes;
    this.description = builder.description;
  }

  /**
   * Creates a new builder for create webhook parameters.
   *
   * @return the builder for create webhook parameters.
   */
  public static Builder builder() {
    return new Builder();
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
   * Returns the events to subscribe to.
   *
   * @return the events to subscribe to.
   */
  public List<WebhookEventType> getEventTypes() {
    return eventTypes;
  }

  /**
   * Returns the webhook description, if present.
   *
   * @return the webhook description, if present.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Builder for {@link CreateWebhookParams}.
   */
  public static final class Builder {
    private String url;
    private List<WebhookEventType> eventTypes;
    private String description;

    private Builder() {
    }

    /**
     * Sets the webhook endpoint URL.
     *
     * @param url the webhook endpoint URL.
     * @return this builder.
     */
    public Builder url(String url) {
      this.url = url;
      return this;
    }

    /**
     * Sets the events to subscribe to.
     *
     * @param eventTypes the events to subscribe to.
     * @return this builder.
     */
    public Builder eventTypes(List<WebhookEventType> eventTypes) {
      this.eventTypes = eventTypes;
      return this;
    }

    /**
     * Sets the webhook description.
     *
     * @param description the webhook description.
     * @return this builder.
     */
    public Builder description(String description) {
      this.description = description;
      return this;
    }

    /**
     * Builds the create webhook parameters.
     *
     * @return the create webhook parameters.
     */
    public CreateWebhookParams build() {
      return new CreateWebhookParams(this);
    }
  }
}
