package com.pdfgate;

import java.util.List;

/**
 * Overrides applied to a placeholder field detected in a PDF.
 *
 * <p>Used with {@link AddFormFieldsParams}, keyed by the placeholder field name.
 */
public final class FieldOverride {
  private final List<String> options;
  private final Integer height;
  private final Integer width;
  private final String role;
  private final Integer fontSize;
  private final Boolean autoFill;
  private final Boolean optional;
  private final String description;

  private FieldOverride(Builder builder) {
    this.options = builder.options;
    this.height = builder.height;
    this.width = builder.width;
    this.role = builder.role;
    this.fontSize = builder.fontSize;
    this.autoFill = builder.autoFill;
    this.optional = builder.optional;
    this.description = builder.description;
  }

  /**
   * Creates a new builder for a field override.
   *
   * @return the builder for a field override.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the select options, if present.
   *
   * @return the select options, if present.
   */
  public List<String> getOptions() {
    return options;
  }

  /**
   * Returns the field height, if present.
   *
   * @return the field height, if present.
   */
  public Integer getHeight() {
    return height;
  }

  /**
   * Returns the field width, if present.
   *
   * @return the field width, if present.
   */
  public Integer getWidth() {
    return width;
  }

  /**
   * Returns the recipient role, if present.
   *
   * @return the recipient role, if present.
   */
  public String getRole() {
    return role;
  }

  /**
   * Returns the font size, if present.
   *
   * @return the font size, if present.
   */
  public Integer getFontSize() {
    return fontSize;
  }

  /**
   * Returns whether the field is auto-filled, if present.
   *
   * @return whether the field is auto-filled, if present.
   */
  public Boolean getAutoFill() {
    return autoFill;
  }

  /**
   * Returns whether the field is optional, if present.
   *
   * @return whether the field is optional, if present.
   */
  public Boolean getOptional() {
    return optional;
  }

  /**
   * Returns the field description, if present.
   *
   * @return the field description, if present.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Builder for {@link FieldOverride}.
   */
  public static final class Builder {
    private List<String> options;
    private Integer height;
    private Integer width;
    private String role;
    private Integer fontSize;
    private Boolean autoFill;
    private Boolean optional;
    private String description;

    private Builder() {
    }

    /**
     * Sets the select options.
     *
     * @param options the select options.
     * @return this builder.
     */
    public Builder options(List<String> options) {
      this.options = options;
      return this;
    }

    /**
     * Sets the field height.
     *
     * @param height the field height.
     * @return this builder.
     */
    public Builder height(Integer height) {
      this.height = height;
      return this;
    }

    /**
     * Sets the field width.
     *
     * @param width the field width.
     * @return this builder.
     */
    public Builder width(Integer width) {
      this.width = width;
      return this;
    }

    /**
     * Sets the recipient role.
     *
     * @param role the recipient role.
     * @return this builder.
     */
    public Builder role(String role) {
      this.role = role;
      return this;
    }

    /**
     * Sets the font size.
     *
     * @param fontSize the font size.
     * @return this builder.
     */
    public Builder fontSize(Integer fontSize) {
      this.fontSize = fontSize;
      return this;
    }

    /**
     * Sets whether the field is auto-filled.
     *
     * @param autoFill whether the field is auto-filled.
     * @return this builder.
     */
    public Builder autoFill(Boolean autoFill) {
      this.autoFill = autoFill;
      return this;
    }

    /**
     * Sets whether the field is optional.
     *
     * @param optional whether the field is optional.
     * @return this builder.
     */
    public Builder optional(Boolean optional) {
      this.optional = optional;
      return this;
    }

    /**
     * Sets the field description.
     *
     * @param description the field description.
     * @return this builder.
     */
    public Builder description(String description) {
      this.description = description;
      return this;
    }

    /**
     * Builds the field override.
     *
     * @return the field override.
     */
    public FieldOverride build() {
      return new FieldOverride(this);
    }
  }
}
