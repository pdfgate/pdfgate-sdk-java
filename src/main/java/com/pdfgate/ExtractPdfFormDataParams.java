package com.pdfgate;

/**
 * Parameters for extracting PDF form data from an already uploaded document by ID.
 *
 * <p>Provide {@code documentId}. The response is always JSON.
 */
public final class ExtractPdfFormDataParams {
  /**
   * Document id when extracting by document id.
   */
  private final String documentId;

  private ExtractPdfFormDataParams(Builder builder) {
    this.documentId = builder.documentId;
  }

  /**
   * Creates a new builder for extract PDF form data parameters.
   *
   * @return the builder for extract PDF form data parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the document ID if extracting by document ID.
   *
   * @return the document ID if extracting by document ID.
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Builder for {@link ExtractPdfFormDataParams}.
   */
  public static final class Builder {
    /**
     * Document id when extracting by document id.
     */
    private String documentId;

    /**
     * Creates a builder for extract PDF form data parameters.
     */
    public Builder() {
    }

    /**
     * Sets the document ID for extraction.
     *
     * @param documentId the document ID.
     * @return this builder.
     */
    public Builder documentId(String documentId) {
      this.documentId = documentId;
      return this;
    }

    /**
     * Builds the extract PDF form data parameters.
     *
     * @return the extract PDF form data parameters.
     */
    public ExtractPdfFormDataParams build() {
      return new ExtractPdfFormDataParams(this);
    }
  }
}
