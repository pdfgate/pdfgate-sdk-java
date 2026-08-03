package com.pdfgate;

/**
 * Parameters for deleting a webhook by ID.
 */
public final class DeleteWebhookParams {
  private final String id;

  private DeleteWebhookParams(Builder builder) {
    this.id = builder.id;
  }

  /**
   * Creates a new builder for delete webhook parameters.
   *
   * @return the builder for delete webhook parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the webhook ID to delete.
   *
   * @return the webhook ID to delete.
   */
  public String getId() {
    return id;
  }

  /**
   * Builder for {@link DeleteWebhookParams}.
   */
  public static final class Builder {
    private String id;

    private Builder() {
    }

    /**
     * Sets the webhook ID to delete.
     *
     * @param id the webhook ID.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Builds the delete webhook parameters.
     *
     * @return the delete webhook parameters.
     */
    public DeleteWebhookParams build() {
      return new DeleteWebhookParams(this);
    }
  }
}
