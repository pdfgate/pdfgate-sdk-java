package com.pdfgate;

/**
 * Parameters for retrieving an envelope by id.
 */
public final class GetEnvelopeParams {
  /**
   * Envelope id to retrieve.
   */
  private final String id;

  private GetEnvelopeParams(Builder builder) {
    this.id = builder.id;
  }

  /**
   * Creates a new builder for get envelope parameters.
   *
   * @return the builder for get envelope parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the envelope id to retrieve.
   *
   * @return the envelope id to retrieve.
   */
  public String getId() {
    return id;
  }

  /**
   * Builder for {@link GetEnvelopeParams}.
   */
  public static final class Builder {
    /**
     * Envelope id to retrieve.
     */
    private String id;

    /**
     * Creates a builder for get envelope parameters.
     */
    public Builder() {
    }

    /**
     * Sets the envelope id to retrieve.
     *
     * @param id the envelope id to retrieve.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Builds the get envelope parameters.
     *
     * @return the get envelope parameters.
     */
    public GetEnvelopeParams build() {
      return new GetEnvelopeParams(this);
    }
  }
}
