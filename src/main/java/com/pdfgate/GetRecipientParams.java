package com.pdfgate;

/**
 * Parameters for retrieving a stored recipient.
 */
public final class GetRecipientParams {
  /**
   * Recipient id to retrieve.
   */
  private final String id;

  private GetRecipientParams(Builder builder) {
    this.id = builder.id;
  }

  /**
   * Creates a new builder for get recipient parameters.
   *
   * @return the builder for get recipient parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the recipient id to retrieve.
   *
   * @return the recipient id to retrieve.
   */
  public String getId() {
    return id;
  }

  /**
   * Builder for {@link GetRecipientParams}.
   */
  public static final class Builder {
    /**
     * Recipient id to retrieve.
     */
    private String id;

    /**
     * Creates a builder for get recipient parameters.
     */
    public Builder() {
    }

    /**
     * Sets the recipient id to retrieve.
     *
     * @param id the recipient id to retrieve.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Builds the get recipient parameters.
     *
     * @return the get recipient parameters.
     */
    public GetRecipientParams build() {
      return new GetRecipientParams(this);
    }
  }
}
