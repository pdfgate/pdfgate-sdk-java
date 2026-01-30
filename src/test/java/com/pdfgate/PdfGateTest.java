package com.pdfgate;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

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

            GeneratePdfJsonParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .buildJson();

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<PdfGateDocument> success = new AtomicReference<>();
            AtomicReference<Throwable> failure = new AtomicReference<>();

            PdfGate pdfGateClient = buildClient(server.url("/").toString());
            pdfGateClient.enqueue(pdfGateClient.generatePdfCall(params), new PDFGateCallback<>() {
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
            Assertions.assertInstanceOf(PdfGateException.class, failure.get(), "failure should be PdfGateException");
            Assertions.assertEquals(
                    String.format("PdfGate API request failed with status 400: %s", errorMessage),
                    failure.get().getMessage(),
                    "error message should include JSON message"
            );
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

            GeneratePdfJsonParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .buildJson();

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<PdfGateDocument> success = new AtomicReference<>();
            AtomicReference<Throwable> failure = new AtomicReference<>();

            PdfGate pdfGateClient = buildClient(server.url("/").toString());
            pdfGateClient.enqueue(pdfGateClient.generatePdfCall(params), new PDFGateCallback<>() {
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
            Assertions.assertInstanceOf(PdfGateDocument.class, success.get(), "success should be PdfGateDocument");

            PdfGateDocument expected = PdfGateJson.gson().fromJson(body, PdfGateDocument.class);
            Assertions.assertEquals(
                    expected,
                    success.get(),
                    "document should match JSON response"
            );
        }
    }

    @Test
    public void generatePdfCallWithBytesResponse() throws Exception {
        byte[] pdfBytes = "%%PDF-1.4\\n%\\xd3\\xeb\\xe9\\xe1\\n1 0 obj\\n<</Title (PDF - Wikipedia)\\n/Creator (Mozilla/5.0 \\\\(X11; Linux x86_64\\\\) AppleW".getBytes(StandardCharsets.UTF_8);
        Buffer buffer = new Buffer().write(pdfBytes);

        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse()
                    .setResponseCode(201)
                    .setHeader("Content-Type", "application/octet-stream")
                    .setBody(buffer));
            server.start();

            GeneratePdfBytesParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .buildBytes();

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<byte[]> success = new AtomicReference<>();
            AtomicReference<Throwable> failure = new AtomicReference<>();

            PdfGate pdfGateClient = buildClient(server.url("/").toString());
            pdfGateClient.enqueue(pdfGateClient.generatePdfCall(params), new PDFGateCallback<>() {
                @Override
                public void onSuccess(okhttp3.Call call, byte[] value) {
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
            Assertions.assertArrayEquals(pdfBytes, success.get(), "bytes should match response");
        }
    }

    @Test
    public void generatePdfCallWithIoFailureWrapsException() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.start();
            String baseUrl = server.url("/").toString();
            server.shutdown();

            GeneratePdfJsonParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .buildJson();

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<PdfGateDocument> success = new AtomicReference<>();
            AtomicReference<Throwable> failure = new AtomicReference<>();

            PdfGate pdfGateClient = buildClient(baseUrl);
            pdfGateClient.enqueue(pdfGateClient.generatePdfCall(params), new PDFGateCallback<>() {
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
            Assertions.assertInstanceOf(PdfGateException.class, failure.get(), "failure should be PdfGateException");
            Assertions.assertNotNull(failure.get().getCause(), "failure should preserve the original cause");
            Assertions.assertInstanceOf(IOException.class, failure.get().getCause(), "failure cause should be IOException");
        }
    }

    @Test
    public void generatePdfCallWithBytesIoFailureWrapsException() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.start();
            String baseUrl = server.url("/").toString();
            server.shutdown();

            GeneratePdfBytesParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .buildBytes();

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<byte[]> success = new AtomicReference<>();
            AtomicReference<Throwable> failure = new AtomicReference<>();

            PdfGate pdfGateClient = buildClient(baseUrl);
            pdfGateClient.enqueue(pdfGateClient.generatePdfCall(params), new PDFGateCallback<>() {
                @Override
                public void onSuccess(okhttp3.Call call, byte[] value) {
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
            Assertions.assertInstanceOf(PdfGateException.class, failure.get(), "failure should be PdfGateException");
            Assertions.assertNotNull(failure.get().getCause(), "failure should preserve the original cause");
            Assertions.assertInstanceOf(IOException.class, failure.get().getCause(), "failure cause should be IOException");
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

            GeneratePdfJsonParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .buildJson();

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
    public void generatePdfWithBytesResponse() throws Exception {
        byte[] pdfBytes = "%%PDF-1.4\\n%\\xd3\\xeb\\xe9\\xe1\\n1 0 obj\\n<</Title (PDF - Wikipedia)\\n/Creator (Mozilla/5.0 \\\\(X11; Linux x86_64\\\\) AppleW".getBytes(StandardCharsets.UTF_8);
        Buffer buffer = new Buffer().write(pdfBytes);

        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse()
                    .setResponseCode(201)
                    .setHeader("Content-Type", "application/octet-stream")
                    .setBody(buffer));
            server.start();

            GeneratePdfBytesParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .buildBytes();

            PdfGate pdfGateClient = buildClient(server.url("/").toString());
            byte[] result = pdfGateClient.generatePdf(params);
            Assertions.assertArrayEquals(pdfBytes, result, "bytes should match response");
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

            GeneratePdfJsonParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .buildJson();

            PdfGate pdfGateClient = buildClient(server.url("/").toString());
            ExecutionException exception = Assertions.assertThrows(
                    ExecutionException.class,
                    () -> pdfGateClient.generatePdfAsync(params).get(2, TimeUnit.SECONDS),
                    "future should complete exceptionally"
            );
            Assertions.assertInstanceOf(PdfGateException.class, exception.getCause(), "failure should be PdfGateException");
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

            GeneratePdfJsonParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .buildJson();

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
    public void generatePdfAsyncWithBytesResponse() throws Exception {
        byte[] pdfBytes = "%%PDF-1.4\\n%\\xd3\\xeb\\xe9\\xe1\\n1 0 obj\\n<</Title (PDF - Wikipedia)\\n/Creator (Mozilla/5.0 \\\\(X11; Linux x86_64\\\\) AppleW".getBytes(StandardCharsets.UTF_8);
        Buffer buffer = new Buffer().write(pdfBytes);

        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse()
                    .setResponseCode(201)
                    .setHeader("Content-Type", "application/octet-stream")
                    .setBody(buffer));
            server.start();

            GeneratePdfBytesParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .buildBytes();

            PdfGate pdfGateClient = buildClient(server.url("/").toString());
            byte[] result = pdfGateClient.generatePdfAsync(params).get(2, TimeUnit.SECONDS);
            Assertions.assertArrayEquals(pdfBytes, result, "bytes should match response");
        }
    }
}
