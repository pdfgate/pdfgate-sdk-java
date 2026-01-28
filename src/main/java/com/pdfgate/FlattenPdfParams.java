package com.pdfgate;

/**
 * Parameters for flattening a PDF by file or document ID.
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

    public static Builder builder() {
        return new Builder();
    }

    public FileParam getFile() {
        return file;
    }

    public String getDocumentId() {
        return documentId;
    }

    public Boolean getJsonResponse() {
        return jsonResponse;
    }

    public boolean isJsonResponse() {
        return Boolean.TRUE.equals(jsonResponse);
    }

    public Long getPreSignedUrlExpiresIn() {
        return preSignedUrlExpiresIn;
    }

    public Object getMetadata() {
        return metadata;
    }

    public static final class Builder {
        private FileParam file;
        private String documentId;
        private Boolean jsonResponse;
        private Long preSignedUrlExpiresIn;
        private Object metadata;

        private Builder() {
        }

        public Builder file(FileParam file) {
            this.file = file;
            return this;
        }

        public Builder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public Builder preSignedUrlExpiresIn(Long preSignedUrlExpiresIn) {
            this.preSignedUrlExpiresIn = preSignedUrlExpiresIn;
            return this;
        }

        public Builder metadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        public FlattenPdfBytesParams buildBytes() {
            this.jsonResponse = false;
            return new FlattenPdfBytesParams(this);
        }

        public FlattenPdfJsonParams buildJson() {
            this.jsonResponse = true;
            return new FlattenPdfJsonParams(this);
        }
    }
}
