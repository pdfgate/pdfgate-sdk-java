package com.pdfgate;

/**
 * Parameters for sending an envelope to its recipients.
 */
public final class SendEnvelopeParams {
  /**
   * Envelope id to send.
   */
  private final String id;

  private SendEnvelopeParams(Builder builder) {
    this.id = builder.id;
  }

  /**
   * Creates a new builder for send envelope parameters.
   *
   * @return the builder for send envelope parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the envelope id to send.
   *
   * @return the envelope id to send.
   */
  public String getId() {
    return id;
  }

  /**
   * Builder for {@link SendEnvelopeParams}.
   */
  public static final class Builder {
    /**
     * Envelope id to send.
     */
    private String id;

    /**
     * Creates a builder for send envelope parameters.
     */
    public Builder() {
    }

    /**
     * Sets the envelope id to send.
     *
     * @param id the envelope id to send.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Builds the send envelope parameters.
     *
     * @return the send envelope parameters.
     */
    public SendEnvelopeParams build() {
      return new SendEnvelopeParams(this);
    }
  }
}
