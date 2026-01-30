package com.pdfgate;

/**
 * Parameters for retrieving a document file.
 */
public final class GetFileParams {
    /**
     * Document id to retrieve.
     */
    private final String documentId;

    private GetFileParams(Builder builder) {
        this.documentId = builder.documentId;
    }

    /**
     * Creates a new builder for get file parameters.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the document id to retrieve.
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Builder for {@link GetFileParams}.
     */
    public static final class Builder {
        /**
         * Document id to retrieve.
         */
        private String documentId;

        /**
         * Sets the document id to retrieve.
         */
        public Builder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        /**
         * Builds the get file parameters.
         */
        public GetFileParams build() {
            return new GetFileParams(this);
        }
    }
}
