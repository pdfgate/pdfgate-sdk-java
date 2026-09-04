package com.pdfgate;

/**
 * Parameters for permanently deleting an envelope.
 */
public final class DeleteEnvelopeParams {
  /**
   * Envelope id to delete.
   */
  private final String id;

  private DeleteEnvelopeParams(Builder builder) {
    this.id = builder.id;
  }

  /**
   * Creates a new builder for delete envelope parameters.
   *
   * @return the builder for delete envelope parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the envelope id to delete.
   *
   * @return the envelope id to delete.
   */
  public String getId() {
    return id;
  }

  /**
   * Builder for {@link DeleteEnvelopeParams}.
   */
  public static final class Builder {
    /**
     * Envelope id to delete.
     */
    private String id;

    /**
     * Creates a builder for delete envelope parameters.
     */
    public Builder() {
    }

    /**
     * Sets the envelope id to delete.
     *
     * @param id the envelope id to delete.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Builds the delete envelope parameters.
     *
     * @return the delete envelope parameters.
     */
    public DeleteEnvelopeParams build() {
      return new DeleteEnvelopeParams(this);
    }
  }
}
