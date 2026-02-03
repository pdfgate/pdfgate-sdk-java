package com.pdfgate;

/**
 * Parameters for extracting PDF form data from a file or an already uploaded document by ID.
 *
 * <p>Provide either {@link FileParam} or {@code documentId}. The response is always JSON.
 */
public final class ExtractPdfFormDataParams {
  /**
   * PDF file payload when extracting by file.
   */
  private final FileParam file;
  /**
   * Document id when extracting by document id.
   */
  private final String documentId;

  private ExtractPdfFormDataParams(Builder builder) {
    this.file = builder.file;
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
   * Returns the file payload if extracting by file.
   *
   * @return the file payload if extracting by file.
   */
  public FileParam getFile() {
    return file;
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
     * PDF file payload when extracting by file.
     */
    private FileParam file;
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
     * Sets the file payload for extraction.
     *
     * @param file the PDF file payload.
     * @return this builder.
     */
    public Builder file(FileParam file) {
      this.file = file;
      return this;
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
