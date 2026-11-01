package com.pdfgate;

/**
 * Parameters for removing the stored signature of a recipient (automatic signing).
 */
public final class RemoveRecipientSignatureParams {
  /**
   * Recipient id to remove the stored signature of.
   */
  private final String id;

  private RemoveRecipientSignatureParams(Builder builder) {
    this.id = builder.id;
  }

  /**
   * Creates a new builder for remove recipient signature parameters.
   *
   * @return the builder for remove recipient signature parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the recipient id to remove the stored signature of.
   *
   * @return the recipient id to remove the stored signature of.
   */
  public String getId() {
    return id;
  }

  /**
   * Builder for {@link RemoveRecipientSignatureParams}.
   */
  public static final class Builder {
    /**
     * Recipient id to remove the stored signature of.
     */
    private String id;

    /**
     * Creates a builder for remove recipient signature parameters.
     */
    public Builder() {
    }

    /**
     * Sets the recipient id to remove the stored signature of.
     *
     * @param id the recipient id to remove the stored signature of.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Builds the remove recipient signature parameters.
     *
     * @return the remove recipient signature parameters.
     */
    public RemoveRecipientSignatureParams build() {
      return new RemoveRecipientSignatureParams(this);
    }
  }
}
