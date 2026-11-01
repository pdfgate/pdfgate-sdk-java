package com.pdfgate;

import com.google.gson.annotations.SerializedName;

/**
 * How a recipient created their stored signature (automatic signing).
 */
public enum SignatureAdoptionMethod {
  /**
   * The recipient typed their name and picked a font.
   */
  @SerializedName("type_signature")
  TYPE_SIGNATURE,
  /**
   * The recipient drew the signature.
   */
  @SerializedName("draw_signature")
  DRAW_SIGNATURE,
  /**
   * The recipient uploaded an image of the signature.
   */
  @SerializedName("upload_signature")
  UPLOAD_SIGNATURE
}
