package com.pdfgate;

import com.google.gson.annotations.SerializedName;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class PdfGateDocument {
    public enum DocumentStatus {
        @SerializedName("completed")
        COMPLETED,
        @SerializedName("processing")
        PROCESSING,
        @SerializedName("expired")
        EXPIRED,
        @SerializedName("failed")
        FAILED
    }

    public enum DocumentType {
        @SerializedName("from_html")
        FROM_HTML,
        @SerializedName("flattened")
        FLATTENED,
        @SerializedName("watermarked")
        WATERMARKED,
        @SerializedName("encrypted")
        ENCRYPTED,
        @SerializedName("compressed")
        COMPRESSED,
        @SerializedName("signed")
        SIGNED
    }

    private String id;
    private DocumentStatus status;
    private Instant createdAt;
    private Instant expiresAt;
    private DocumentType type;
    private String fileUrl;
    private Long size;
    private Map<String, Object> metadata;
    private String derivedFrom;

    public String getId() {
        return id;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public DocumentType getType() {
        return type;
    }

    public Optional<String> getFileUrl() {
        return Optional.ofNullable(fileUrl);
    }

    public Long getSize() {
        return size;
    }

    public Optional<Map<String, Object>> getMetadata() {
        return Optional.ofNullable(metadata);
    }

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
        return Objects.hash(id, status, createdAt, expiresAt, type, fileUrl, size, metadata, derivedFrom);
    }
}
