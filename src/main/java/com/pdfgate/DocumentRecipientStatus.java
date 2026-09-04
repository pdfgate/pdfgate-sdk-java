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
   * The recipient has expired.
   */
  @SerializedName("expired")
  EXPIRED,
  /**
   * The recipient was voided before signing.
   */
  @SerializedName("voided")
  VOIDED,
  /**
   * The recipient has signed.
   */
  @SerializedName("signed")
  SIGNED
}
