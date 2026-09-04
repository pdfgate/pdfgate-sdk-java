package com.pdfgate;

import java.util.List;

/**
 * Parameters for creating a signing envelope.
 */
public final class CreateEnvelopeParams {
  private final List<EnvelopeDocument> documents;
  private final String requesterName;
  private final Object metadata;
  private final Integer expiresInDays;

  /**
   * Initializes create envelope parameters from the builder.
   *
   * @param builder builder with configured values.
   */
  private CreateEnvelopeParams(Builder builder) {
    this.documents = builder.documents;
    this.requesterName = builder.requesterName;
    this.metadata = builder.metadata;
    this.expiresInDays = builder.expiresInDays;
  }

  /**
   * Creates a new builder for create envelope parameters.
   *
   * @return the builder for create envelope parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the envelope documents.
   *
   * @return the envelope documents.
   */
  public List<EnvelopeDocument> getDocuments() {
    return documents;
  }

  /**
   * Returns the requester name.
   *
   * @return the requester name.
   */
  public String getRequesterName() {
    return requesterName;
  }

  /**
   * Returns metadata attached to the envelope, if present.
   *
   * @return metadata attached to the envelope, if present.
   */
  public Object getMetadata() {
    return metadata;
  }

  /**
   * Returns days until the envelope and its signing links expire, if present.
   *
   * @return days until the envelope expires, if present.
   */
  public Integer getExpiresInDays() {
    return expiresInDays;
  }

  /**
   * Builder for {@link CreateEnvelopeParams}.
   */
  public static final class Builder {
    private List<EnvelopeDocument> documents;
    private String requesterName;
    private Object metadata;
    private Integer expiresInDays;

    private Builder() {
    }

    /**
     * Sets the envelope documents.
     *
     * @param documents envelope documents.
     * @return this builder.
     */
    public Builder documents(List<EnvelopeDocument> documents) {
      this.documents = documents;
      return this;
    }

    /**
     * Sets the requester name.
     *
     * @param requesterName requester name.
     * @return this builder.
     */
    public Builder requesterName(String requesterName) {
      this.requesterName = requesterName;
      return this;
    }

    /**
     * Sets metadata attached to the envelope.
     *
     * @param metadata metadata attached to the envelope.
     * @return this builder.
     */
    public Builder metadata(Object metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets how many days until the envelope and its signing links expire,
     * counted from creation (min 1, max 90). Defaults to the account's
     * envelope expiration setting when not set.
     *
     * @param expiresInDays days until the envelope expires.
     * @return this builder.
     */
    public Builder expiresInDays(Integer expiresInDays) {
      this.expiresInDays = expiresInDays;
      return this;
    }

    /**
     * Builds the create envelope parameters.
     *
     * @return the create envelope parameters.
     */
    public CreateEnvelopeParams build() {
      return new CreateEnvelopeParams(this);
    }
  }
}
