package com.pdfgate;

/**
 * Parameters for creating an embedded signing link.
 */
public final class CreateEmbedLinkParams {
  /**
   * Envelope id to create the embed link for.
   */
  private final String id;

  /**
   * Document id inside the envelope.
   */
  private final String documentId;

  /**
   * Id of the embedded recipient who will sign.
   */
  private final String recipientId;

  /**
   * URL the signing iframe redirects to when the session ends.
   */
  private final String returnUrl;

  private CreateEmbedLinkParams(Builder builder) {
    this.id = builder.id;
    this.documentId = builder.documentId;
    this.recipientId = builder.recipientId;
    this.returnUrl = builder.returnUrl;
  }

  /**
   * Creates a new builder for create embed link parameters.
   *
   * @return the builder for create embed link parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the envelope id to create the embed link for.
   *
   * @return the envelope id to create the embed link for.
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the document id inside the envelope.
   *
   * @return the document id inside the envelope.
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Returns the id of the embedded recipient who will sign.
   *
   * @return the id of the embedded recipient who will sign.
   */
  public String getRecipientId() {
    return recipientId;
  }

  /**
   * Returns the URL the signing iframe redirects to when the session ends.
   *
   * @return the URL the signing iframe redirects to when the session ends.
   */
  public String getReturnUrl() {
    return returnUrl;
  }

  /**
   * Builder for {@link CreateEmbedLinkParams}.
   */
  public static final class Builder {
    /**
     * Envelope id to create the embed link for.
     */
    private String id;

    /**
     * Document id inside the envelope.
     */
    private String documentId;

    /**
     * Id of the embedded recipient who will sign.
     */
    private String recipientId;

    /**
     * URL the signing iframe redirects to when the session ends.
     */
    private String returnUrl;

    /**
     * Creates a builder for create embed link parameters.
     */
    public Builder() {
    }

    /**
     * Sets the envelope id to create the embed link for.
     *
     * @param id the envelope id to create the embed link for.
     * @return this builder.
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Sets the document id inside the envelope.
     *
     * @param documentId the document id inside the envelope.
     * @return this builder.
     */
    public Builder documentId(String documentId) {
      this.documentId = documentId;
      return this;
    }

    /**
     * Sets the id of the embedded recipient who will sign.
     *
     * @param recipientId the id of the embedded recipient who will sign.
     * @return this builder.
     */
    public Builder recipientId(String recipientId) {
      this.recipientId = recipientId;
      return this;
    }

    /**
     * Sets the URL the signing iframe redirects to when the session ends.
     * Existing query parameters on the URL are preserved.
     *
     * @param returnUrl the URL the signing iframe redirects to.
     * @return this builder.
     */
    public Builder returnUrl(String returnUrl) {
      this.returnUrl = returnUrl;
      return this;
    }

    /**
     * Builds the create embed link parameters.
     *
     * @return the create embed link parameters.
     */
    public CreateEmbedLinkParams build() {
      return new CreateEmbedLinkParams(this);
    }
  }
}
