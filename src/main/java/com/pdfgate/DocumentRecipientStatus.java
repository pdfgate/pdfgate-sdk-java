package com.pdfgate;

import com.google.gson.annotations.SerializedName;

/**
 * Recipient status values returned by the API.
 */
public enum DocumentRecipientStatus {
  /**
   * The recipient has not yet signed.
   */
  @SerializedName("pending")
  PENDING,
  /**
   * The recipient has signed.
   */
  @SerializedName("signed")
  SIGNED
}
