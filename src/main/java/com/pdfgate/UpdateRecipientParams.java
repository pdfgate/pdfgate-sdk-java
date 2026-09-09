package com.pdfgate;

/**
 * Parameters for updating a stored recipient.
 */
public final class UpdateRecipientParams {
  /**
   * Recipient id to update.
   */
  private final String id;

  /**
   * Optional new recipient display name.
   */
  private final String name;

  /**
   * Optional new metadata attached to the recipient.
   */
  private final Object metadata;

  private UpdateRecipientParams(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.metadata = builder.metadata;
  }

  /**
   * Creates a new builder for update recipient parameters.
   *
   * @return the builder for update recipient parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the recipient id to update.
   *
   * @return the recipient id to update.
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the new recipient display name, if present.
   *
   * @return the new recipient display name, if present.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the new metadata attached to the recipient, if present.
   *
   * @return the new metadata attached to the recipient, if present.
   */
  public Object getMetadata() {
    return metadata;
  }

  /**
   * Builder for {@link UpdateRecipientParams}.
   */
  public static final class Builder {
    /**
     * Recipient id to update.
     */
    private String id;

    /**
     * Optional new recipient display name.
     */
    private String name;

    /**
     * Optional new metadata attached to the recipient.
     */
    private Object metadata;

    /**
     * Creates a builder for update recipient parameters.
     */
    public Builder() {
    }

    /**
     * Sets the recipient id to update.
     *
     * @param id the recipient id to update.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Sets the new recipient display name. Envelopes created earlier keep the
     * name the recipient had at creation time.
     *
     * @param name the new recipient display name.
     * @return this builder.
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the new metadata attached to the recipient.
     *
     * @param metadata the new metadata attached to the recipient.
     * @return this builder.
     */
    public Builder metadata(Object metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Builds the update recipient parameters.
     *
     * @return the update recipient parameters.
     */
    public UpdateRecipientParams build() {
      return new UpdateRecipientParams(this);
    }
  }
}
