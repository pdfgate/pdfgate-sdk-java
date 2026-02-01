package com.pdfgate;

/**
 * Parameters for flattening a PDF by file or document ID.
 *
 * <p>Flattening converts interactive fields/annotations into a static PDF. Provide either
 * {@link FileParam} or {@code documentId}. Use {@link Builder#buildWithFileResponse()} for raw
 * bytes or {@link Builder#buildWithJsonResponse()} for a {@link PdfGateDocument}.
 */
public abstract class FlattenPdfParams {
  private final FileParam file;
  private final String documentId;
  private final Boolean jsonResponse;
  private final Long preSignedUrlExpiresIn;
  private final Object metadata;

  protected FlattenPdfParams(Builder builder) {
    this.file = builder.file;
    this.documentId = builder.documentId;
    this.jsonResponse = builder.jsonResponse;
    this.preSignedUrlExpiresIn = builder.preSignedUrlExpiresIn;
    this.metadata = builder.metadata;
  }

  /**
   * Creates a new builder for flatten PDF parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the PDF file payload when flattening by file.
   */
  public FileParam getFile() {
    return file;
  }

  /**
   * Returns the document ID when flattening by document ID.
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Returns whether the response is JSON.
   */
  public Boolean getJsonResponse() {
    return jsonResponse;
  }

  /**
   * Returns whether the response is JSON.
   */
  public boolean isJsonResponse() {
    return Boolean.TRUE.equals(jsonResponse);
  }

  /**
   * Returns the pre-signed URL expiration time in seconds.
   */
  public Long getPreSignedUrlExpiresIn() {
    return preSignedUrlExpiresIn;
  }

  /**
   * Returns metadata to attach to the document.
   */
  public Object getMetadata() {
    return metadata;
  }

  /**
   * Builder for {@link FlattenPdfParams}.
   */
  public static final class Builder {
    private FileParam file;
    private String documentId;
    private Boolean jsonResponse;
    private Long preSignedUrlExpiresIn;
    private Object metadata;

    private Builder() {
    }

    /**
     * Sets the PDF file payload.
     */
    public Builder file(FileParam file) {
      this.file = file;
      return this;
    }

    /**
     * Sets the document ID for the source PDF.
     */
    public Builder documentId(String documentId) {
      this.documentId = documentId;
      return this;
    }

    /**
     * Sets the pre-signed URL expiration time in seconds.
     */
    public Builder preSignedUrlExpiresIn(Long preSignedUrlExpiresIn) {
      this.preSignedUrlExpiresIn = preSignedUrlExpiresIn;
      return this;
    }

    /**
     * Sets metadata to attach to the document.
     */
    public Builder metadata(Object metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Builds flatten PDF parameters for bytes responses.
     */
    public FlattenPdfFileParams buildWithFileResponse() {
      this.jsonResponse = false;
      return new FlattenPdfFileParams(this);
    }

    /**
     * Builds flatten PDF parameters for JSON responses.
     */
    public FlattenPdfJsonParams buildWithJsonResponse() {
      this.jsonResponse = true;
      return new FlattenPdfJsonParams(this);
    }
  }
}
