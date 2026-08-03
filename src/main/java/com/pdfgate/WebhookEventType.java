package com.pdfgate;

import com.google.gson.annotations.SerializedName;

/**
 * Events that a webhook can subscribe to.
 */
public enum WebhookEventType {
  /**
   * An envelope was sent to its recipients.
   */
  @SerializedName("envelope.sent")
  ENVELOPE_SENT,
  /**
   * An envelope was completed by all recipients.
   */
  @SerializedName("envelope.completed")
  ENVELOPE_COMPLETED,
  /**
   * An envelope expired before completion.
   */
  @SerializedName("envelope.expired")
  ENVELOPE_EXPIRED,
  /**
   * A document within an envelope was completed.
   */
  @SerializedName("envelope.document.completed")
  ENVELOPE_DOCUMENT_COMPLETED
}
