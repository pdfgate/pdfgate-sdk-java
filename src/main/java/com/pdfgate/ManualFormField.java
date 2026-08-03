package com.pdfgate;

import java.util.List;

/**
 * A form field placed at an explicit position on a given page.
 *
 * <p>Used with {@link AddFormFieldsParams} to add fields at explicit coordinates.
 */
public final class ManualFormField {
  private final String name;
  private final DocumentFieldType type;
  private final Integer page;
  private final Integer height;
  private final Integer width;
  private final Double x;
  private final Double y;
  private final String value;
  private final List<String> options;
  private final String role;
  private final Integer fontSize;
  private final Boolean autoFill;
  private final Boolean optional;
  private final String description;

  private ManualFormField(Builder builder) {
    this.name = builder.name;
    this.type = builder.type;
    this.page = builder.page;
    this.height = builder.height;
    this.width = builder.width;
    this.x = builder.x;
    this.y = builder.y;
    this.value = builder.value;
    this.options = builder.options;
    this.role = builder.role;
    this.fontSize = builder.fontSize;
    this.autoFill = builder.autoFill;
    this.optional = builder.optional;
    this.description = builder.description;
  }

  /**
   * Creates a new builder for a manual form field.
   *
   * @return the builder for a manual form field.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the field name.
   *
   * @return the field name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the field type.
   *
   * @return the field type.
   */
  public DocumentFieldType getType() {
    return type;
  }

  /**
   * Returns the 1-based page number.
   *
   * @return the page number.
   */
  public Integer getPage() {
    return page;
  }

  /**
   * Returns the field height.
   *
   * @return the field height.
   */
  public Integer getHeight() {
    return height;
  }

  /**
   * Returns the field width.
   *
   * @return the field width.
   */
  public Integer getWidth() {
    return width;
  }

  /**
   * Returns the x coordinate.
   *
   * @return the x coordinate.
   */
  public Double getX() {
    return x;
  }

  /**
   * Returns the y coordinate.
   *
   * @return the y coordinate.
   */
  public Double getY() {
    return y;
  }

  /**
   * Returns the field value, if present.
   *
   * @return the field value, if present.
   */
  public String getValue() {
    return value;
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
   * Builder for {@link ManualFormField}.
   */
  public static final class Builder {
    private String name;
    private DocumentFieldType type;
    private Integer page;
    private Integer height;
    private Integer width;
    private Double x;
    private Double y;
    private String value;
    private List<String> options;
    private String role;
    private Integer fontSize;
    private Boolean autoFill;
    private Boolean optional;
    private String description;

    private Builder() {
    }

    /**
     * Sets the field name.
     *
     * @param name the field name.
     * @return this builder.
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the field type.
     *
     * @param type the field type.
     * @return this builder.
     */
    public Builder type(DocumentFieldType type) {
      this.type = type;
      return this;
    }

    /**
     * Sets the 1-based page number.
     *
     * @param page the page number.
     * @return this builder.
     */
    public Builder page(Integer page) {
      this.page = page;
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
     * Sets the x coordinate.
     *
     * @param x the x coordinate.
     * @return this builder.
     */
    public Builder x(Double x) {
      this.x = x;
      return this;
    }

    /**
     * Sets the y coordinate.
     *
     * @param y the y coordinate.
     * @return this builder.
     */
    public Builder y(Double y) {
      this.y = y;
      return this;
    }

    /**
     * Sets the field value.
     *
     * @param value the field value.
     * @return this builder.
     */
    public Builder value(String value) {
      this.value = value;
      return this;
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
     * Builds the manual form field.
     *
     * @return the manual form field.
     */
    public ManualFormField build() {
      return new ManualFormField(this);
    }
  }
}
