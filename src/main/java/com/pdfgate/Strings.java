package com.pdfgate;

/**
 * Internal string helpers that remain compatible with Java 8.
 */
final class Strings {
  private Strings() {
  }

  /**
   * Returns whether the value is {@code null}, empty, or contains only whitespace.
   */
  static boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }
}
