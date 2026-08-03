package com.pdfgate;

/**
 * Parameters for fetching a webhook by ID.
 */
public final class GetWebhookParams {
  private final String id;

  private GetWebhookParams(Builder builder) {
    this.id = builder.id;
  }

  /**
   * Creates a new builder for get webhook parameters.
   *
   * @return the builder for get webhook parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the webhook ID to fetch.
   *
   * @return the webhook ID to fetch.
   */
  public String getId() {
    return id;
  }

  /**
   * Builder for {@link GetWebhookParams}.
   */
  public static final class Builder {
    private String id;

    private Builder() {
    }

    /**
     * Sets the webhook ID to fetch.
     *
     * @param id the webhook ID.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Builds the get webhook parameters.
     *
     * @return the get webhook parameters.
     */
    public GetWebhookParams build() {
      return new GetWebhookParams(this);
    }
  }
}
