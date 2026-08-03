package com.pdfgate;

/**
 * Parameters for deleting a stored document by ID.
 */
public final class DeleteDocumentParams {
  private final String documentId;

  private DeleteDocumentParams(Builder builder) {
    this.documentId = builder.documentId;
  }

  /**
   * Creates a new builder for delete document parameters.
   *
   * @return the builder for delete document parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the document ID to delete.
   *
   * @return the document ID to delete.
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Builder for {@link DeleteDocumentParams}.
   */
  public static final class Builder {
    private String documentId;

    private Builder() {
    }

    /**
     * Sets the document ID to delete.
     *
     * @param documentId the document ID.
     * @return this builder.
     */
    public Builder documentId(String documentId) {
      this.documentId = documentId;
      return this;
    }

    /**
     * Builds the delete document parameters.
     *
     * @return the delete document parameters.
     */
    public DeleteDocumentParams build() {
      return new DeleteDocumentParams(this);
    }
  }
}
