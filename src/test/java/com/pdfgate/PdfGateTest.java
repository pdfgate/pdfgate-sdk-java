package com.pdfgate;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PdfGateTest {
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

            PdfGateConfig config = PdfGateConfig.of(
                    "https://invalid-production-host",
                    server.url("/").toString(),
                    Duration.ofSeconds(2),
                    Duration.ofSeconds(2)
            );
            PdfGate localClient = new PdfGate("test_mock_key", config);

            GeneratePdfParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .jsonResponse(true)
                    .build();

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<PdfGateDocument> success = new AtomicReference<>();
            AtomicReference<Throwable> failure = new AtomicReference<>();

            localClient.enqueue(localClient.generatePdfCall(params), new PDFGateCallback<>() {
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

            PdfGateConfig config = PdfGateConfig.of(
                    "https://invalid-production-host",
                    server.url("/").toString(),
                    Duration.ofSeconds(2),
                    Duration.ofSeconds(2)
            );
            PdfGate localClient = new PdfGate("test_mock_key", config);

            GeneratePdfParams params = GeneratePdfParams.builder()
                    .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
                    .jsonResponse(true)
                    .build();

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<PdfGateDocument> success = new AtomicReference<>();
            AtomicReference<Throwable> failure = new AtomicReference<>();

            localClient.enqueue(localClient.generatePdfCall(params), new PDFGateCallback<>() {
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
}
