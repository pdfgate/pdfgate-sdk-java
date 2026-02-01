package com.pdfgate;

/**
 * Parameters for retrieving a document's metadata.
 *
 * <p>Use {@code preSignedUrlExpiresIn} to request a fresh temporary download URL.
 */
public final class GetDocumentParams {
  /**
   * Document id to retrieve.
   */
  private final String documentId;
  /**
   * Optional pre-signed URL expiry in seconds.
   */
  private final Long preSignedUrlExpiresIn;

  private GetDocumentParams(Builder builder) {
    this.documentId = builder.documentId;
    this.preSignedUrlExpiresIn = builder.preSignedUrlExpiresIn;
  }

  /**
   * Creates a new builder for get document parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the document id to retrieve.
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Returns the pre-signed URL expiry in seconds, if provided.
   */
  public Long getPreSignedUrlExpiresIn() {
    return preSignedUrlExpiresIn;
  }

  /**
   * Builder for {@link GetDocumentParams}.
   */
  public static final class Builder {
    /**
     * Document id to retrieve.
     */
    private String documentId;
    /**
     * Optional pre-signed URL expiry in seconds.
     */
    private Long preSignedUrlExpiresIn;

    /**
     * Sets the document id to retrieve.
     */
    public Builder documentId(String documentId) {
      this.documentId = documentId;
      return this;
    }

    /**
     * Sets the pre-signed URL expiry in seconds.
     */
    public Builder preSignedUrlExpiresIn(Long preSignedUrlExpiresIn) {
      this.preSignedUrlExpiresIn = preSignedUrlExpiresIn;
      return this;
    }

    /**
     * Builds the get document parameters.
     */
    public GetDocumentParams build() {
      return new GetDocumentParams(this);
    }
  }
}
