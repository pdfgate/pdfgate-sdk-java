package com.pdfgate;

/**
 * Exception thrown when a webhook signature cannot be verified.
 */
public final class PdfGateWebhookVerificationException extends Exception {
  /**
   * Creates a verification exception with the provided message.
   *
   * @param message verification failure message.
   */
  public PdfGateWebhookVerificationException(String message) {
    super(message);
  }

  /**
   * Creates a verification exception with the provided message and cause.
   *
   * @param message verification failure message.
   * @param cause   underlying cause.
   */
  public PdfGateWebhookVerificationException(String message, Throwable cause) {
    super(message, cause);
  }
}
