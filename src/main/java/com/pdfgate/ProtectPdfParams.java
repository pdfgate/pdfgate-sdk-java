package com.pdfgate;

import com.google.gson.annotations.SerializedName;

/**
 * Parameters for protecting a PDF by file or document ID.
 *
 * <p>Use {@link EncryptionAlgorithm} to select AES256 (default) or AES128. Set
 * {@code userPassword} and {@code ownerPassword} to control access and permissions,
 * and use the disable flags to restrict printing, copying, or editing.
 */
public abstract class ProtectPdfParams {
  private final FileParam file;
  private final String documentId;
  private final EncryptionAlgorithm algorithm;
  private final String userPassword;
  private final String ownerPassword;
  private final Boolean disablePrint;
  private final Boolean disableCopy;
  private final Boolean disableEditing;
  private final Boolean encryptMetadata;
  private final Boolean jsonResponse;
  private final Long preSignedUrlExpiresIn;
  private final Object metadata;

  protected ProtectPdfParams(Builder builder) {
    this.file = builder.file;
    this.documentId = builder.documentId;
    this.algorithm = builder.algorithm;
    this.userPassword = builder.userPassword;
    this.ownerPassword = builder.ownerPassword;
    this.disablePrint = builder.disablePrint;
    this.disableCopy = builder.disableCopy;
    this.disableEditing = builder.disableEditing;
    this.encryptMetadata = builder.encryptMetadata;
    this.jsonResponse = builder.jsonResponse;
    this.preSignedUrlExpiresIn = builder.preSignedUrlExpiresIn;
    this.metadata = builder.metadata;
  }

  /**
   * Creates a new builder for protect PDF parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the PDF file payload when protecting by file.
   */
  public FileParam getFile() {
    return file;
  }

  /**
   * Returns the document id when protecting by document id.
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Returns the encryption algorithm.
   */
  public EncryptionAlgorithm getAlgorithm() {
    return algorithm;
  }

  /**
   * Returns the user password, if provided.
   */
  public String getUserPassword() {
    return userPassword;
  }

  /**
   * Returns the owner password, if provided.
   */
  public String getOwnerPassword() {
    return ownerPassword;
  }

  /**
   * Returns whether printing is disabled.
   */
  public Boolean getDisablePrint() {
    return disablePrint;
  }

  /**
   * Returns whether copying is disabled.
   */
  public Boolean getDisableCopy() {
    return disableCopy;
  }

  /**
   * Returns whether editing is disabled.
   */
  public Boolean getDisableEditing() {
    return disableEditing;
  }

  /**
   * Returns whether metadata is encrypted.
   */
  public Boolean getEncryptMetadata() {
    return encryptMetadata;
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
   * Supported encryption algorithms.
   */
  public enum EncryptionAlgorithm {
    @SerializedName("AES256")
    AES256("AES256"),
    @SerializedName("AES128")
    AES128("AES128");

    private final String value;

    EncryptionAlgorithm(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  /**
   * Builder for {@link ProtectPdfParams}.
   */
  public static final class Builder {
    private FileParam file;
    private String documentId;
    private EncryptionAlgorithm algorithm;
    private String userPassword;
    private String ownerPassword;
    private Boolean disablePrint;
    private Boolean disableCopy;
    private Boolean disableEditing;
    private Boolean encryptMetadata;
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
     * Sets the document id for the source PDF.
     */
    public Builder documentId(String documentId) {
      this.documentId = documentId;
      return this;
    }

    /**
     * Sets the encryption algorithm.
     */
    public Builder algorithm(EncryptionAlgorithm algorithm) {
      this.algorithm = algorithm;
      return this;
    }

    /**
     * Sets the user password required to open the PDF.
     */
    public Builder userPassword(String userPassword) {
      this.userPassword = userPassword;
      return this;
    }

    /**
     * Sets the owner password with full control.
     */
    public Builder ownerPassword(String ownerPassword) {
      this.ownerPassword = ownerPassword;
      return this;
    }

    /**
     * Disables printing when set to true.
     */
    public Builder disablePrint(Boolean disablePrint) {
      this.disablePrint = disablePrint;
      return this;
    }

    /**
     * Disables copying when set to true.
     */
    public Builder disableCopy(Boolean disableCopy) {
      this.disableCopy = disableCopy;
      return this;
    }

    /**
     * Disables editing when set to true.
     */
    public Builder disableEditing(Boolean disableEditing) {
      this.disableEditing = disableEditing;
      return this;
    }

    /**
     * Encrypts metadata when set to true.
     */
    public Builder encryptMetadata(Boolean encryptMetadata) {
      this.encryptMetadata = encryptMetadata;
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
     * Builds protect PDF parameters for bytes responses.
     */
    public ProtectPdfFileParams buildWithFileResponse() {
      this.jsonResponse = false;
      return new ProtectPdfFileParams(this);
    }

    /**
     * Builds protect PDF parameters for JSON responses.
     */
    public ProtectPdfJsonParams buildWithJsonResponse() {
      this.jsonResponse = true;
      return new ProtectPdfJsonParams(this);
    }
  }
}
