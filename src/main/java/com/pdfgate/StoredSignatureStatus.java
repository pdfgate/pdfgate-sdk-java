package com.pdfgate;

import com.google.gson.annotations.SerializedName;

/**
 * State of a recipient's stored signature (automatic signing).
 */
public enum StoredSignatureStatus {
  /**
   * The enrolment link was sent but the recipient has not completed it.
   */
  @SerializedName("pending")
  PENDING,
  /**
   * The signature can be applied to documents.
   */
  @SerializedName("active")
  ACTIVE,
  /**
   * The signature was removed.
   */
  @SerializedName("revoked")
  REVOKED
}
