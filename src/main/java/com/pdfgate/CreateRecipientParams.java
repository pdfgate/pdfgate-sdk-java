package com.pdfgate;

/**
 * Parameters for creating a stored recipient.
 */
public final class CreateRecipientParams {
  /**
   * Recipient email address.
   */
  private final String email;

  /**
   * Optional recipient display name.
   */
  private final String name;

  /**
   * Optional metadata attached to the recipient.
   */
  private final Object metadata;

  private CreateRecipientParams(Builder builder) {
    this.email = builder.email;
    this.name = builder.name;
    this.metadata = builder.metadata;
  }

  /**
   * Creates a new builder for create recipient parameters.
   *
   * @return the builder for create recipient parameters.
   */
  public static Builder builder() {
    return new Builder();
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
   * Returns the recipient display name, if present.
   *
   * @return the recipient display name, if present.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns metadata attached to the recipient, if present.
   *
   * @return metadata attached to the recipient, if present.
   */
  public Object getMetadata() {
    return metadata;
  }

  /**
   * Builder for {@link CreateRecipientParams}.
   */
  public static final class Builder {
    /**
     * Recipient email address.
     */
    private String email;

    /**
     * Optional recipient display name.
     */
    private String name;

    /**
     * Optional metadata attached to the recipient.
     */
    private Object metadata;

    /**
     * Creates a builder for create recipient parameters.
     */
    public Builder() {
    }

    /**
     * Sets the recipient email address. The email is stored lowercased and
     * cannot be changed after creation. Emails are not unique: every call
     * creates a new recipient.
     *
     * @param email the recipient email address.
     * @return this builder.
     */
    public Builder email(String email) {
      this.email = email;
      return this;
    }

    /**
     * Sets the recipient display name.
     *
     * @param name the recipient display name.
     * @return this builder.
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets metadata attached to the recipient.
     *
     * @param metadata metadata attached to the recipient.
     * @return this builder.
     */
    public Builder metadata(Object metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Builds the create recipient parameters.
     *
     * @return the create recipient parameters.
     */
    public CreateRecipientParams build() {
      return new CreateRecipientParams(this);
    }
  }
}
