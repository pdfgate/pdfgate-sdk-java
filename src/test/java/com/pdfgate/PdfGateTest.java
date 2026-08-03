package com.pdfgate;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
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
  private Map<String, Object> mapOf(Object... keyValues) {
    Map<String, Object> values = new LinkedHashMap<String, Object>();
    for (int i = 0; i < keyValues.length; i += 2) {
      values.put((String) keyValues[i], keyValues[i + 1]);
    }
    return values;
  }

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
    Map<String, Object> payload = mapOf(
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
      pdfGateClient.enqueue(pdfGateClient.generatePdfCall(params),
          new PdfGateCallback<PdfGateDocument>() {
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
  public void generatePdfErrorResponseBodyIsTruncated() throws Exception {
    StringBuilder bodyBuilder = new StringBuilder();
    for (int i = 0; i < 5000; i++) {
      bodyBuilder.append('x');
    }
    String body = bodyBuilder.toString();

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(400)
          .setHeader("Content-Type", "text/plain")
          .setBody(body));
      server.start();

      GeneratePdfParams params = GeneratePdfParams.builder()
          .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      PdfGateException exception = Assertions.assertThrows(
          PdfGateException.class,
          () -> pdfGateClient.generatePdf(params)
      );

      Assertions.assertEquals(400, exception.getStatusCode(),
          "status code should be preserved");
      Assertions.assertTrue(exception.getResponseBody().length() <= 4096,
          "stored response body should be truncated");
      Assertions.assertTrue(exception.getResponseBody().endsWith("...(truncated)"),
          "stored response body should indicate truncation");
      Assertions.assertTrue(exception.getMessage().endsWith("...(truncated)"),
          "error message should indicate truncation");
    }
  }

  @Test
  public void generatePdfRequestAlwaysIncludesJsonResponse() throws Exception {
    String body = PdfGateJson.gson().toJson(mapOf(
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
  public void multipartRequestsIncludeJsonResponseExceptExtractAndUpload() throws Exception {
    String documentBody = PdfGateJson.gson().toJson(mapOf(
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

      // flatten, protect, compress, watermark support jsonResponse and must send it.
      for (int i = 0; i < 4; i++) {
        String requestBody = server.takeRequest(2, TimeUnit.SECONDS).getBody().readUtf8();
        Assertions.assertTrue(requestBody.contains("name=\"jsonResponse\""),
            "jsonResponse should be included in multipart body");
        Assertions.assertTrue(requestBody.contains("true"),
            "jsonResponse should be true");
      }

      // extract-data and upload always return JSON and have no jsonResponse param.
      String extractBody = server.takeRequest(2, TimeUnit.SECONDS).getBody().readUtf8();
      Assertions.assertFalse(extractBody.contains("name=\"jsonResponse\""),
          "extract-data must not send jsonResponse");
      String uploadBody = server.takeRequest(2, TimeUnit.SECONDS).getBody().readUtf8();
      Assertions.assertFalse(uploadBody.contains("name=\"jsonResponse\""),
          "upload must not send jsonResponse");
    }
  }

  @Test
  public void uploadFilePrefersMultipartWhenFileProvided() throws Exception {
    String body = PdfGateJson.gson().toJson(mapOf(
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
    String body = PdfGateJson.gson().toJson(mapOf(
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
          .metadata(mapOf("source", "test"))
          .preSignedUrlExpiresIn(120L)
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      pdfGateClient.uploadFile(params);

      String requestBody = server.takeRequest(2, TimeUnit.SECONDS).getBody().readUtf8();
      JsonObject requestJson = PdfGateJson.gson().fromJson(requestBody, JsonObject.class);
      Assertions.assertEquals("https://example.com/sample.pdf",
          requestJson.get("url").getAsString(), "url should be included");
      Assertions.assertFalse(requestJson.has("jsonResponse"),
          "upload has no jsonResponse param");
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
    Map<String, Object> payload = mapOf(
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
      pdfGateClient.enqueue(pdfGateClient.generatePdfCall(params),
          new PdfGateCallback<PdfGateDocument>() {
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
      pdfGateClient.enqueue(pdfGateClient.generatePdfCall(params),
          new PdfGateCallback<PdfGateDocument>() {
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
    Map<String, Object> payload = mapOf(
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
  public void generatePdfWithMalformedSuccessPayloadWrapsException() throws Exception {
    String body = "{\"id\":\"doc_123\",\"createdAt\":\"not-an-instant\"}";

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(200)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      GeneratePdfParams params = GeneratePdfParams.builder()
          .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      PdfGateException exception = Assertions.assertThrows(
          PdfGateException.class,
          () -> pdfGateClient.generatePdf(params)
      );

      Assertions.assertEquals(200, exception.getStatusCode(),
          "status code should be preserved on parse failure");
      Assertions.assertEquals(body, exception.getResponseBody(),
          "raw response body should be preserved on parse failure");
      Assertions.assertNotNull(exception.getCause(),
          "parse failure should preserve the original cause");
      Assertions.assertTrue(
          exception.getMessage().startsWith(
              "PdfGate API request failed while parsing successful response with status 200:"),
          "error message should describe a parse failure"
      );
    }
  }

  @Test
  public void generatePdfAsyncWithJsonResponseWithError() throws Exception {
    String errorMessage = "Required field 'pdf' is missing";
    Map<String, Object> payload = mapOf(
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
    Map<String, Object> payload = mapOf(
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
  public void generatePdfAsyncWithMalformedSuccessPayloadWrapsException() throws Exception {
    String body = "{\"id\":\"doc_123\",\"createdAt\":\"not-an-instant\"}";

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(200)
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
      PdfGateException pdfGateException = (PdfGateException) exception.getCause();
      Assertions.assertEquals(200, pdfGateException.getStatusCode(),
          "status code should be preserved on parse failure");
      Assertions.assertEquals(body, pdfGateException.getResponseBody(),
          "raw response body should be preserved on parse failure");
      Assertions.assertNotNull(pdfGateException.getCause(),
          "parse failure should preserve the original cause");
    }
  }

  @Test
  public void createEnvelopeSerializesNestedCamelCaseKeysAndParsesResponse() throws Exception {
    String createdAt = "2024-02-13T15:56:12.607Z";
    String body = PdfGateJson.gson().toJson(mapOf(
        "id", "env_123",
        "status", "created",
        "documents", java.util.Collections.singletonList(mapOf(
            "sourceDocumentId", "src_123",
            "recipients", java.util.Collections.singletonList(mapOf(
                "email", "anna@example.com",
                "status", "pending",
                "fields", java.util.Collections.singletonList(mapOf(
                    "name", "signature",
                    "type", "signature"
                ))
            )),
            "status", "pending"
        )),
        "createdAt", createdAt,
        "metadata", mapOf(
            "customerId", "cus_123",
            "department", "sales"
        )
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      CreateEnvelopeParams params = CreateEnvelopeParams.builder()
          .requesterName("John Doe")
          .documents(java.util.Collections.singletonList(
              EnvelopeDocument.builder()
                  .sourceDocumentId("src_123")
                  .name("Employment Agreement")
                  .recipients(java.util.Collections.singletonList(
                      EnvelopeRecipient.builder()
                          .email("anna@example.com")
                          .name("Anna Smith")
                          .role("signer")
                          .build()
                  ))
                  .build()
          ))
          .metadata(mapOf(
              "customerId", "cus_123",
              "department", "sales"
          ))
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      PDFGateEnvelope result = pdfGateClient.createEnvelope(params);

      String requestBody = server.takeRequest(2, TimeUnit.SECONDS).getBody().readUtf8();
      JsonObject requestJson = PdfGateJson.gson().fromJson(requestBody, JsonObject.class);
      Assertions.assertEquals("John Doe", requestJson.get("requesterName").getAsString());
      Assertions.assertFalse(requestJson.has("requester_name"),
          "requesterName should remain camelCase");
      JsonObject requestDocument = requestJson.getAsJsonArray("documents").get(0).getAsJsonObject();
      Assertions.assertEquals("src_123", requestDocument.get("sourceDocumentId").getAsString());
      Assertions.assertFalse(requestDocument.has("source_document_id"),
          "sourceDocumentId should remain camelCase");
      JsonObject requestRecipient = requestDocument.getAsJsonArray("recipients")
          .get(0)
          .getAsJsonObject();
      Assertions.assertEquals("anna@example.com", requestRecipient.get("email").getAsString());
      Assertions.assertEquals("Anna Smith", requestRecipient.get("name").getAsString());
      Assertions.assertEquals("signer", requestRecipient.get("role").getAsString());

      Assertions.assertEquals("env_123", result.getId(), "envelope id should be present");
      Assertions.assertEquals(EnvelopeStatus.CREATED, result.getStatus(),
          "envelope status should use enum values");
      Assertions.assertEquals(EnvelopeDocumentStatus.PENDING,
          result.getDocuments().get(0).getStatus(),
          "document status should use enum values");
      Assertions.assertEquals(DocumentRecipientStatus.PENDING,
          result.getDocuments().get(0).getRecipients().get(0).getStatus(),
          "recipient status should use enum values");
      Assertions.assertEquals(DocumentFieldType.SIGNATURE,
          result.getDocuments().get(0).getRecipients().get(0).getFields().get(0).getType(),
          "field type should use enum values");
      Assertions.assertEquals(Instant.parse(createdAt), result.getCreatedAt(),
          "createdAt should parse as an instant");
      Assertions.assertEquals("cus_123",
          result.getMetadata().orElseThrow(AssertionError::new).get("customerId"),
          "metadata should round-trip");
    }
  }

  @Test
  public void createEnvelopeOmitsOptionalFieldsRecursively() throws Exception {
    String body = PdfGateJson.gson().toJson(mapOf(
        "id", "env_123",
        "status", "created",
        "documents", java.util.Collections.singletonList(mapOf(
            "sourceDocumentId", "src_123",
            "recipients", java.util.Collections.singletonList(mapOf(
                "email", "anna@example.com",
                "status", "pending",
                "fields", java.util.Collections.emptyList()
            )),
            "status", "pending"
        )),
        "createdAt", "2024-02-13T15:56:12.607Z"
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      CreateEnvelopeParams params = CreateEnvelopeParams.builder()
          .requesterName("John Doe")
          .documents(java.util.Collections.singletonList(
              EnvelopeDocument.builder()
                  .sourceDocumentId("src_123")
                  .name("Employment Agreement")
                  .recipients(java.util.Collections.singletonList(
                      EnvelopeRecipient.builder()
                          .email("anna@example.com")
                          .name("Anna Smith")
                          .build()
                  ))
                  .build()
          ))
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      pdfGateClient.createEnvelope(params);

      String requestBody = server.takeRequest(2, TimeUnit.SECONDS).getBody().readUtf8();
      JsonObject requestJson = PdfGateJson.gson().fromJson(requestBody, JsonObject.class);
      Assertions.assertFalse(requestJson.has("metadata"), "metadata should be omitted");
      JsonObject requestRecipient = requestJson.getAsJsonArray("documents")
          .get(0)
          .getAsJsonObject()
          .getAsJsonArray("recipients")
          .get(0)
          .getAsJsonObject();
      Assertions.assertFalse(requestRecipient.has("role"), "role should be omitted");
    }
  }

  @Test
  public void createEnvelopeAsyncReturnsEnvelopeResponse() throws Exception {
    String body = PdfGateJson.gson().toJson(mapOf(
        "id", "env_123",
        "status", "created",
        "documents", java.util.Collections.singletonList(mapOf(
            "sourceDocumentId", "src_123",
            "recipients", java.util.Collections.singletonList(mapOf(
                "email", "anna@example.com",
                "status", "pending",
                "fields", java.util.Collections.emptyList()
            )),
            "status", "pending"
        )),
        "createdAt", "2024-02-13T15:56:12.607Z"
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      CreateEnvelopeParams params = CreateEnvelopeParams.builder()
          .requesterName("John Doe")
          .documents(java.util.Collections.singletonList(
              EnvelopeDocument.builder()
                  .sourceDocumentId("src_123")
                  .name("Employment Agreement")
                  .recipients(java.util.Collections.singletonList(
                      EnvelopeRecipient.builder()
                          .email("anna@example.com")
                          .name("Anna Smith")
                          .build()
                  ))
                  .build()
          ))
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      PDFGateEnvelope result = pdfGateClient.createEnvelopeAsync(params).get(2, TimeUnit.SECONDS);

      Assertions.assertEquals("env_123", result.getId(), "envelope id should be present");
      Assertions.assertEquals(EnvelopeStatus.CREATED, result.getStatus(),
          "status should parse correctly");
    }
  }

  @Test
  public void extractPdfFormDataCallWithJsonResponseWithError() throws Exception {
    String errorMessage = "Invalid document id";
    Map<String, Object> payload = mapOf(
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
      pdfGateClient.enqueue(pdfGateClient.extractPdfFormDataCall(params),
          new PdfGateCallback<JsonObject>() {
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
      pdfGateClient.enqueue(pdfGateClient.extractPdfFormDataCall(params),
          new PdfGateCallback<JsonObject>() {
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
    Map<String, Object> payload = mapOf(
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

  @Test
  public void watermarkPdfSendsFontFile() throws Exception {
    String body = PdfGateJson.gson().toJson(mapOf(
        "id", "doc_wm",
        "status", "completed",
        "type", "watermarked",
        "createdAt", "2024-02-13T15:56:12.607Z"
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      WatermarkPdfParams params = WatermarkPdfParams.builder()
          .documentId("doc_123")
          .type(WatermarkPdfParams.WatermarkType.TEXT)
          .text("Confidential")
          .fontFile(new FileParam("custom.ttf", "fake-font-bytes".getBytes(), "font/ttf"))
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      PdfGateDocument result = pdfGateClient.watermarkPdf(params);

      okhttp3.mockwebserver.RecordedRequest request = server.takeRequest(2, TimeUnit.SECONDS);
      String requestBody = request.getBody().readUtf8();
      Assertions.assertEquals("/watermark/pdf", request.getPath(), "path should be watermark/pdf");
      Assertions.assertTrue(requestBody.contains("name=\"fontFile\""),
          "multipart body should include the fontFile part");
      Assertions.assertTrue(requestBody.contains("filename=\"custom.ttf\""),
          "multipart body should include the font file name");
      Assertions.assertEquals("doc_wm", result.getId(), "document id should be parsed");
    }
  }

  @Test
  public void flattenPdfSendsFieldNames() throws Exception {
    String body = PdfGateJson.gson().toJson(mapOf(
        "id", "doc_flat",
        "status", "completed",
        "type", "flattened",
        "derivedFrom", "doc_123",
        "createdAt", "2024-02-13T15:56:12.607Z"
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      FlattenPdfParams params = FlattenPdfParams.builder()
          .documentId("doc_123")
          .fieldNames(java.util.Arrays.asList("name", "email"))
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      PdfGateDocument result = pdfGateClient.flattenPdf(params);

      okhttp3.mockwebserver.RecordedRequest request = server.takeRequest(2, TimeUnit.SECONDS);
      String requestBody = request.getBody().readUtf8();
      Assertions.assertEquals("/forms/flatten", request.getPath(), "path should be forms/flatten");
      Assertions.assertTrue(requestBody.contains("name=\"fieldNames\""),
          "multipart body should include fieldNames part");
      Assertions.assertTrue(requestBody.contains("[\"name\",\"email\"]"),
          "fieldNames should be sent as a JSON array");
      Assertions.assertTrue(requestBody.contains("name=\"jsonResponse\""),
          "multipart body should request a JSON response");
      Assertions.assertEquals("doc_flat", result.getId(), "document id should be parsed");
    }
  }

  @Test
  public void addFormFieldsSendsOverridesAndFields() throws Exception {
    String body = PdfGateJson.gson().toJson(mapOf(
        "id", "doc_fields",
        "status", "completed",
        "type", "document_fields_added",
        "derivedFrom", "doc_123",
        "createdAt", "2024-02-13T15:56:12.607Z"
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      AddFormFieldsParams params = AddFormFieldsParams.builder()
          .documentId("doc_123")
          .fieldOverrides(java.util.Collections.singletonMap(
              "full_name",
              FieldOverride.builder().role("signer").fontSize(12).build()
          ))
          .fields(java.util.Collections.singletonList(
              ManualFormField.builder()
                  .name("signed_on")
                  .type(DocumentFieldType.DATE)
                  .page(1)
                  .x(10.0)
                  .y(20.0)
                  .width(100)
                  .height(24)
                  .fontSize(10)
                  .build()
          ))
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      PdfGateDocument result = pdfGateClient.addFormFields(params);

      okhttp3.mockwebserver.RecordedRequest request = server.takeRequest(2, TimeUnit.SECONDS);
      Assertions.assertEquals("/forms/fields", request.getPath(), "path should be forms/fields");
      JsonObject requestJson = PdfGateJson.gson()
          .fromJson(request.getBody().readUtf8(), JsonObject.class);
      Assertions.assertEquals("doc_123", requestJson.get("documentId").getAsString());
      Assertions.assertTrue(requestJson.get("jsonResponse").getAsBoolean(),
          "jsonResponse should be true");
      JsonObject overrides = requestJson.getAsJsonObject("fieldOverrides");
      Assertions.assertTrue(overrides.has("full_name"),
          "field-override keys must be preserved verbatim");
      Assertions.assertEquals("signer",
          overrides.getAsJsonObject("full_name").get("role").getAsString());
      Assertions.assertEquals(12,
          overrides.getAsJsonObject("full_name").get("fontSize").getAsInt(),
          "override option keys should be camelCase");
      JsonObject manualField = requestJson.getAsJsonArray("fields").get(0).getAsJsonObject();
      Assertions.assertEquals("signed_on", manualField.get("name").getAsString());
      Assertions.assertEquals("date", manualField.get("type").getAsString());
      Assertions.assertEquals(10, manualField.get("fontSize").getAsInt());
      Assertions.assertEquals("doc_fields", result.getId(), "document id should be parsed");
      Assertions.assertEquals(PdfGateDocument.DocumentType.DOCUMENT_FIELDS_ADDED, result.getType(),
          "document type should parse the new enum value");
    }
  }

  @Test
  public void deleteDocumentSendsDeleteRequest() throws Exception {
    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse().setResponseCode(204));
      server.start();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      pdfGateClient.deleteDocument(DeleteDocumentParams.builder().documentId("doc_123").build());

      okhttp3.mockwebserver.RecordedRequest request = server.takeRequest(2, TimeUnit.SECONDS);
      Assertions.assertEquals("DELETE", request.getMethod(), "method should be DELETE");
      Assertions.assertEquals("/document/doc_123", request.getPath(),
          "path should target the document");
    }
  }

  @Test
  public void createWebhookSendsConfigAndParsesResponse() throws Exception {
    String body = PdfGateJson.gson().toJson(mapOf(
        "id", "wh_123",
        "url", "https://example.com/hook",
        "eventTypes", java.util.Collections.singletonList("envelope.completed"),
        "status", "active",
        "secret", "whsec_abc",
        "createdAt", "2024-02-13T15:56:12.607Z"
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(201)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      CreateWebhookParams params = CreateWebhookParams.builder()
          .url("https://example.com/hook")
          .eventTypes(java.util.Arrays.asList(
              WebhookEventType.ENVELOPE_COMPLETED,
              WebhookEventType.ENVELOPE_SENT
          ))
          .description("my hook")
          .build();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      PdfGateWebhookResponse result = pdfGateClient.createWebhook(params);

      okhttp3.mockwebserver.RecordedRequest request = server.takeRequest(2, TimeUnit.SECONDS);
      Assertions.assertEquals("/webhook", request.getPath(), "path should be webhook");
      JsonObject requestJson = PdfGateJson.gson()
          .fromJson(request.getBody().readUtf8(), JsonObject.class);
      Assertions.assertEquals("https://example.com/hook", requestJson.get("url").getAsString());
      Assertions.assertEquals("envelope.completed",
          requestJson.getAsJsonArray("eventTypes").get(0).getAsString(),
          "event types should serialize to their wire values");
      Assertions.assertEquals("my hook", requestJson.get("description").getAsString());

      Assertions.assertEquals("wh_123", result.getId(), "webhook id should be parsed");
      Assertions.assertEquals(WebhookStatus.ACTIVE, result.getStatus());
      Assertions.assertEquals(WebhookEventType.ENVELOPE_COMPLETED, result.getEventTypes().get(0));
      Assertions.assertEquals("whsec_abc",
          result.getSecret().orElseThrow(AssertionError::new),
          "secret should be present at creation");
    }
  }

  @Test
  public void getWebhookReturnsResponse() throws Exception {
    String body = PdfGateJson.gson().toJson(mapOf(
        "id", "wh_123",
        "url", "https://example.com/hook",
        "eventTypes", java.util.Collections.singletonList("envelope.sent"),
        "status", "active",
        "createdAt", "2024-02-13T15:56:12.607Z"
    ));

    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse()
          .setResponseCode(200)
          .setHeader("Content-Type", "application/json")
          .setBody(body));
      server.start();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      PdfGateWebhookResponse result =
          pdfGateClient.getWebhook(GetWebhookParams.builder().id("wh_123").build());

      okhttp3.mockwebserver.RecordedRequest request = server.takeRequest(2, TimeUnit.SECONDS);
      Assertions.assertEquals("GET", request.getMethod(), "method should be GET");
      Assertions.assertEquals("/webhook/wh_123", request.getPath(), "path should target the webhook");
      Assertions.assertEquals("wh_123", result.getId(), "webhook id should be parsed");
      Assertions.assertFalse(result.getSecret().isPresent(),
          "secret should not be returned outside of creation");
    }
  }

  @Test
  public void deleteWebhookSendsDeleteRequest() throws Exception {
    try (MockWebServer server = new MockWebServer()) {
      server.enqueue(new MockResponse().setResponseCode(204));
      server.start();

      PdfGate pdfGateClient = buildClient(server.url("/").toString());
      pdfGateClient.deleteWebhook(DeleteWebhookParams.builder().id("wh_123").build());

      okhttp3.mockwebserver.RecordedRequest request = server.takeRequest(2, TimeUnit.SECONDS);
      Assertions.assertEquals("DELETE", request.getMethod(), "method should be DELETE");
      Assertions.assertEquals("/webhook/wh_123", request.getPath(),
          "path should target the webhook");
    }
  }
}
