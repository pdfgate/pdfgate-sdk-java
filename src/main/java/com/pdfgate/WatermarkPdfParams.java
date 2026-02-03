package com.pdfgate;

import com.google.gson.annotations.SerializedName;

/**
 * Parameters for watermarking a PDF by file or document ID.
 *
 * <p>{@code type} is required. For text watermarks, set {@code text}. For image
 * watermarks, provide a {@code watermark} image file. Optional settings include
 * font, size, color, opacity, position, and rotation.
 */
public abstract class WatermarkPdfParams {
  private final FileParam file;
  private final String documentId;
  private final FileParam watermark;
  private final WatermarkType type;
  private final String text;
  private final String font;
  private final Integer fontSize;
  private final String fontColor;
  private final Double opacity;
  private final Integer xPosition;
  private final Integer yPosition;
  private final Integer imageWidth;
  private final Integer imageHeight;
  private final Double rotate;
  private final Boolean jsonResponse;
  private final Long preSignedUrlExpiresIn;
  private final Object metadata;

  /**
   * Initializes watermark PDF parameters from the builder.
   *
   * @param builder builder with configured values.
   */
  protected WatermarkPdfParams(Builder builder) {
    this.file = builder.file;
    this.documentId = builder.documentId;
    this.watermark = builder.watermark;
    this.type = builder.type;
    this.text = builder.text;
    this.font = builder.font;
    this.fontSize = builder.fontSize;
    this.fontColor = builder.fontColor;
    this.opacity = builder.opacity;
    this.xPosition = builder.xPosition;
    this.yPosition = builder.yPosition;
    this.imageWidth = builder.imageWidth;
    this.imageHeight = builder.imageHeight;
    this.rotate = builder.rotate;
    this.jsonResponse = builder.jsonResponse;
    this.preSignedUrlExpiresIn = builder.preSignedUrlExpiresIn;
    this.metadata = builder.metadata;
  }

  /**
   * Creates a new builder for watermark PDF parameters.
   *
   * @return the builder for watermark PDF parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the PDF file payload when watermarking by file.
   *
   * @return the PDF file payload when watermarking by file.
   */
  public FileParam getFile() {
    return file;
  }

  /**
   * Returns the document id when watermarking by document id.
   *
   * @return the document id when watermarking by document id.
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Returns the watermark image payload when watermarking with an image.
   *
   * @return the watermark image payload when watermarking with an image.
   */
  public FileParam getWatermark() {
    return watermark;
  }

  /**
   * Returns the watermark type.
   *
   * @return the watermark type.
   */
  public WatermarkType getType() {
    return type;
  }

  /**
   * Returns the watermark text.
   *
   * @return the watermark text.
   */
  public String getText() {
    return text;
  }

  /**
   * Returns the text font name.
   *
   * @return the text font name.
   */
  public String getFont() {
    return font;
  }

  /**
   * Returns the text font size.
   *
   * @return the text font size.
   */
  public Integer getFontSize() {
    return fontSize;
  }

  /**
   * Returns the text font color.
   *
   * @return the text font color.
   */
  public String getFontColor() {
    return fontColor;
  }

  /**
   * Returns the watermark opacity.
   *
   * @return the watermark opacity.
   */
  public Double getOpacity() {
    return opacity;
  }

  /**
   * Returns the watermark horizontal position.
   *
   * @return the watermark horizontal position.
   */
  public Integer getXPosition() {
    return xPosition;
  }

  /**
   * Returns the watermark vertical position.
   *
   * @return the watermark vertical position.
   */
  public Integer getYPosition() {
    return yPosition;
  }

  /**
   * Returns the watermark image width.
   *
   * @return the watermark image width.
   */
  public Integer getImageWidth() {
    return imageWidth;
  }

  /**
   * Returns the watermark image height.
   *
   * @return the watermark image height.
   */
  public Integer getImageHeight() {
    return imageHeight;
  }

