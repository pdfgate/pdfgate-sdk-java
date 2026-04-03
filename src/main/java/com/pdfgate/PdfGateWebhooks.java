package com.pdfgate;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Helpers for verifying PDFGate webhook requests.
 */
public final class PdfGateWebhooks {
  /**
   * Maximum accepted timestamp drift in seconds.
   */
  private static final long DEFAULT_TOLERANCE_SECONDS = 300L;

  private PdfGateWebhooks() {
  }

  /**
   * Verifies a webhook signature header against the raw request body.
   *
   * @param secret webhook secret used to sign requests.
   * @param signatureHeader {@code x-pdfgate-signature} header value.
   * @param payload raw request body as received.
   * @throws PdfGateWebhookVerificationException when the signature is missing, expired, or invalid.
   */
  public static void verifySignature(String secret, String signatureHeader, byte[] payload)
      throws PdfGateWebhookVerificationException {
    verifySignature(secret, signatureHeader, payload, currentTimestampSeconds());
  }

  /**
   * Verifies a webhook signature header against the raw request body.
   *
   * @param secret webhook secret used to sign requests.
   * @param signatureHeader {@code x-pdfgate-signature} header value.
   * @param payload raw request body as received.
   * @throws PdfGateWebhookVerificationException when the signature is missing, expired, or invalid.
   */
  public static void verifySignature(String secret, String signatureHeader, String payload)
      throws PdfGateWebhookVerificationException {
    verifySignature(secret, signatureHeader, payload == null
        ? null
        : payload.getBytes(StandardCharsets.UTF_8));
  }

  static void verifySignature(
      String secret,
      String signatureHeader,
      byte[] payload,
      long currentTimestampSeconds
  ) throws PdfGateWebhookVerificationException {
    if (Strings.isBlank(secret)) {
      throw new IllegalArgumentException("secret must be provided.");
    }
    if (payload == null) {
      throw new IllegalArgumentException("payload must be provided.");
    }

    ParsedSignature parsedSignature = parseSignatureHeader(signatureHeader);
    if (parsedSignature.timestamp == null) {
      throw new PdfGateWebhookVerificationException("Missing signature timestamp.");
    }
    if (parsedSignature.signatures.isEmpty()) {
      throw new PdfGateWebhookVerificationException("Missing signature.");
    }
    if (Math.abs(currentTimestampSeconds - parsedSignature.timestamp.longValue())
        > DEFAULT_TOLERANCE_SECONDS) {
      throw new PdfGateWebhookVerificationException("Signature expired.");
    }

    String expectedSignature = computeSignature(secret, parsedSignature.timestamp.longValue(), payload);
    if (!matchesAnySignature(parsedSignature.signatures, expectedSignature)) {
      throw new PdfGateWebhookVerificationException("Invalid signature.");
    }
  }

  private static ParsedSignature parseSignatureHeader(String signatureHeader) {
    Long timestamp = null;
    List<String> signatures = new ArrayList<String>();
    if (Strings.isBlank(signatureHeader)) {
      return new ParsedSignature(timestamp, signatures);
    }

    String[] parts = signatureHeader.trim().split(",");
    for (String rawPart : parts) {
      String part = rawPart.trim();
      int separatorIndex = part.indexOf('=');
      if (separatorIndex <= 0 || separatorIndex >= part.length() - 1) {
        continue;
      }

      String key = part.substring(0, separatorIndex);
      String value = part.substring(separatorIndex + 1);
      if ("t".equals(key)) {
        try {
          timestamp = Long.parseLong(value);
        } catch (NumberFormatException ignored) {
          timestamp = null;
        }
      } else if ("v1".equals(key)) {
        signatures.add(value);
      }
    }

    return new ParsedSignature(timestamp, signatures);
  }

  private static String computeSignature(String secret, long timestamp, byte[] payload)
      throws PdfGateWebhookVerificationException {
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
      mac.update(Long.toString(timestamp).getBytes(StandardCharsets.UTF_8));
      mac.update((byte) '.');
      mac.update(payload);
      return toHex(mac.doFinal());
    } catch (NoSuchAlgorithmException e) {
      throw new PdfGateWebhookVerificationException(
          "Failed to verify signature due to missing HmacSHA256 support.",
          e
      );
    } catch (InvalidKeyException e) {
      throw new PdfGateWebhookVerificationException("Failed to verify signature.", e);
    }
  }

  private static boolean matchesAnySignature(List<String> signatures, String expectedSignature) {
    byte[] expectedBytes = expectedSignature.getBytes(StandardCharsets.US_ASCII);
    for (String candidate : signatures) {
      if (candidate != null
          && MessageDigest.isEqual(candidate.getBytes(StandardCharsets.US_ASCII), expectedBytes)) {
        return true;
      }
    }
    return false;
  }

  static String toHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    char[] digits = "0123456789abcdef".toCharArray();
    for (int i = 0; i < bytes.length; i++) {
      int value = bytes[i] & 0xFF;
      hexChars[i * 2] = digits[value >>> 4];
      hexChars[i * 2 + 1] = digits[value & 0x0F];
    }
    return new String(hexChars);
  }

  private static long currentTimestampSeconds() {
    return System.currentTimeMillis() / 1000L;
  }

  private static final class ParsedSignature {
    private final Long timestamp;
    private final List<String> signatures;

    private ParsedSignature(Long timestamp, List<String> signatures) {
      this.timestamp = timestamp;
      this.signatures = signatures;
    }
  }
}
