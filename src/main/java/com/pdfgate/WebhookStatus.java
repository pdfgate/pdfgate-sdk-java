package com.pdfgate;

import com.google.gson.annotations.SerializedName;

/**
 * Webhook status values returned by the API.
 */
public enum WebhookStatus {
  /**
   * The webhook is active and receiving events.
   */
  @SerializedName("active")
  ACTIVE,
  /**
   * The webhook is disabled and not receiving events.
   */
  @SerializedName("disabled")
  DISABLED
}
