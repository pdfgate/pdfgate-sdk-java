package com.pdfgate;

import com.google.gson.annotations.SerializedName;

/**
 * Envelope field type values returned by the API.
 */
public enum DocumentFieldType {
  /**
   * Signature field.
   */
  @SerializedName("signature")
  SIGNATURE,
  /**
   * Single-line text field.
   */
  @SerializedName("text")
  TEXT,
  /**
   * Numeric field.
   */
  @SerializedName("number")
  NUMBER,
  /**
   * Multi-line text field.
   */
  @SerializedName("textarea")
  TEXT_AREA,
  /**
   * Date field.
   */
  @SerializedName("date")
  DATE,
  /**
   * Time field.
   */
  @SerializedName("time")
  TIME,
  /**
   * Date-time field.
   */
  @SerializedName("datetime")
  DATETIME,
  /**
   * Checkbox field.
   */
  @SerializedName("checkbox")
  CHECKBOX,
  /**
   * Radio button field.
   */
  @SerializedName("radio")
  RADIO_BUTTON,
  /**
   * Select field.
   */
  @SerializedName("select")
  SELECT
}
