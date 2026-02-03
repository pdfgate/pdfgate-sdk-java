package com.pdfgate;

import com.google.gson.annotations.SerializedName;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Document metadata returned by JSON responses from the PDFGate API.
 */
public final class PdfGateDocument {
  private String id;
  private DocumentStatus status;
  private Instant createdAt;
  private Instant expiresAt;
  private DocumentType type;
  private String fileUrl;
  private Long size;
  private Map<String, Object> metadata;
  private String derivedFrom;

  /**
   * Creates an empty document instance for JSON deserialization.
   */
  public PdfGateDocument() {
  }

  /**
   * Returns the document identifier.
   *
   * @return the document identifier.
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the document processing status.
   *
   * @return the document processing status.
   */
  public DocumentStatus getStatus() {
    return status;
  }

  /**
   * Returns when the document was created.
   *
   * @return when the document was created.
   */
  public Instant getCreatedAt() {
    return createdAt;
  }

  /**
   * Returns when the document expires, if present.
   *
   * @return when the document expires, if present.
   */
  public Instant getExpiresAt() {
    return expiresAt;
  }

  /**
   * Returns the document type.
   *
   * @return the document type.
   */
  public DocumentType getType() {
    return type;
  }

  /**
   * Returns a temporary file URL if provided by the API.
   *
   * @return a temporary file URL if provided by the API.
   */
  public Optional<String> getFileUrl() {
    return Optional.ofNullable(fileUrl);
  }

  /**
   * Returns the file size in bytes, if provided.
   *
   * @return the file size in bytes, if provided.
   */
  public Long getSize() {
    return size;
  }

  /**
   * Returns metadata attached to the document, if present.
   *
   * @return metadata attached to the document, if present.
   */
  public Optional<Map<String, Object>> getMetadata() {
    return Optional.ofNullable(metadata);
  }

  /**
   * Returns the source document ID when derived from another document.
   *
   * @return the source document ID when derived from another document.
   */
  public Optional<String> getDerivedFrom() {
    return Optional.ofNullable(derivedFrom);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PdfGateDocument that = (PdfGateDocument) o;
    return Objects.equals(id, that.id)
        && status == that.status
        && Objects.equals(createdAt, that.createdAt)
        && Objects.equals(expiresAt, that.expiresAt)
        && type == that.type
        && Objects.equals(fileUrl, that.fileUrl)
        && Objects.equals(size, that.size)
        && Objects.equals(metadata, that.metadata)
        && Objects.equals(derivedFrom, that.derivedFrom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, createdAt, expiresAt, type, fileUrl, size, metadata,
        derivedFrom);
  }

  /**
   * Status values returned by the API for document processing.
   */
  public enum DocumentStatus {
    /**
     * The document is finished and available.
     */
    @SerializedName("completed")
    COMPLETED,
    /**
     * The document is still processing.
     */
    @SerializedName("processing")
    PROCESSING,
    /**
     * The document has expired and is no longer available.
     */
    @SerializedName("expired")
    EXPIRED,
    /**
     * The document failed to process.
     */
    @SerializedName("failed")
    FAILED
  }

  /**
   * Document type values returned by the API.
   */
  public enum DocumentType {
    /**
     * Document generated from HTML or URL.
     */
    @SerializedName("from_html")
    FROM_HTML,
    /**
     * Document created by flattening a PDF.
     */
    @SerializedName("flattened")
    FLATTENED,
    /**
     * Document created by applying a watermark.
     */
    @SerializedName("watermarked")
    WATERMARKED,
    /**
     * Document created by encryption.
     */
    @SerializedName("encrypted")
    ENCRYPTED,
    /**
     * Document created by compression.
     */
    @SerializedName("compressed")
    COMPRESSED,
    /**
     * Document created by signing.
     */
    @SerializedName("signed")
    SIGNED
  }
}
