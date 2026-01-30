package com.pdfgate;

import java.io.IOException;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;
import okhttp3.*;

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
        try (Response response = generatePdfCall(params).execute()) {
            return PdfGateResponseParser.parseBytes(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Generates a PDF and returns the document metadata from a JSON response.
     */
    public PdfGateDocument generatePdf(GeneratePdfJsonParams params)
            throws IOException {
        try (Response response = generatePdfCall(params).execute()) {
            return PdfGateResponseParser.parseJson(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Generates a PDF asynchronously and returns raw bytes.
     */
    public CompletableFuture<byte[]> generatePdfAsync(GeneratePdfBytesParams params) {
        return enqueueAsFuture(generatePdfCall(params));
    }

    /**
     * Generates a PDF asynchronously and returns the document metadata from a JSON response.
     */
    public CompletableFuture<PdfGateDocument> generatePdfAsync(GeneratePdfJsonParams params) {
        return enqueueAsFuture(generatePdfCall(params));
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

    /**
     * Flattens a PDF provided as a file and returns raw bytes.
     */
    public byte[] flattenPdf(FlattenPdfBytesParams params)
            throws IOException {
        try (Response response = flattenPdfCall(params).execute()) {
            return PdfGateResponseParser.parseBytes(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Flattens a PDF provided as a file and returns the document metadata from a JSON response.
     */
    public PdfGateDocument flattenPdf(FlattenPdfJsonParams params)
            throws IOException {
        try (Response response = flattenPdfCall(params).execute()) {
            return PdfGateResponseParser.parseJson(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Flattens a PDF asynchronously and returns raw bytes.
     */
    public CompletableFuture<byte[]> flattenPdfAsync(FlattenPdfBytesParams params) {
        return enqueueAsFuture(flattenPdfCall(params));
    }

    /**
     * Flattens a PDF provided as a file asynchronously and returns the document metadata from a JSON response.
     */
    public CompletableFuture<PdfGateDocument> flattenPdfAsync(FlattenPdfJsonParams params) {
        return enqueueAsFuture(flattenPdfCall(params));
    }

    /**
     * Builds a call that expects a JSON response.
     */
    public CallJson flattenPdfCall(FlattenPdfJsonParams params) {
        return new PdfGateJsonCall(buildFlattenPdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallBytes flattenPdfCall(FlattenPdfBytesParams params) {
        return new PdfGateBytesCall(buildFlattenPdfCall(params));
    }

    /**
     * Protects a PDF and returns raw bytes.
     */
    public byte[] protectPdf(ProtectPdfBytesParams params)
            throws IOException {
        try (Response response = protectPdfCall(params).execute()) {
            return PdfGateResponseParser.parseBytes(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Protects a PDF and returns the document metadata from a JSON response.
     */
    public PdfGateDocument protectPdf(ProtectPdfJsonParams params)
            throws IOException {
        try (Response response = protectPdfCall(params).execute()) {
            return PdfGateResponseParser.parseJson(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Protects a PDF asynchronously and returns raw bytes.
     */
    public CompletableFuture<byte[]> protectPdfAsync(ProtectPdfBytesParams params) {
        return enqueueAsFuture(protectPdfCall(params));
    }

    /**
     * Protects a PDF asynchronously and returns the document metadata from a JSON response.
     */
    public CompletableFuture<PdfGateDocument> protectPdfAsync(ProtectPdfJsonParams params) {
        return enqueueAsFuture(protectPdfCall(params));
    }

    /**
     * Builds a call that expects a JSON response.
     */
    public CallJson protectPdfCall(ProtectPdfJsonParams params) {
        return new PdfGateJsonCall(buildProtectPdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallBytes protectPdfCall(ProtectPdfBytesParams params) {
        return new PdfGateBytesCall(buildProtectPdfCall(params));
    }

    /**
     * Applies a watermark to a PDF and returns raw bytes.
     */
    public byte[] watermarkPdf(WatermarkPdfBytesParams params)
            throws IOException {
        try (Response response = watermarkPdfCall(params).execute()) {
            return PdfGateResponseParser.parseBytes(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Applies a watermark to a PDF and returns the document metadata from a JSON response.
     */
    public PdfGateDocument watermarkPdf(WatermarkPdfJsonParams params)
            throws IOException {
        try (Response response = watermarkPdfCall(params).execute()) {
            return PdfGateResponseParser.parseJson(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Applies a watermark to a PDF asynchronously and returns raw bytes.
     */
    public CompletableFuture<byte[]> watermarkPdfAsync(WatermarkPdfBytesParams params) {
        return enqueueAsFuture(watermarkPdfCall(params));
    }

    /**
     * Applies a watermark to a PDF asynchronously and returns the document metadata from a JSON response.
     */
    public CompletableFuture<PdfGateDocument> watermarkPdfAsync(WatermarkPdfJsonParams params) {
        return enqueueAsFuture(watermarkPdfCall(params));
    }

    /**
     * Builds a call that expects a JSON response.
     */
    public CallJson watermarkPdfCall(WatermarkPdfJsonParams params) {
        return new PdfGateJsonCall(buildWatermarkPdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallBytes watermarkPdfCall(WatermarkPdfBytesParams params) {
        return new PdfGateBytesCall(buildWatermarkPdfCall(params));
    }

    /**
     * Extracts PDF form field data and returns the JSON response.
     */
    public JsonObject extractPdfFormData(ExtractPdfFormDataParams params)
            throws IOException {
        try (Response response = extractPdfFormDataCall(params).execute()) {
            return PdfGateResponseParser.parseJsonObject(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Extracts PDF form field data asynchronously and returns the JSON response.
     */
    public CompletableFuture<JsonObject> extractPdfFormDataAsync(ExtractPdfFormDataParams params) {
        return enqueueAsFuture(extractPdfFormDataCall(params));
    }

    /**
     * Builds a call that expects a JSON response containing form field data.
     */
    public CallJsonObject extractPdfFormDataCall(ExtractPdfFormDataParams params) {
        return new PdfGateJsonObjectCall(buildExtractPdfFormDataCall(params));
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

    private Call buildFlattenPdfCall(FlattenPdfParams params) {
        validateFlattenPdfParams(params);
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addFlattenPdfCommonFields(bodyBuilder, params.getJsonResponse(), params.getPreSignedUrlExpiresIn(), params.getMetadata());

        FileParam file = params.getFile();
        if (file != null) {
            MediaType mediaType = resolveFileMediaType(file);
            bodyBuilder.addFormDataPart(
                    "file",
                    file.getName(),
                    RequestBody.create(file.getData(), mediaType)
            );
        } else {
            String documentId = params.getDocumentId();
            if (documentId != null && !documentId.isBlank()) {
                bodyBuilder.addFormDataPart("documentId", documentId);
            }
        }

        Request request = new Request.Builder()
                .url(urlBuilder.flattenPdf())
                .header("Authorization", "Bearer " + apiKey)
                .post(bodyBuilder.build())
                .build();

        OkHttpClient client = httpClient.newBuilder()
                .callTimeout(config.getFlattenPdfTimeout())
                .readTimeout(config.getFlattenPdfTimeout())
                .build();

        return client.newCall(request);
    }

    private Call buildWatermarkPdfCall(WatermarkPdfParams params) {
        validateWatermarkPdfParams(params);
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addWatermarkPdfCommonFields(
                bodyBuilder,
                params.getType(),
                params.getText(),
                params.getFont(),
                params.getFontSize(),
                params.getFontColor(),
                params.getOpacity(),
                params.getXPosition(),
                params.getYPosition(),
                params.getImageWidth(),
                params.getImageHeight(),
                params.getRotate(),
                params.getJsonResponse(),
                params.getPreSignedUrlExpiresIn(),
                params.getMetadata()
        );

        FileParam file = params.getFile();
        if (file != null) {
            MediaType mediaType = resolveFileMediaType(file);
            bodyBuilder.addFormDataPart(
                    "file",
                    file.getName(),
                    RequestBody.create(file.getData(), mediaType)
            );
        } else {
            String documentId = params.getDocumentId();
            if (documentId != null && !documentId.isBlank()) {
                bodyBuilder.addFormDataPart("documentId", documentId);
            }
        }

        if (params.getType() == WatermarkPdfParams.WatermarkType.IMAGE) {
            FileParam watermark = params.getWatermark();
            if (watermark != null) {
                MediaType mediaType = resolveFileMediaType(watermark);
                bodyBuilder.addFormDataPart(
                        "watermark",
                        watermark.getName(),
                        RequestBody.create(watermark.getData(), mediaType)
                );
            }
        }

        Request request = new Request.Builder()
                .url(urlBuilder.watermarkPdf())
                .header("Authorization", "Bearer " + apiKey)
                .post(bodyBuilder.build())
                .build();

        OkHttpClient client = httpClient.newBuilder()
                .callTimeout(config.getDefaultTimeout())
                .readTimeout(config.getDefaultTimeout())
                .build();

        return client.newCall(request);
    }

    private Call buildProtectPdfCall(ProtectPdfParams params) {
        validateProtectPdfParams(params);
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addProtectPdfCommonFields(
                bodyBuilder,
                params.getAlgorithm(),
                params.getUserPassword(),
                params.getOwnerPassword(),
                params.getDisablePrint(),
                params.getDisableCopy(),
                params.getDisableEditing(),
                params.getEncryptMetadata(),
                params.getJsonResponse(),
                params.getPreSignedUrlExpiresIn(),
                params.getMetadata()
        );

        FileParam file = params.getFile();
        if (file != null) {
            MediaType mediaType = resolveFileMediaType(file);
            bodyBuilder.addFormDataPart(
                    "file",
                    file.getName(),
                    RequestBody.create(file.getData(), mediaType)
            );
        } else {
            String documentId = params.getDocumentId();
            if (documentId != null && !documentId.isBlank()) {
                bodyBuilder.addFormDataPart("documentId", documentId);
            }
        }

        Request request = new Request.Builder()
                .url(urlBuilder.protectPdf())
                .header("Authorization", "Bearer " + apiKey)
                .post(bodyBuilder.build())
                .build();

        OkHttpClient client = httpClient.newBuilder()
                .callTimeout(config.getProtectPdfTimeout())
                .readTimeout(config.getProtectPdfTimeout())
                .build();

        return client.newCall(request);
    }

    private Call buildExtractPdfFormDataCall(ExtractPdfFormDataParams params) {
        validateExtractPdfFormDataParams(params);
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        FileParam file = params.getFile();
        if (file != null) {
            MediaType mediaType = resolveFileMediaType(file);
            bodyBuilder.addFormDataPart(
                    "file",
                    file.getName(),
                    RequestBody.create(file.getData(), mediaType)
            );
        } else {
            String documentId = params.getDocumentId();
            if (documentId != null && !documentId.isBlank()) {
                bodyBuilder.addFormDataPart("documentId", documentId);
            }
        }

        Request request = new Request.Builder()
                .url(urlBuilder.extractPdfFormData())
                .header("Authorization", "Bearer " + apiKey)
                .post(bodyBuilder.build())
                .build();

        OkHttpClient client = httpClient.newBuilder()
                .callTimeout(config.getDefaultTimeout())
                .readTimeout(config.getDefaultTimeout())
                .build();

        return client.newCall(request);
    }

    /**
     * Enqueues a JSON response call and maps the response to {@link PdfGateDocument}.
     */
    public void enqueue(CallJson call, PDFGateCallback<PdfGateDocument> callback) {
        call.enqueue(new PdfGateJsonResponseParserCallback(callback));
    }

    /**
     * Enqueues a bytes response call and returns the raw response bytes.
     */
    public void enqueue(CallBytes call, PDFGateCallback<byte[]> callback) {
        call.enqueue(new PdfGateBytesResponseParserCallback(callback));
    }

    /**
     * Enqueues a JSON response call and maps the response to {@link JsonObject}.
     */
    public void enqueue(CallJsonObject call, PDFGateCallback<JsonObject> callback) {
        call.enqueue(new PdfGateJsonObjectResponseParserCallback(callback));
    }

    private CompletableFuture<PdfGateDocument> enqueueAsFuture(CallJson call) {
        CompletableFuture<PdfGateDocument> future = new CompletableFuture<>();
        enqueue(call, new PDFGateCallback<PdfGateDocument>() {
            @Override
            public void onSuccess(Call call, PdfGateDocument value) {
                future.complete(value);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                future.completeExceptionally(wrapAsyncThrowable(t));
            }
        });
        future.whenComplete((r, t) -> {
            if (future.isCancelled()) {
                call.cancel();
            }
        });
        return future;
    }

    private CompletableFuture<JsonObject> enqueueAsFuture(CallJsonObject call) {
        CompletableFuture<JsonObject> future = new CompletableFuture<>();
        enqueue(call, new PDFGateCallback<JsonObject>() {
            @Override
            public void onSuccess(Call call, JsonObject value) {
                future.complete(value);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                future.completeExceptionally(wrapAsyncThrowable(t));
            }
        });
        future.whenComplete((r, t) -> {
            if (future.isCancelled()) {
                call.cancel();
            }
        });
        return future;
    }

    private CompletableFuture<byte[]> enqueueAsFuture(CallBytes call) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        enqueue(call, new PDFGateCallback<byte[]>() {
            @Override
            public void onSuccess(Call call, byte[] value) {
                future.complete(value);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                future.completeExceptionally(wrapAsyncThrowable(t));
            }
        });
        future.whenComplete((r, t) -> {
            if (future.isCancelled()) {
                call.cancel();
            }
        });
        return future;
    }

    private Throwable wrapAsyncThrowable(Throwable t) {
        if (t instanceof PdfGateException) {
            return t;
        }
        if (t instanceof IOException) {
            return PdfGateException.fromException((IOException) t);
        }
        return t;
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

    private void addFlattenPdfCommonFields(
            MultipartBody.Builder bodyBuilder,
            Boolean jsonResponse,
            Long preSignedUrlExpiresIn,
            Object metadata
    ) {
        if (jsonResponse != null) {
            bodyBuilder.addFormDataPart("jsonResponse", jsonResponse.toString());
        }
        if (preSignedUrlExpiresIn != null) {
            bodyBuilder.addFormDataPart("preSignedUrlExpiresIn", preSignedUrlExpiresIn.toString());
        }
        if (metadata != null) {
            String metadataValue = metadata instanceof String
                    ? (String) metadata
                    : PdfGateJson.gson().toJson(metadata);
            bodyBuilder.addFormDataPart("metadata", metadataValue);
        }
    }

    private void addWatermarkPdfCommonFields(
            MultipartBody.Builder bodyBuilder,
            WatermarkPdfParams.WatermarkType type,
            String text,
            String font,
            Integer fontSize,
            String fontColor,
            Double opacity,
            Integer xPosition,
            Integer yPosition,
            Integer imageWidth,
            Integer imageHeight,
            Double rotate,
            Boolean jsonResponse,
            Long preSignedUrlExpiresIn,
            Object metadata
    ) {
        if (type != null) {
            bodyBuilder.addFormDataPart("type", type.toString());
        }
        if (text != null) {
            bodyBuilder.addFormDataPart("text", text);
        }
        if (font != null) {
            bodyBuilder.addFormDataPart("font", font);
        }
        if (fontSize != null) {
            bodyBuilder.addFormDataPart("fontSize", fontSize.toString());
        }
        if (fontColor != null) {
            bodyBuilder.addFormDataPart("fontColor", fontColor);
        }
        if (opacity != null) {
            bodyBuilder.addFormDataPart("opacity", opacity.toString());
        }
        if (xPosition != null) {
            bodyBuilder.addFormDataPart("xPosition", xPosition.toString());
        }
        if (yPosition != null) {
            bodyBuilder.addFormDataPart("yPosition", yPosition.toString());
        }
        if (imageWidth != null) {
            bodyBuilder.addFormDataPart("imageWidth", imageWidth.toString());
        }
        if (imageHeight != null) {
            bodyBuilder.addFormDataPart("imageHeight", imageHeight.toString());
        }
        if (rotate != null) {
            bodyBuilder.addFormDataPart("rotate", rotate.toString());
        }
        if (jsonResponse != null) {
            bodyBuilder.addFormDataPart("jsonResponse", jsonResponse.toString());
        }
        if (preSignedUrlExpiresIn != null) {
            bodyBuilder.addFormDataPart("preSignedUrlExpiresIn", preSignedUrlExpiresIn.toString());
        }
        if (metadata != null) {
            String metadataValue = metadata instanceof String
                    ? (String) metadata
                    : PdfGateJson.gson().toJson(metadata);
            bodyBuilder.addFormDataPart("metadata", metadataValue);
        }
    }

    private void addProtectPdfCommonFields(
            MultipartBody.Builder bodyBuilder,
            ProtectPdfParams.EncryptionAlgorithm algorithm,
            String userPassword,
            String ownerPassword,
            Boolean disablePrint,
            Boolean disableCopy,
            Boolean disableEditing,
            Boolean encryptMetadata,
            Boolean jsonResponse,
            Long preSignedUrlExpiresIn,
            Object metadata
    ) {
        if (algorithm != null) {
            bodyBuilder.addFormDataPart("algorithm", algorithm.toString());
        }
        if (userPassword != null) {
            bodyBuilder.addFormDataPart("userPassword", userPassword);
        }
        if (ownerPassword != null) {
            bodyBuilder.addFormDataPart("ownerPassword", ownerPassword);
        }
        if (disablePrint != null) {
            bodyBuilder.addFormDataPart("disablePrint", disablePrint.toString());
        }
        if (disableCopy != null) {
            bodyBuilder.addFormDataPart("disableCopy", disableCopy.toString());
        }
        if (disableEditing != null) {
            bodyBuilder.addFormDataPart("disableEditing", disableEditing.toString());
        }
        if (encryptMetadata != null) {
            bodyBuilder.addFormDataPart("encryptMetadata", encryptMetadata.toString());
        }
        if (jsonResponse != null) {
            bodyBuilder.addFormDataPart("jsonResponse", jsonResponse.toString());
        }
        if (preSignedUrlExpiresIn != null) {
            bodyBuilder.addFormDataPart("preSignedUrlExpiresIn", preSignedUrlExpiresIn.toString());
        }
        if (metadata != null) {
            String metadataValue = metadata instanceof String
                    ? (String) metadata
                    : PdfGateJson.gson().toJson(metadata);
            bodyBuilder.addFormDataPart("metadata", metadataValue);
        }
    }

    private void validateFlattenPdfParams(FlattenPdfParams params) {
        if (params == null) {
            throw new IllegalArgumentException("params must be provided.");
        }
        FileParam file = params.getFile();
        String documentId = params.getDocumentId();
        if (file == null && (documentId == null || documentId.isBlank())) {
            throw new IllegalArgumentException("Either file or documentId must be provided.");
        }
        if (file != null) {
            if (file.getName() == null || file.getName().isBlank()) {
                throw new IllegalArgumentException("file name must be provided.");
            }
            if (file.getData() == null || file.getData().length == 0) {
                throw new IllegalArgumentException("file data must be provided.");
            }
        }
    }

    private void validateWatermarkPdfParams(WatermarkPdfParams params) {
        if (params == null) {
            throw new IllegalArgumentException("params must be provided.");
        }
        if (params.getType() == null) {
            throw new IllegalArgumentException("type must be provided.");
        }
        FileParam file = params.getFile();
        String documentId = params.getDocumentId();
        if (file == null && (documentId == null || documentId.isBlank())) {
            throw new IllegalArgumentException("Either file or documentId must be provided.");
        }
        if (file != null) {
            if (file.getName() == null || file.getName().isBlank()) {
                throw new IllegalArgumentException("file name must be provided.");
            }
            if (file.getData() == null || file.getData().length == 0) {
                throw new IllegalArgumentException("file data must be provided.");
            }
        }
        if (params.getType() == WatermarkPdfParams.WatermarkType.TEXT) {
            if (params.getText() == null || params.getText().isBlank()) {
                throw new IllegalArgumentException("text must be provided when type is text.");
            }
        }
        if (params.getType() == WatermarkPdfParams.WatermarkType.IMAGE) {
            FileParam watermark = params.getWatermark();
            if (watermark == null) {
                throw new IllegalArgumentException("watermark file must be provided when type is image.");
            }
            if (watermark.getName() == null || watermark.getName().isBlank()) {
                throw new IllegalArgumentException("watermark file name must be provided.");
            }
            if (watermark.getData() == null || watermark.getData().length == 0) {
                throw new IllegalArgumentException("watermark file data must be provided.");
            }
        }
    }

    private void validateProtectPdfParams(ProtectPdfParams params) {
        if (params == null) {
            throw new IllegalArgumentException("params must be provided.");
        }
        FileParam file = params.getFile();
        String documentId = params.getDocumentId();
        if (file == null && (documentId == null || documentId.isBlank())) {
            throw new IllegalArgumentException("Either file or documentId must be provided.");
        }
        if (file != null) {
            if (file.getName() == null || file.getName().isBlank()) {
                throw new IllegalArgumentException("file name must be provided.");
            }
            if (file.getData() == null || file.getData().length == 0) {
                throw new IllegalArgumentException("file data must be provided.");
            }
        }
    }

    private void validateExtractPdfFormDataParams(ExtractPdfFormDataParams params) {
        if (params == null) {
            throw new IllegalArgumentException("params must be provided.");
        }
        FileParam file = params.getFile();
        String documentId = params.getDocumentId();
        if (file == null && (documentId == null || documentId.isBlank())) {
            throw new IllegalArgumentException("Either file or documentId must be provided.");
        }
        if (file != null) {
            if (file.getName() == null || file.getName().isBlank()) {
                throw new IllegalArgumentException("file name must be provided.");
            }
            if (file.getData() == null || file.getData().length == 0) {
                throw new IllegalArgumentException("file data must be provided.");
            }
        }
    }

    private MediaType resolveFileMediaType(FileParam file) {
        String mimeType = file.getType();
        if (mimeType == null || mimeType.isBlank()) {
            mimeType = URLConnection.guessContentTypeFromName(file.getName());
        }
        if (mimeType == null || mimeType.isBlank()) {
            mimeType = "application/octet-stream";
        }
        return MediaType.get(mimeType);
    }

}
