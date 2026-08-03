package com.pdfgate;

import java.util.List;
import java.util.Map;

/**
 * Parameters for adding interactive form fields to a PDF by document ID.
 *
 * <p>Two complementary ways to add fields:
 *
 * <ul>
 *   <li>{@code fieldOverrides}: customize placeholder fields detected in the PDF, keyed by
 *       field name.
 *   <li>{@code fields}: place fields at explicit {@code x}/{@code y} positions on a page.
 * </ul>
 *
 * <p>PDFGate creates a new document with the added fields; the original is left untouched.
 * Responses are JSON-only; use {@link Builder#build()} for {@link PdfGateDocument} metadata.
 */
public final class AddFormFieldsParams {
  private final String documentId;
  private final Map<String, FieldOverride> fieldOverrides;
  private final List<ManualFormField> fields;
  private final Boolean jsonResponse;
  private final Long preSignedUrlExpiresIn;
  private final Object metadata;

  private AddFormFieldsParams(Builder builder) {
    this.documentId = builder.documentId;
    this.fieldOverrides = builder.fieldOverrides;
    this.fields = builder.fields;
    this.jsonResponse = builder.jsonResponse;
    this.preSignedUrlExpiresIn = builder.preSignedUrlExpiresIn;
    this.metadata = builder.metadata;
  }

  /**
   * Creates a new builder for add form fields parameters.
   *
   * @return the builder for add form fields parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the document ID of the source PDF.
   *
   * @return the document ID.
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Returns the placeholder field overrides, keyed by field name, if present.
   *
   * @return the field overrides, if present.
   */
  public Map<String, FieldOverride> getFieldOverrides() {
    return fieldOverrides;
  }

  /**
   * Returns the manually positioned fields, if present.
   *
   * @return the manually positioned fields, if present.
   */
  public List<ManualFormField> getFields() {
    return fields;
  }

  /**
   * Returns whether the response is JSON.
   *
   * @return whether the response is JSON.
   */
  public Boolean getJsonResponse() {
    return jsonResponse;
  }

  /**
   * Returns whether the response is JSON.
   *
   * @return whether the response is JSON.
   */
  public boolean isJsonResponse() {
    return Boolean.TRUE.equals(jsonResponse);
  }

  /**
   * Returns the pre-signed URL expiration time in seconds.
   *
   * @return the pre-signed URL expiration time in seconds.
   */
  public Long getPreSignedUrlExpiresIn() {
    return preSignedUrlExpiresIn;
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
   * Builder for {@link AddFormFieldsParams}.
   */
  public static final class Builder {
    private String documentId;
    private Map<String, FieldOverride> fieldOverrides;
    private List<ManualFormField> fields;
    private Boolean jsonResponse = true;
    private Long preSignedUrlExpiresIn;
    private Object metadata;

    private Builder() {
    }

    /**
     * Sets the document ID for the source PDF.
     *
     * @param documentId the document ID.
     * @return this builder.
     */
    public Builder documentId(String documentId) {
      this.documentId = documentId;
      return this;
    }

    /**
     * Sets the placeholder field overrides, keyed by field name.
     *
     * @param fieldOverrides the field overrides.
     * @return this builder.
     */
    public Builder fieldOverrides(Map<String, FieldOverride> fieldOverrides) {
      this.fieldOverrides = fieldOverrides;
      return this;
    }

    /**
     * Sets the manually positioned fields.
     *
     * @param fields the manually positioned fields.
     * @return this builder.
     */
    public Builder fields(List<ManualFormField> fields) {
      this.fields = fields;
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
     * Builds add form fields parameters for JSON responses.
     *
     * @return parameters configured for JSON responses.
     */
    public AddFormFieldsParams build() {
      this.jsonResponse = true;
      return new AddFormFieldsParams(this);
    }
  }
}
