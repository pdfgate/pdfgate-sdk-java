package com.pdfgate;

/**
 * Parameters for compressing a PDF by file or document ID.
 */
public abstract class CompressPdfParams {
    private final FileParam file;
    private final String documentId;
    private final Boolean linearize;
    private final Boolean jsonResponse;
    private final Long preSignedUrlExpiresIn;
    private final Object metadata;

    protected CompressPdfParams(Builder builder) {
        this.file = builder.file;
        this.documentId = builder.documentId;
        this.linearize = builder.linearize;
        this.jsonResponse = builder.jsonResponse;
        this.preSignedUrlExpiresIn = builder.preSignedUrlExpiresIn;
        this.metadata = builder.metadata;
    }

    /**
     * Creates a new builder for compress PDF parameters.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the PDF file payload when compressing by file.
     */
    public FileParam getFile() {
        return file;
    }

    /**
     * Returns the document ID when compressing by document ID.
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Returns whether the PDF should be linearized.
     */
    public Boolean getLinearize() {
        return linearize;
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
     * Builder for {@link CompressPdfParams}.
     */
    public static final class Builder {
        private FileParam file;
        private String documentId;
        private Boolean linearize;
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
         * Enables linearized PDF output when set to true.
         */
        public Builder linearize(Boolean linearize) {
            this.linearize = linearize;
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
         * Builds compress PDF parameters for bytes responses.
         */
        public CompressPdfBytesParams buildBytes() {
            this.jsonResponse = false;
            return new CompressPdfBytesParams(this);
        }

        /**
         * Builds compress PDF parameters for JSON responses.
         */
        public CompressPdfJsonParams buildJson() {
            this.jsonResponse = true;
            return new CompressPdfJsonParams(this);
        }
    }
}
