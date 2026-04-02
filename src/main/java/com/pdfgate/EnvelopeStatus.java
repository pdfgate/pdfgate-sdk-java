package com.pdfgate;

import com.google.gson.annotations.SerializedName;

/**
 * Envelope status values returned by the API.
 */
public enum EnvelopeStatus {
  /**
   * The envelope has been created.
   */
  @SerializedName("created")
  CREATED,
  /**
   * The envelope is currently being processed.
   */
  @SerializedName("in_progress")
  IN_PROGRESS,
  /**
   * The envelope has been completed.
   */
  @SerializedName("completed")
  COMPLETED,
  /**
   * The envelope has expired.
   */
  @SerializedName("expired")
  EXPIRED
}
