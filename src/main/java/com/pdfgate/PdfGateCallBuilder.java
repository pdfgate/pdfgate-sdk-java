package com.pdfgate;

import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Builds OkHttp calls for PdfGate API requests.
 */
final class PdfGateCallBuilder {
    /** JSON media type for body encoding. */
    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    /** API key used to authenticate requests. */
    private final String apiKey;
    /** Base OkHttp client used to create per-endpoint clients. */
    private final OkHttpClient httpClient;
    /** SDK configuration for timeouts and domains. */
    private final PdfGateConfig config;
    /** URL builder for API endpoints. */
    private final UrlBuilder urlBuilder;

    /**
     * Creates a new call builder for PdfGate requests.
     */
    PdfGateCallBuilder(String apiKey, OkHttpClient httpClient, PdfGateConfig config, UrlBuilder urlBuilder) {
        this.apiKey = apiKey;
        this.httpClient = httpClient;
        this.config = config;
        this.urlBuilder = urlBuilder;
    }

    /**
     * Builds the call for generating a PDF.
     */
    Call buildGeneratePdfCall(GeneratePdfParams params) {
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

    /**
     * Builds the call for flattening a PDF.
     */
    Call buildFlattenPdfCall(FlattenPdfParams params) {
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

    /**
     * Builds the call for watermarking a PDF.
     */
    Call buildWatermarkPdfCall(WatermarkPdfParams params) {
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

    /**
     * Builds the call for protecting a PDF.
     */
    Call buildProtectPdfCall(ProtectPdfParams params) {
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

    /**
     * Builds the call for extracting form data from a PDF.
     */
    Call buildExtractPdfFormDataCall(ExtractPdfFormDataParams params) {
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
     * Validates generate PDF request parameters.
     */
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

    /**
     * Adds shared multipart fields for flatten PDF requests.
     */
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

    /**
     * Adds shared multipart fields for watermark PDF requests.
     */
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

    /**
     * Adds shared multipart fields for protect PDF requests.
     */
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

    /**
     * Validates flatten PDF request parameters.
     */
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

    /**
     * Validates watermark PDF request parameters.
     */
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

    /**
     * Validates protect PDF request parameters.
     */
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

    /**
     * Validates extract PDF form data request parameters.
     */
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

    /**
     * Resolves a file's media type for multipart uploads.
     */
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
