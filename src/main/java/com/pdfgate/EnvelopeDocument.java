package com.pdfgate;

import java.util.List;

/**
 * Document parameters for a create envelope request.
 */
public final class EnvelopeDocument {
  private final String sourceDocumentId;
  private final String name;
  private final List<EnvelopeRecipient> recipients;

  /**
   * Initializes envelope document parameters from the builder.
   *
   * @param builder builder with configured values.
   */
  private EnvelopeDocument(Builder builder) {
    this.sourceDocumentId = builder.sourceDocumentId;
    this.name = builder.name;
    this.recipients = builder.recipients;
  }

  /**
   * Creates a new builder for envelope document parameters.
   *
   * @return the builder for envelope document parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the source document ID.
   *
   * @return the source document ID.
   */
  public String getSourceDocumentId() {
    return sourceDocumentId;
  }

  /**
   * Returns the document name inside the envelope.
   *
   * @return the document name inside the envelope.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the recipients for the document.
   *
   * @return the recipients for the document.
   */
  public List<EnvelopeRecipient> getRecipients() {
    return recipients;
  }

  /**
   * Builder for {@link EnvelopeDocument}.
   */
  public static final class Builder {
    private String sourceDocumentId;
    private String name;
    private List<EnvelopeRecipient> recipients;

    private Builder() {
    }

    /**
     * Sets the source document ID.
     *
     * @param sourceDocumentId source document ID.
     * @return this builder.
     */
    public Builder sourceDocumentId(String sourceDocumentId) {
      this.sourceDocumentId = sourceDocumentId;
      return this;
    }

    /**
     * Sets the display name inside the envelope.
     *
     * @param name display name inside the envelope.
     * @return this builder.
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the recipients for the document.
     *
     * @param recipients recipients for the document.
     * @return this builder.
     */
    public Builder recipients(List<EnvelopeRecipient> recipients) {
      this.recipients = recipients;
      return this;
    }

    /**
     * Builds the envelope document parameters.
     *
     * @return the envelope document parameters.
     */
    public EnvelopeDocument build() {
      return new EnvelopeDocument(this);
    }
  }
}
