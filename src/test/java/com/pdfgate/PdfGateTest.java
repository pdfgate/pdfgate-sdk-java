package com.pdfgate;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PdfGateTest {

  private PdfGate buildClient(String url) {
    PdfGateConfig config = PdfGateConfig.of(
        "https://invalid-production-host",
        url,
        Duration.ofSeconds(2),
        Duration.ofSeconds(2)
    );
    return new PdfGate("test_mock_key", config);
  }

  @Test
  public void generatePdfCallWithJsonResponseWithError() throws Exception {
    String errorMessage = "Required field 'pdf' is missing";
    Map<String, Object> payload = Map.of(
        "statusCode", 400,
        "error", "Bad Request",
        "message", errorMessage
    );
    String body = PdfGateJson.gson().toJson(payload);

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(400)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      GeneratePdfParams params = GeneratePdfParams.builder()
          .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
          .build();

      CountDownLatch latch = new CountDownLatch(1);
      AtomicReference<PdfGateDocument> success = new AtomicReference<>();
      AtomicReference<Throwable> failure = new AtomicReference<>();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      pdfGateClient.enqueue(pdfGateClient.generatePdfCall(params), new PdfGateCallback<>() {
        @Override
        public void onSuccess(okhttp3.Call call, PdfGateDocument value) {
          success.set(value);
          latch.countDown();
        }

        @Override
        public void onFailure(okhttp3.Call call, Throwable t) {
          failure.set(t);
          latch.countDown();
        }
      });

      Assertions.assertTrue(latch.await(2, TimeUnit.SECONDS), "callback should be invoked");
      Assertions.assertNull(success.get(), "success callback should not be invoked");
      Assertions.assertNotNull(failure.get(), "failure callback should be invoked");
      Assertions.assertInstanceOf(PdfGateException.class, failure.get(),
          "failure should be PdfGateException");
      Assertions.assertEquals(
          String.format("PdfGate API request failed with status 400: %s", errorMessage),
          failure.get().getMessage(),
          "error message should include JSON message"
      );
    }
  }

  @Test
  public void generatePdfRequestAlwaysIncludesJsonResponse() throws Exception {
    String body = PdfGateJson.gson().toJson(Map.of(
        "id", "doc_123",
        "status", "completed"
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      GeneratePdfParams params = GeneratePdfParams.builder()
          .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      pdfGateClient.generatePdf(params);

      String requestBody = server.takeRequest(2, TimeUnit.SECONDS).getBody().readUtf8();
      JsonObject requestJson = PdfGateJson.gson().fromJson(requestBody, JsonObject.class);
      Assertions.assertTrue(requestJson.get("jsonResponse").getAsBoolean(),
          "jsonResponse should be true");
    }
  }

  @Test
  public void multipartRequestsAlwaysIncludeJsonResponse() throws Exception {
    String documentBody = PdfGateJson.gson().toJson(Map.of(
        "id", "doc_123",
        "status", "completed"
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(documentBody));
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(documentBody));
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(documentBody));
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(documentBody));
      server.enqueue(new MockResponse()
          .setResponseCode(200)
          .setHeader("Content-Type", "application/json")
          .setBody("{\"field\":\"value\"}"));
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(documentBody));
      server.start();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());

      FlattenPdfParams flattenParams = FlattenPdfParams.builder()
          .documentId("doc_123")
          .build();
      pdfGateClient.flattenPdf(flattenParams);

      ProtectPdfParams protectParams = ProtectPdfParams.builder()
          .documentId("doc_123")
          .build();
      pdfGateClient.protectPdf(protectParams);

      CompressPdfParams compressParams = CompressPdfParams.builder()
          .documentId("doc_123")
          .build();
      pdfGateClient.compressPdf(compressParams);

      WatermarkPdfParams watermarkParams = WatermarkPdfParams.builder()
          .documentId("doc_123")
          .type(WatermarkPdfParams.WatermarkType.TEXT)
          .text("CONFIDENTIAL")
          .build();
      pdfGateClient.watermarkPdf(watermarkParams);

      ExtractPdfFormDataParams extractParams = ExtractPdfFormDataParams.builder()
          .documentId("doc_123")
          .build();
      pdfGateClient.extractPdfFormData(extractParams);

      UploadFileParams uploadParams = UploadFileParams.builder()
          .file(new FileParam("upload.pdf", "file".getBytes(java.nio.charset.StandardCharsets.UTF_8)))
          .build();
      pdfGateClient.uploadFile(uploadParams);

      for (int i = 0; i < 6; i++) {
        String requestBody = server.takeRequest(2, TimeUnit.SECONDS).getBody().readUtf8();
        Assertions.assertTrue(requestBody.contains("name=\"jsonResponse\""),
            "jsonResponse should be included in multipart body");
        Assertions.assertTrue(requestBody.contains("true"),
            "jsonResponse should be true");
      }
    }
  }

  @Test
  public void uploadFilePrefersMultipartWhenFileProvided() throws Exception {
    String body = PdfGateJson.gson().toJson(Map.of(
        "id", "doc_123",
        "status", "completed"
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      UploadFileParams params = UploadFileParams.builder()
          .file(new FileParam("upload.pdf", "file".getBytes(java.nio.charset.StandardCharsets.UTF_8)))
          .url("https://example.com/sample.pdf")
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      pdfGateClient.uploadFile(params);

      okhttp3.mockwebserver.RecordedRequest request =
          server.takeRequest(2, TimeUnit.SECONDS);
      Assertions.assertNotNull(request, "request should be recorded");
      String requestBody = request.getBody().readUtf8();
      String contentType = request.getHeader("Content-Type");
      Assertions.assertNotNull(contentType, "content type should be present");
      Assertions.assertTrue(contentType.startsWith("multipart/form-data"),
          "content type should be multipart when file is present");
      Assertions.assertTrue(requestBody.contains("name=\"file\"; filename=\"upload.pdf\""),
          "file part should be present");
      Assertions.assertFalse(requestBody.contains("name=\"url\""),
          "url should not be included when file is present");
    }
  }

  @Test
  public void uploadFileUsesJsonWhenNoFileProvided() throws Exception {
    String body = PdfGateJson.gson().toJson(Map.of(
        "id", "doc_123",
        "status", "completed"
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      UploadFileParams params = UploadFileParams.builder()
          .url("https://example.com/sample.pdf")
          .metadata(Map.of("source", "test"))
          .preSignedUrlExpiresIn(120L)
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      pdfGateClient.uploadFile(params);

      String requestBody = server.takeRequest(2, TimeUnit.SECONDS).getBody().readUtf8();
      JsonObject requestJson = PdfGateJson.gson().fromJson(requestBody, JsonObject.class);
      Assertions.assertEquals("https://example.com/sample.pdf",
          requestJson.get("url").getAsString(), "url should be included");
      Assertions.assertTrue(requestJson.get("jsonResponse").getAsBoolean(),
          "jsonResponse should be true");
      Assertions.assertEquals(120L, requestJson.get("preSignedUrlExpiresIn").getAsLong(),
          "preSignedUrlExpiresIn should be included");
      Assertions.assertEquals("test",
          requestJson.getAsJsonObject("metadata").get("source").getAsString(),
          "metadata should be included");
    }
  }

  @Test
  public void generatePdfCallWithJsonResponse() throws Exception {
    Random random = new Random();
    Instant now = Instant.now();
    String createdAt = DateTimeFormatter.ISO_INSTANT.format(now);
    Map<String, Object> payload = Map.of(
        "id", "6642381c5c61",
        "status", "completed",
        "type", "from_html",
        "size", random.nextInt(99999),
        "createdAt", createdAt
    );
    String body = PdfGateJson.gson().toJson(payload);

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      GeneratePdfParams params = GeneratePdfParams.builder()
          .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
          .build();

      CountDownLatch latch = new CountDownLatch(1);
      AtomicReference<PdfGateDocument> success = new AtomicReference<>();
      AtomicReference<Throwable> failure = new AtomicReference<>();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      pdfGateClient.enqueue(pdfGateClient.generatePdfCall(params), new PdfGateCallback<>() {
        @Override
        public void onSuccess(okhttp3.Call call, PdfGateDocument value) {
          success.set(value);
          latch.countDown();
        }

        @Override
        public void onFailure(okhttp3.Call call, Throwable t) {
          failure.set(t);
          latch.countDown();
        }
      });

      Assertions.assertTrue(latch.await(2, TimeUnit.SECONDS), "callback should be invoked");
      Assertions.assertNull(failure.get(), "failure callback should not be invoked");
      Assertions.assertNotNull(success.get(), "success callback should be invoked");
      Assertions.assertInstanceOf(PdfGateDocument.class, success.get(),
          "success should be PdfGateDocument");

      PdfGateDocument expected = PdfGateJson.gson().fromJson(body, PdfGateDocument.class);
      Assertions.assertEquals(
          expected,
          success.get(),
          "document should match JSON response"
      );
    }
  }

  @Test
  public void generatePdfCallWithIoFailureWrapsException() throws Exception {
    try (MockWebServer server = new MockWebServer()) {
      server.start();
      String baseUrl = server.url("/").toString();
      server.shutdown();

      GeneratePdfParams params = GeneratePdfParams.builder()
          .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
          .build();

      CountDownLatch latch = new CountDownLatch(1);
      AtomicReference<PdfGateDocument> success = new AtomicReference<>();
      AtomicReference<Throwable> failure = new AtomicReference<>();

      PdfGate pdfGateClient = buildClient(baseUrl);
      pdfGateClient.enqueue(pdfGateClient.generatePdfCall(params), new PdfGateCallback<>() {
        @Override
        public void onSuccess(okhttp3.Call call, PdfGateDocument value) {
          success.set(value);
          latch.countDown();
        }

        @Override
        public void onFailure(okhttp3.Call call, Throwable t) {
          failure.set(t);
          latch.countDown();
        }
      });

      Assertions.assertTrue(latch.await(3, TimeUnit.SECONDS), "callback should be invoked");
      Assertions.assertNull(success.get(), "success callback should not be invoked");
      Assertions.assertNotNull(failure.get(), "failure callback should be invoked");
      Assertions.assertInstanceOf(PdfGateException.class, failure.get(),
          "failure should be PdfGateException");
      Assertions.assertNotNull(failure.get().getCause(),
          "failure should preserve the original cause");
      Assertions.assertInstanceOf(IOException.class, failure.get().getCause(),
          "failure cause should be IOException");
    }
  }

  @Test
  public void generatePdfWithJsonResponse() throws Exception {
    Random random = new Random();
    Instant now = Instant.now();
    String createdAt = DateTimeFormatter.ISO_INSTANT.format(now);
    Map<String, Object> payload = Map.of(
        "id", "6642381c5c61",
        "status", "completed",
        "type", "from_html",
        "size", random.nextInt(99999),
        "createdAt", createdAt
    );
    String body = PdfGateJson.gson().toJson(payload);

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      GeneratePdfParams params = GeneratePdfParams.builder()
          .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      PdfGateDocument result = pdfGateClient.generatePdf(params);

      PdfGateDocument expected = PdfGateJson.gson().fromJson(body, PdfGateDocument.class);
      Assertions.assertEquals(
          expected,
          result,
          "document should match JSON response"
      );
    }
  }

  @Test
  public void generatePdfAsyncWithJsonResponseWithError() throws Exception {
    String errorMessage = "Required field 'pdf' is missing";
    Map<String, Object> payload = Map.of(
        "statusCode", 400,
        "error", "Bad Request",
        "message", errorMessage
    );
    String body = PdfGateJson.gson().toJson(payload);

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(400)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      GeneratePdfParams params = GeneratePdfParams.builder()
          .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      ExecutionException exception = Assertions.assertThrows(
          ExecutionException.class,
          () -> pdfGateClient.generatePdfAsync(params).get(2, TimeUnit.SECONDS),
          "future should complete exceptionally"
      );
      Assertions.assertInstanceOf(PdfGateException.class, exception.getCause(),
          "failure should be PdfGateException");
      Assertions.assertEquals(
          String.format("PdfGate API request failed with status 400: %s", errorMessage),
          exception.getCause().getMessage(),
          "error message should include JSON message"
      );
    }
  }

  @Test
  public void generatePdfAsyncWithJsonResponse() throws Exception {
    Random random = new Random();
    Instant now = Instant.now();
    String createdAt = DateTimeFormatter.ISO_INSTANT.format(now);
    Map<String, Object> payload = Map.of(
        "id", "6642381c5c61",
        "status", "completed",
        "type", "from_html",
        "size", random.nextInt(99999),
        "createdAt", createdAt
    );
    String body = PdfGateJson.gson().toJson(payload);

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      GeneratePdfParams params = GeneratePdfParams.builder()
          .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      PdfGateDocument result = pdfGateClient.generatePdfAsync(params).get(2, TimeUnit.SECONDS);
      Assertions.assertNotNull(result, "success should be PdfGateDocument");

      PdfGateDocument expected = PdfGateJson.gson().fromJson(body, PdfGateDocument.class);
      Assertions.assertEquals(
          expected,
          result,
          "document should match JSON response"
      );
    }
  }

  @Test
  public void extractPdfFormDataCallWithJsonResponseWithError() throws Exception {
    String errorMessage = "Invalid document id";
    Map<String, Object> payload = Map.of(
        "statusCode", 404,
        "error", "Not Found",
        "message", errorMessage
    );
    String body = PdfGateJson.gson().toJson(payload);

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(404)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      ExtractPdfFormDataParams params = ExtractPdfFormDataParams.builder()
          .documentId("doc_123")
          .build();

      CountDownLatch latch = new CountDownLatch(1);
      AtomicReference<JsonObject> success = new AtomicReference<>();
      AtomicReference<Throwable> failure = new AtomicReference<>();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      pdfGateClient.enqueue(pdfGateClient.extractPdfFormDataCall(params), new PdfGateCallback<>() {
        @Override
        public void onSuccess(okhttp3.Call call, JsonObject value) {
          success.set(value);
          latch.countDown();
        }

        @Override
        public void onFailure(okhttp3.Call call, Throwable t) {
          failure.set(t);
          latch.countDown();
        }
      });

      Assertions.assertTrue(latch.await(2, TimeUnit.SECONDS), "callback should be invoked");
      Assertions.assertNull(success.get(), "success callback should not be invoked");
      Assertions.assertNotNull(failure.get(), "failure callback should be invoked");
      Assertions.assertInstanceOf(PdfGateException.class, failure.get(),
          "failure should be PdfGateException");
      Assertions.assertEquals(
          String.format("PdfGate API request failed with status 404: %s", errorMessage),
          failure.get().getMessage(),
          "error message should include JSON message"
      );
    }
  }

  @Test
  public void extractPdfFormDataCallWithIoFailureWrapsException() throws Exception {
    try (MockWebServer server = new MockWebServer()) {
      server.start();
      String baseUrl = server.url("/").toString();
      server.shutdown();

      ExtractPdfFormDataParams params = ExtractPdfFormDataParams.builder()
          .documentId("doc_123")
          .build();

      CountDownLatch latch = new CountDownLatch(1);
      AtomicReference<JsonObject> success = new AtomicReference<>();
      AtomicReference<Throwable> failure = new AtomicReference<>();

      PdfGate pdfGateClient = buildClient(baseUrl);
      pdfGateClient.enqueue(pdfGateClient.extractPdfFormDataCall(params), new PdfGateCallback<>() {
        @Override
        public void onSuccess(okhttp3.Call call, JsonObject value) {
          success.set(value);
          latch.countDown();
        }

        @Override
        public void onFailure(okhttp3.Call call, Throwable t) {
          failure.set(t);
          latch.countDown();
        }
      });

      Assertions.assertTrue(latch.await(3, TimeUnit.SECONDS), "callback should be invoked");
      Assertions.assertNull(success.get(), "success callback should not be invoked");
      Assertions.assertNotNull(failure.get(), "failure callback should be invoked");
      Assertions.assertInstanceOf(PdfGateException.class, failure.get(),
          "failure should be PdfGateException");
      Assertions.assertNotNull(failure.get().getCause(),
          "failure should preserve the original cause");
      Assertions.assertInstanceOf(IOException.class, failure.get().getCause(),
          "failure cause should be IOException");
    }
  }

  @Test
  public void extractPdfFormDataAsyncWithJsonResponseWithError() throws Exception {
    String errorMessage = "Invalid document id";
    Map<String, Object> payload = Map.of(
        "statusCode", 404,
        "error", "Not Found",
        "message", errorMessage
    );
    String body = PdfGateJson.gson().toJson(payload);

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(404)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      ExtractPdfFormDataParams params = ExtractPdfFormDataParams.builder()
          .documentId("doc_123")
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      ExecutionException exception = Assertions.assertThrows(
          ExecutionException.class,
          () -> pdfGateClient.extractPdfFormDataAsync(params).get(2, TimeUnit.SECONDS),
          "future should complete exceptionally"
      );
      Assertions.assertInstanceOf(PdfGateException.class, exception.getCause(),
          "failure should be PdfGateException");
      Assertions.assertEquals(
          String.format("PdfGate API request failed with status 404: %s", errorMessage),
          exception.getCause().getMessage(),
          "error message should include JSON message"
      );
    }
  }

  @Test
  public void extractPdfFormDataAsyncWithIoFailureWrapsException() throws Exception {
    try (MockWebServer server = new MockWebServer()) {
      server.start();
      String baseUrl = server.url("/").toString();
      server.shutdown();

      ExtractPdfFormDataParams params = ExtractPdfFormDataParams.builder()
          .documentId("doc_123")
          .build();

      PdfGate pdfGateClient = buildClient(baseUrl);
      ExecutionException exception = Assertions.assertThrows(
          ExecutionException.class,
          () -> pdfGateClient.extractPdfFormDataAsync(params).get(2, TimeUnit.SECONDS),
          "future should complete exceptionally"
      );
      Assertions.assertInstanceOf(PdfGateException.class, exception.getCause(),
          "failure should be PdfGateException");
      Assertions.assertTrue(
          exception.getCause().getMessage()
              .startsWith("PdfGate API request failed: Failed to connect"),
          "error message should include JSON message"
      );
    }
  }
}
