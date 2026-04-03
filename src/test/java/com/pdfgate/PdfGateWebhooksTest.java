package com.pdfgate;

import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PdfGateWebhooksTest {
  private static final String SECRET = "whsecret_test_123";
  private static final byte[] PAYLOAD = "{\"id\":\"evt_123\"}".getBytes(StandardCharsets.UTF_8);
  private static final long NOW = 1712345678L;

  @Test
  public void verifySignatureSucceedsWhenSignatureIsValid() throws Exception {
    String header = buildHeader(NOW, sign(SECRET, NOW, PAYLOAD));

    Assertions.assertDoesNotThrow(
        () -> PdfGateWebhooks.verifySignature(SECRET, header, PAYLOAD, NOW)
    );
  }

  @Test
  public void verifySignatureSucceedsWhenAnyV1SignatureMatches() throws Exception {
    String validSignature = sign(SECRET, NOW, PAYLOAD);
    String header = "t=" + NOW + ",v1=deadbeef,v1=" + validSignature + ",v1=00ff";

    Assertions.assertDoesNotThrow(
        () -> PdfGateWebhooks.verifySignature(SECRET, header, PAYLOAD, NOW)
    );
  }

  @Test
  public void verifySignatureFailsWhenHeaderIsMissingValidSignature() {
    String header = "t=" + NOW;

    PdfGateWebhookVerificationException exception = Assertions.assertThrows(
        PdfGateWebhookVerificationException.class,
        () -> PdfGateWebhooks.verifySignature(SECRET, header, PAYLOAD, NOW)
    );
    Assertions.assertEquals("Missing signature.", exception.getMessage());
  }

  @Test
  public void verifySignatureFailsWhenHeaderIsMissingTimestamp() throws Exception {
    String header = "v1=" + sign(SECRET, NOW, PAYLOAD);

    PdfGateWebhookVerificationException exception = Assertions.assertThrows(
        PdfGateWebhookVerificationException.class,
        () -> PdfGateWebhooks.verifySignature(SECRET, header, PAYLOAD, NOW)
    );
    Assertions.assertEquals("Missing signature timestamp.", exception.getMessage());
  }

  @Test
  public void verifySignatureFailsWhenSignatureIsExpired() throws Exception {
    String header = buildHeader(NOW - 301L, sign(SECRET, NOW - 301L, PAYLOAD));

    PdfGateWebhookVerificationException exception = Assertions.assertThrows(
        PdfGateWebhookVerificationException.class,
        () -> PdfGateWebhooks.verifySignature(SECRET, header, PAYLOAD, NOW)
    );
    Assertions.assertEquals("Signature expired.", exception.getMessage());
  }

  @Test
  public void verifySignatureFailsWhenSignatureIsInvalid() throws Exception {
    String header = buildHeader(NOW, sign("wrong_secret", NOW, PAYLOAD));

    PdfGateWebhookVerificationException exception = Assertions.assertThrows(
        PdfGateWebhookVerificationException.class,
        () -> PdfGateWebhooks.verifySignature(SECRET, header, PAYLOAD, NOW)
    );
    Assertions.assertEquals("Invalid signature.", exception.getMessage());
  }

  private static String buildHeader(long timestamp, String signature) {
    return "t=" + timestamp + ",v1=" + signature;
  }

  private static String sign(String secret, long timestamp, byte[] payload) throws Exception {
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    mac.update(Long.toString(timestamp).getBytes(StandardCharsets.UTF_8));
    mac.update((byte) '.');
    mac.update(payload);
    return PdfGateWebhooks.toHex(mac.doFinal());
  }
}
