package com.pdfgate;

/**
 * Recipient parameters for a document inside a create envelope request.
 */
public final class EnvelopeRecipient {
  private final String email;
  private final String name;
  private final String role;

  /**
   * Initializes envelope recipient parameters from the builder.
   *
   * @param builder builder with configured values.
   */
  private EnvelopeRecipient(Builder builder) {
    this.email = builder.email;
    this.name = builder.name;
    this.role = builder.role;
  }

  /**
   * Creates a new builder for envelope recipient parameters.
   *
   * @return the builder for envelope recipient parameters.
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
   * Returns the recipient display name.
   *
   * @return the recipient display name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the recipient role, if present.
   *
   * @return the recipient role, if present.
   */
  public String getRole() {
    return role;
  }

  /**
   * Builder for {@link EnvelopeRecipient}.
   */
  public static final class Builder {
    private String email;
    private String name;
    private String role;

    private Builder() {
    }

    /**
     * Sets the recipient email address.
     *
     * @param email recipient email address.
     * @return this builder.
     */
    public Builder email(String email) {
      this.email = email;
      return this;
    }

    /**
     * Sets the recipient display name.
     *
     * @param name recipient display name.
     * @return this builder.
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the recipient role.
     *
     * @param role recipient role.
     * @return this builder.
     */
    public Builder role(String role) {
      this.role = role;
      return this;
    }

    /**
     * Builds the envelope recipient parameters.
     *
     * @return the envelope recipient parameters.
     */
    public EnvelopeRecipient build() {
      return new EnvelopeRecipient(this);
    }
  }
}
