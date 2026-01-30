package com.pdfgate;

/**
 * Parameters for extracting PDF form data from a file or document id.
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
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the file payload if extracting by file.
     */
    public FileParam getFile() {
        return file;
    }

    /**
     * Returns the document id if extracting by document id.
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
         * Sets the file payload for extraction.
         */
        public Builder file(FileParam file) {
            this.file = file;
            return this;
        }

        /**
         * Sets the document id for extraction.
         */
        public Builder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        /**
         * Builds the extract PDF form data parameters.
         */
        public ExtractPdfFormDataParams build() {
            return new ExtractPdfFormDataParams(this);
        }
    }
}
