package com.pdfgate;

/**
 * Parameters for requesting a stored signature from a recipient (automatic signing).
 */
public final class RequestRecipientSignatureParams {
  /**
   * Recipient id to request a stored signature from.
   */
  private final String id;

  private RequestRecipientSignatureParams(Builder builder) {
    this.id = builder.id;
  }

  /**
   * Creates a new builder for request recipient signature parameters.
   *
   * @return the builder for request recipient signature parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the recipient id to request a stored signature from.
   *
   * @return the recipient id to request a stored signature from.
   */
  public String getId() {
    return id;
  }

  /**
   * Builder for {@link RequestRecipientSignatureParams}.
   */
  public static final class Builder {
    /**
     * Recipient id to request a stored signature from.
     */
    private String id;

    /**
     * Creates a builder for request recipient signature parameters.
     */
    public Builder() {
    }

    /**
     * Sets the recipient id to request a stored signature from.
     *
     * @param id the recipient id to request a stored signature from.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Builds the request recipient signature parameters.
     *
     * @return the request recipient signature parameters.
     */
    public RequestRecipientSignatureParams build() {
      return new RequestRecipientSignatureParams(this);
    }
  }
}