  /**
   * Returns the watermark rotation in degrees.
   *
   * @return the watermark rotation in degrees.
   */
  public Double getRotate() {
    return rotate;
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
   * Watermark type supported by the API.
   */
  public enum WatermarkType {
    /** Text watermark. */
    @SerializedName("text")
    TEXT("text"),
    /** Image watermark. */
    @SerializedName("image")
    IMAGE("image");

    private final String value;

    WatermarkType(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  /**
   * Builder for {@link WatermarkPdfParams}.
   */
  public static final class Builder {
    private FileParam file;
    private String documentId;
    private FileParam watermark;
    private WatermarkType type;
    private String text;
    private String font;
    private Integer fontSize;
    private String fontColor;
    private Double opacity;
    private Integer xPosition;
    private Integer yPosition;
    private Integer imageWidth;
    private Integer imageHeight;
    private Double rotate;
    private Boolean jsonResponse;
    private Long preSignedUrlExpiresIn;
    private Object metadata;

    private Builder() {
    }

    /**
     * Sets the PDF file payload.
     *
     * @param file the PDF file payload.
     * @return this builder.
     */
    public Builder file(FileParam file) {
      this.file = file;
      return this;
    }

    /**
     * Sets the document id for the source PDF.
     *
     * @param documentId the document id for the source PDF.
     * @return this builder.
     */
    public Builder documentId(String documentId) {
      this.documentId = documentId;
      return this;
    }

    /**
     * Sets the watermark image file.
     *
     * @param watermark watermark image file.
     * @return this builder.
     */
    public Builder watermark(FileParam watermark) {
      this.watermark = watermark;
      return this;
    }

    /**
     * Sets the watermark type.
     *
     * @param type watermark type.
     * @return this builder.
     */
    public Builder type(WatermarkType type) {
      this.type = type;
      return this;
    }

    /**
     * Sets the watermark text.
     *
     * @param text watermark text.
     * @return this builder.
     */
    public Builder text(String text) {
      this.text = text;
      return this;
    }

    /**
     * Sets the text watermark font.
     *
     * @param font text watermark font.
     * @return this builder.
     */
    public Builder font(String font) {
      this.font = font;
      return this;
    }

    /**
     * Sets the text watermark font size.
     *
     * @param fontSize text watermark font size.
     * @return this builder.
     */
    public Builder fontSize(Integer fontSize) {
      this.fontSize = fontSize;
      return this;
    }

    /**
     * Sets the text watermark font color.
     *
     * @param fontColor text watermark font color.
     * @return this builder.
     */
    public Builder fontColor(String fontColor) {
      this.fontColor = fontColor;
      return this;
    }

    /**
     * Sets the watermark opacity.
     *
     * @param opacity watermark opacity.
     * @return this builder.
     */
    public Builder opacity(Double opacity) {
      this.opacity = opacity;
      return this;
    }

    /**
     * Sets the watermark horizontal position.
     *
     * @param xPosition watermark horizontal position.
     * @return this builder.
     */
    public Builder xPosition(Integer xPosition) {
      this.xPosition = xPosition;
      return this;
    }

    /**
     * Sets the watermark vertical position.
     *
     * @param yPosition watermark vertical position.
     * @return this builder.
     */
    public Builder yPosition(Integer yPosition) {
      this.yPosition = yPosition;
      return this;
    }

    /**
     * Sets the watermark image width.
     *
     * @param imageWidth watermark image width.
     * @return this builder.
     */
    public Builder imageWidth(Integer imageWidth) {
      this.imageWidth = imageWidth;
      return this;
    }

    /**
     * Sets the watermark image height.
     *
     * @param imageHeight watermark image height.
     * @return this builder.
     */
    public Builder imageHeight(Integer imageHeight) {
      this.imageHeight = imageHeight;
      return this;
    }

    /**
     * Sets the watermark rotation in degrees.
     *
     * @param rotate watermark rotation in degrees.
     * @return this builder.
     */
    public Builder rotate(Double rotate) {
      this.rotate = rotate;
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
     * Builds watermark PDF parameters for bytes responses.
     *
     * @return parameters configured for file responses.
     */
    public WatermarkPdfFileParams buildWithFileResponse() {
      this.jsonResponse = false;
      return new WatermarkPdfFileParams(this);
    }

    /**
     * Builds watermark PDF parameters for JSON responses.
     *
     * @return parameters configured for JSON responses.
     */
    public WatermarkPdfJsonParams buildWithJsonResponse() {
      this.jsonResponse = true;
      return new WatermarkPdfJsonParams(this);
    }
  }
}
