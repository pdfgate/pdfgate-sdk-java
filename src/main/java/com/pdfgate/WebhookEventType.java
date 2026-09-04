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
   * An envelope was voided (cancelled) by the sender.
   */
  @SerializedName("envelope.voided")
  ENVELOPE_VOIDED,
  /**
   * An envelope was permanently deleted by the sender.
   */
  @SerializedName("envelope.deleted")
  ENVELOPE_DELETED,
  /**
   * A recipient signed a document within an envelope.
   */
  @SerializedName("envelope.recipient.signed")
  ENVELOPE_RECIPIENT_SIGNED,
  /**
   * A document within an envelope was completed.
   */
  @SerializedName("envelope.document.completed")
  ENVELOPE_DOCUMENT_COMPLETED
}
