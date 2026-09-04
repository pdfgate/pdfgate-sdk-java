package com.pdfgate;

/**
 * Parameters for voiding (cancelling) an envelope.
 */
public final class VoidEnvelopeParams {
  /**
   * Envelope id to void.
   */
  private final String id;

  /**
   * Optional reason for voiding, visible to recipients.
   */
  private final String reason;

  private VoidEnvelopeParams(Builder builder) {
    this.id = builder.id;
    this.reason = builder.reason;
  }

  /**
   * Creates a new builder for void envelope parameters.
   *
   * @return the builder for void envelope parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the envelope id to void.
   *
   * @return the envelope id to void.
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the reason for voiding, if present.
   *
   * @return the reason for voiding, if present.
   */
  public String getReason() {
    return reason;
  }

  /**
   * Builder for {@link VoidEnvelopeParams}.
   */
  public static final class Builder {
    /**
     * Envelope id to void.
     */
    private String id;

    /**
     * Optional reason for voiding, visible to recipients.
     */
    private String reason;

    /**
     * Creates a builder for void envelope parameters.
     */
    public Builder() {
    }

    /**
     * Sets the envelope id to void.
     *
     * @param id the envelope id to void.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Sets the reason for voiding (max 500 characters). The reason is visible
     * to recipients: it is included in the cancellation email sent to
     * recipients who had not signed yet.
     *
     * @param reason the reason for voiding.
     * @return this builder.
     */
    public Builder reason(String reason) {
      this.reason = reason;
      return this;
    }

    /**
     * Builds the void envelope parameters.
     *
     * @return the void envelope parameters.
     */
    public VoidEnvelopeParams build() {
      return new VoidEnvelopeParams(this);
    }
  }
}
