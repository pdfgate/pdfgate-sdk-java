package com.pdfgate;

import com.google.gson.annotations.SerializedName;

/**
 * Envelope document status values returned by the API.
 */
public enum EnvelopeDocumentStatus {
  /**
   * The document is pending.
   */
  @SerializedName("pending")
  PENDING,
  /**
   * The document has expired.
   */
  @SerializedName("expired")
  EXPIRED,
  /**
   * The document was voided before all recipients signed.
   */
  @SerializedName("voided")
  VOIDED,
  /**
   * The document has been sent for signing.
   */
  @SerializedName("sent_for_signing")
  SENT_FOR_SIGNING,
  /**
   * The document is currently being signed.
   */
  @SerializedName("signing_in_progress")
  SIGNING_IN_PROGRESS,
  /**
   * Signing failed for the document.
   */
  @SerializedName("signing_failed")
  SIGNING_FAILED,
  /**
   * The document signing flow is complete.
   */
  @SerializedName("completed")
  COMPLETED
}
