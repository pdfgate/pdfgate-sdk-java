package com.pdfgate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

public final class PdfGate {
    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    private final String apiKey;
    private final OkHttpClient httpClient;
    private final PdfGateConfig config;
    private final UrlBuilder urlBuilder;

    public PdfGate(String apiKey) {
        this(apiKey, PdfGateConfig.defaultConfig());
    }

    public PdfGate(String apiKey, PdfGateConfig config) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("apiKey must be provided.");
        }
        if (config == null) {
            throw new IllegalArgumentException("config must be provided.");
        }
        this.config = config;
        this.urlBuilder = new UrlBuilder(apiKey, config);
        this.apiKey = apiKey;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(config.getDefaultTimeout())
                .build();
    }

    /**
     * Generates a PDF and returns raw bytes.
     */
    public byte[] generatePdf(GeneratePdfBytesParams params)
            throws IOException {
        try {
            try (Response response = generatePdfCall(params).execute()) {
                if (!response.isSuccessful()) {
                    throw PdfGateException.fromResponse(response);
                }
                ResponseBody responseBody = response.body();
                return responseBody == null ? new byte[0] : responseBody.bytes();
            }
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Generates a PDF and returns the document metadata from a JSON response.
     */
    public PdfGateDocument generatePdf(GeneratePdfJsonParams params)
            throws IOException {
        try {
            try (Response response = generatePdfCall(params).execute()) {
                if (!response.isSuccessful()) {
                    throw PdfGateException.fromResponse(response);
                }
                ResponseBody responseBody = response.body();
                String responseJson = responseBody == null ? "" : responseBody.string();
                return PdfGateJson.gson().fromJson(responseJson, PdfGateDocument.class);
            }
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Generates a PDF asynchronously and returns raw bytes.
     */
    public CompletableFuture<byte[]> generatePdfAsync(GeneratePdfBytesParams params) {
        Call call = generatePdfCall(params);
        CompletableFuture<byte[]> future = new CompletableFuture<>();

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(PdfGateException.fromException(e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try (Response r = response) {
                    if (!r.isSuccessful()) {
                        future.completeExceptionally(PdfGateException.fromResponse(r));
                        return;
                    }

                    ResponseBody responseBody = r.body();
                    byte[] bytes = responseBody == null ? new byte[0] : responseBody.bytes();
                    future.complete(bytes);
                } catch (IOException e) {
                    future.completeExceptionally(e);
                }
            }
        });

        future.whenComplete((r, t) -> {
            if (future.isCancelled()) {
                call.cancel();
            }
        });

        return future;
    }

    /**
     * Generates a PDF asynchronously and returns the document metadata from a JSON response.
     */
    public CompletableFuture<PdfGateDocument> generatePdfAsync(GeneratePdfJsonParams params) {
        Call call = generatePdfCall(params);
        CompletableFuture<PdfGateDocument> future = new CompletableFuture<>();

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(PdfGateException.fromException(e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try (Response r = response) {
                    if (!r.isSuccessful()) {
                        future.completeExceptionally(PdfGateException.fromResponse(r));
                        return;
                    }

                    ResponseBody responseBody = r.body();
                    String responseJson = responseBody == null ? "" : responseBody.string();
                    PdfGateDocument document = PdfGateJson.gson().fromJson(responseJson, PdfGateDocument.class);
                    future.complete(document);
                } catch (IOException e) {
                    future.completeExceptionally(e);
                }
            }
        });

        future.whenComplete((r, t) -> {
            if (future.isCancelled()) {
                call.cancel();
            }
        });

        return future;
    }

    /**
     * Enqueues a JSON response call and maps the response to {@link PdfGateDocument}.
     */
    public void enqueue(CallJson call, PDFGateCallback<PdfGateDocument> callback) {
        call.enqueue(new Callback() {
            @Override public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(call, e);
            }

            @Override public void onResponse(@NotNull Call call, @NotNull Response response) {
                try (Response r = response) {
                    if (!r.isSuccessful()) {
                        callback.onFailure(call, PdfGateException.fromResponse(r));
                        return;
                    }
                    ResponseBody responseBody = r.body();
                    String responseJson = responseBody == null ? "" : responseBody.string();
                    PdfGateDocument document = PdfGateJson.gson().fromJson(responseJson, PdfGateDocument.class);
                    callback.onSuccess(call, document);
                } catch (Exception e) {
                    callback.onFailure(call, e);
                }
            }
        });
    }

    /**
     * Enqueues a bytes response call and returns the raw response bytes.
     */
    public void enqueue(CallBytes call, PDFGateCallback<byte[]> callback) {
        call.enqueue(new Callback() {
            @Override public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(call, e);
            }

            @Override public void onResponse(@NotNull Call call, @NotNull Response response) {
                try (Response r = response) {
                    if (!r.isSuccessful()) {
                        callback.onFailure(call, PdfGateException.fromResponse(r));
                        return;
                    }
                    ResponseBody responseBody = response.body();
                    byte[] bytes = responseBody == null ? new byte[0] : responseBody.bytes();
                    callback.onSuccess(call, bytes);
                } catch (Exception e) {
                    callback.onFailure(call, e);
                }
            }
        });
    }

    /**
     * Builds a call that expects a JSON response.
     */
    public CallJson generatePdfCall(GeneratePdfJsonParams params) {
        return new PdfGateJsonCall(buildGeneratePdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallBytes generatePdfCall(GeneratePdfBytesParams params) {
        return new PdfGateBytesCall(buildGeneratePdfCall(params));
    }

    private Call buildGeneratePdfCall(GeneratePdfParams params) {
        validateGeneratePdfParams(params);
        String jsonBody = PdfGateJson.gson().toJson(params);
        RequestBody body = RequestBody.create(jsonBody, JSON_MEDIA_TYPE);
        String requestUrl = urlBuilder.generatePdf();
        Request request = new Request.Builder()
                .url(requestUrl)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        OkHttpClient client = httpClient.newBuilder()
                .callTimeout(config.getGeneratePdfTimeout())
                .readTimeout(config.getGeneratePdfTimeout())
                .build();

        return client.newCall(request);
    }

    private void validateGeneratePdfParams(GeneratePdfParams params) {
        if (params == null) {
            throw new IllegalArgumentException("params must be provided.");
        }
        String html = params.getHtml();
        String url = params.getUrl();
        if ((html == null || html.isBlank()) && (url == null || url.isBlank())) {
            throw new IllegalArgumentException(
                    "Either the 'html' or 'url' parameters must be provided to generate a PDF."
            );
        }
    }

}
