package com.pdfgate;

/**
 * Parameters for uploading a raw PDF file or URL to PDFGate.
 *
 * <p>Provide either {@code file} or {@code url}. If {@code file} is provided, it is
 * prioritized and sent as multipart form data.
 */
public final class UploadFileParams {
  private final FileParam file;
  private final String url;
  private final Object metadata;
  private final Long preSignedUrlExpiresIn;

  /**
   * Initializes upload file parameters from the builder.
   *
   * @param builder builder with configured values.
   */
  private UploadFileParams(Builder builder) {
    this.file = builder.file;
    this.url = builder.url;
    this.metadata = builder.metadata;
    this.preSignedUrlExpiresIn = builder.preSignedUrlExpiresIn;
  }

  /**
   * Creates a new builder for upload file parameters.
   *
   * @return the builder for upload file parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the file payload to upload, if provided.
   *
   * @return the file payload to upload, if provided.
   */
  public FileParam getFile() {
    return file;
  }

  /**
   * Returns the URL to upload, if provided.
   *
   * @return the URL to upload, if provided.
   */
  public String getUrl() {
    return url;
  }

  /**
   * Returns metadata to attach to the document.
   *
   * @return metadata to attach to the document.
   */
  public Object getMetadata() {
    return metadata;
  }

  /**
   * Returns the pre-signed URL expiration time in seconds, if provided.
   *
   * @return the pre-signed URL expiration time in seconds, if provided.
   */
  public Long getPreSignedUrlExpiresIn() {
    return preSignedUrlExpiresIn;
  }

  /**
   * Builder for {@link UploadFileParams}.
   */
  public static final class Builder {
    private FileParam file;
    private String url;
    private Object metadata;
    private Long preSignedUrlExpiresIn;

    private Builder() {
    }

    /**
     * Sets the file to upload.
     *
     * @param file file payload to upload.
     * @return this builder.
     */
    public Builder file(FileParam file) {
      this.file = file;
      return this;
    }

    /**
     * Sets the URL of the file to upload.
     *
     * @param url URL of the file to upload.
     * @return this builder.
     */
    public Builder url(String url) {
      this.url = url;
      return this;
    }

    /**
     * Sets metadata to attach to the document.
     *
     * @param metadata metadata to attach to the document.
     * @return this builder.
     */
    public Builder metadata(Object metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets the pre-signed URL expiration time in seconds.
     *
     * @param preSignedUrlExpiresIn expiration time in seconds.
     * @return this builder.
     */
    public Builder preSignedUrlExpiresIn(Long preSignedUrlExpiresIn) {
      this.preSignedUrlExpiresIn = preSignedUrlExpiresIn;
      return this;
    }

    /**
     * Builds upload file parameters.
     *
     * @return upload file parameters.
     */
    public UploadFileParams build() {
      return new UploadFileParams(this);
    }
  }
}
