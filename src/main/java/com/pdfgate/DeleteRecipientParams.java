package com.pdfgate;

/**
 * Parameters for deleting a stored recipient.
 */
public final class DeleteRecipientParams {
  /**
   * Recipient id to delete.
   */
  private final String id;

  private DeleteRecipientParams(Builder builder) {
    this.id = builder.id;
  }

  /**
   * Creates a new builder for delete recipient parameters.
   *
   * @return the builder for delete recipient parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the recipient id to delete.
   *
   * @return the recipient id to delete.
   */
  public String getId() {
    return id;
  }

  /**
   * Builder for {@link DeleteRecipientParams}.
   */
  public static final class Builder {
    /**
     * Recipient id to delete.
     */
    private String id;

    /**
     * Creates a builder for delete recipient parameters.
     */
    public Builder() {
    }

    /**
     * Sets the recipient id to delete.
     *
     * @param id the recipient id to delete.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Builds the delete recipient parameters.
     *
     * @return the delete recipient parameters.
     */
    public DeleteRecipientParams build() {
      return new DeleteRecipientParams(this);
    }
  }
}
