package com.pdfgate;

/**
 * Parameters for retrieving a document file.
 *
 * <p>Accessing stored generated files requires enabling "Save files" in the
 * PDFGate Dashboard settings.
 */
public final class GetFileParams {
  /**
   * Document id to retrieve.
   */
  private final String documentId;

  private GetFileParams(Builder builder) {
    this.documentId = builder.documentId;
  }

  /**
   * Creates a new builder for get file parameters.
   *
   * @return the builder for get file parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the document id to retrieve.
   *
   * @return the document id to retrieve.
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Builder for {@link GetFileParams}.
   */
  public static final class Builder {
    /**
     * Document id to retrieve.
     */
    private String documentId;

    /**
     * Creates a builder for get file parameters.
     */
    public Builder() {
    }

    /**
     * Sets the document id to retrieve.
     *
     * @param documentId the document id to retrieve.
     * @return this builder.
     */
    public Builder documentId(String documentId) {
      this.documentId = documentId;
      return this;
    }

    /**
     * Builds the get file parameters.
     *
     * @return the get file parameters.
     */
    public GetFileParams build() {
      return new GetFileParams(this);
    }
  }
}
