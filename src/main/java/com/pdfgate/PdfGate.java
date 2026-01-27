package com.pdfgate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import java.io.IOException;
import java.time.Instant;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

public final class PdfGate {
    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (json, type, ctx) -> {
                if (json == null || json.isJsonNull()) {
                    return null;
                }
                String value = json.getAsString();
                if (value == null || value.isBlank()) {
                    return null;
                }
                return Instant.parse(value);
            })
            .create();

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

    public PdfGateDocument generatePdf(GeneratePdfParams params)
            throws IOException {
        try {
            try (Response response = generatePdfCall(params).execute()) {
                if (!response.isSuccessful()) {
                    throw PdfGateException.fromResponse(response);
                }
                ResponseBody responseBody = response.body();
                if (params.isJsonResponse()) {
                    String responseJson = responseBody == null ? "" : responseBody.string();
                    return GSON.fromJson(responseJson, PdfGateDocument.class);
                }

                return new PdfGateDocument();
            }
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    public void enqueue(Call call, PDFGateCallback<PdfGateDocument> callback) {
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
                    String responseJson = responseBody == null ? "" : responseBody.string();
                    PdfGateDocument document = GSON.fromJson(responseJson, PdfGateDocument.class);
                    callback.onSuccess(call, document);
                } catch (Exception e) {
                    callback.onFailure(call, e);
                }
            }
        });
    }

    public Call generatePdfCall(GeneratePdfParams params) {
        validateGeneratePdfParams(params);
        String jsonBody = GSON.toJson(params);
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
