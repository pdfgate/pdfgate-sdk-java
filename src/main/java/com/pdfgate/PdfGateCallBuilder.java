package com.pdfgate;

import java.net.URLConnection;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Builds OkHttp calls for PdfGate API requests.
 */
final class PdfGateCallBuilder {
  /**
   * JSON media type for body encoding.
   */
  private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

  /**
   * API key used to authenticate requests.
   */
  private final String apiKey;
  /**
   * Base OkHttp client used to create per-endpoint clients.
   */
  private final OkHttpClient httpClient;
  /**
   * SDK configuration for timeouts and domains.
   */
  private final PdfGateConfig config;
  /**
   * URL builder for API endpoints.
   */
  private final UrlBuilder urlBuilder;

  /**
   * Creates a new call builder for PdfGate requests.
   */
  PdfGateCallBuilder(String apiKey, OkHttpClient httpClient, PdfGateConfig config,
                     UrlBuilder urlBuilder) {
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

    OkHttpClient client = clientWithTimeout(config.getGeneratePdfTimeout());

    return client.newCall(request);
  }

  /**
   * Builds the call for flattening a PDF.
   */
  Call buildFlattenPdfCall(FlattenPdfParams params) {
    validateFlattenPdfParams(params);
    MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
        .setType(MultipartBody.FORM);
    addFlattenPdfCommonFields(bodyBuilder, params.getPreSignedUrlExpiresIn(), params.getMetadata());

    String documentId = params.getDocumentId();
    if (!Strings.isBlank(documentId)) {
      bodyBuilder.addFormDataPart("documentId", documentId);
    }

    Request request = new Request.Builder()
        .url(urlBuilder.flattenPdf())
        .header("Authorization", "Bearer " + apiKey)
        .post(bodyBuilder.build())
        .build();

    OkHttpClient client = clientWithTimeout(config.getFlattenPdfTimeout());

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
        params.getPreSignedUrlExpiresIn(),
        params.getMetadata()
    );

    String documentId = params.getDocumentId();
    if (!Strings.isBlank(documentId)) {
      bodyBuilder.addFormDataPart("documentId", documentId);
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

    OkHttpClient client = clientWithTimeout(config.getDefaultTimeout());

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
        params.getPreSignedUrlExpiresIn(),
        params.getMetadata()
    );

    String documentId = params.getDocumentId();
    if (!Strings.isBlank(documentId)) {
      bodyBuilder.addFormDataPart("documentId", documentId);
    }

    Request request = new Request.Builder()
        .url(urlBuilder.protectPdf())
        .header("Authorization", "Bearer " + apiKey)
        .post(bodyBuilder.build())
        .build();

    OkHttpClient client = clientWithTimeout(config.getProtectPdfTimeout());

    return client.newCall(request);
  }

  /**
   * Builds the call for compressing a PDF.
   */
  Call buildCompressPdfCall(CompressPdfParams params) {
    validateCompressPdfParams(params);
    MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
        .setType(MultipartBody.FORM);
    addCompressPdfCommonFields(
        bodyBuilder,
        params.getLinearize(),
        params.getPreSignedUrlExpiresIn(),
        params.getMetadata()
    );

    String documentId = params.getDocumentId();
    if (!Strings.isBlank(documentId)) {
      bodyBuilder.addFormDataPart("documentId", documentId);
    }

    Request request = new Request.Builder()
        .url(urlBuilder.compressPdf())
        .header("Authorization", "Bearer " + apiKey)
        .post(bodyBuilder.build())
        .build();

    OkHttpClient client = clientWithTimeout(config.getCompressPdfTimeout());

    return client.newCall(request);
  }

  /**
   * Builds the call for creating an envelope.
   */
  Call buildCreateEnvelopeCall(CreateEnvelopeParams params) {
    validateCreateEnvelopeParams(params);
    String jsonBody = PdfGateJson.gson().toJson(params);
    RequestBody body = RequestBody.create(jsonBody, JSON_MEDIA_TYPE);
    Request request = authorizedRequestFor(urlBuilder.createEnvelope())
        .post(body)
        .build();

    OkHttpClient client = clientWithTimeout(config.getDefaultTimeout());

    return client.newCall(request);
  }

  /**
   * Builds the call for sending an envelope.
   */
  Call buildSendEnvelopeCall(SendEnvelopeParams params) {
    validateSendEnvelopeParams(params);
    RequestBody body = RequestBody.create(new byte[0], null);
    Request request = authorizedRequestFor(urlBuilder.sendEnvelope(params.getId()))
        .post(body)
        .build();

    OkHttpClient client = clientWithTimeout(config.getDefaultTimeout());

    return client.newCall(request);
  }

  /**
   * Builds the call for retrieving an envelope.
   */
  Call buildGetEnvelopeCall(GetEnvelopeParams params) {
    validateGetEnvelopeParams(params);
    Request request = authorizedRequestFor(urlBuilder.getEnvelope(params.getId()))
        .get()
        .build();

    OkHttpClient client = clientWithTimeout(config.getDefaultTimeout());

    return client.newCall(request);
  }

  /**
   * Builds the call for extracting form data from a PDF.
   */
  Call buildExtractPdfFormDataCall(ExtractPdfFormDataParams params) {
    validateExtractPdfFormDataParams(params);
    MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
        .setType(MultipartBody.FORM);
    bodyBuilder.addFormDataPart("jsonResponse", Boolean.TRUE.toString());

    String documentId = params.getDocumentId();
    if (!Strings.isBlank(documentId)) {
      bodyBuilder.addFormDataPart("documentId", documentId);
    }

    Request request = new Request.Builder()
        .url(urlBuilder.extractPdfFormData())
        .header("Authorization", "Bearer " + apiKey)
        .post(bodyBuilder.build())
        .build();

    OkHttpClient client = clientWithTimeout(config.getDefaultTimeout());

    return client.newCall(request);
  }

  /**
   * Builds the call for retrieving a document's metadata.
   */
  Call buildGetDocumentCall(GetDocumentParams params) {
    validateGetDocumentParams(params);
    String requestUrl = urlBuilder.getDocument(params.getDocumentId());
    HttpUrl url = HttpUrl.parse(requestUrl);
    if (url == null) {
      throw new IllegalArgumentException("Failed to build document URL.");
    }
    HttpUrl.Builder requestUrlBuilder = url.newBuilder();
    if (params.getPreSignedUrlExpiresIn() != null) {
      requestUrlBuilder.addQueryParameter("preSignedUrlExpiresIn",
          params.getPreSignedUrlExpiresIn().toString());
    }
    Request request = new Request.Builder()
        .url(requestUrlBuilder.build())
        .header("Authorization", "Bearer " + apiKey)
        .get()
        .build();

    OkHttpClient client = clientWithTimeout(config.getDefaultTimeout());

    return client.newCall(request);
  }

  /**
   * Returns a request builder with the Authorization header set.
   *
   * @param url full request URL.
   * @return a request builder with authentication configured.
   */
  private Request.Builder authorizedRequestFor(String url) {
    return new Request.Builder()
        .url(url)
        .header("Authorization", "Bearer " + apiKey);
  }

  /**
   * Builds the call for retrieving a document's file.
   */
  Call buildGetFileCall(GetFileParams params) {
    validateGetFileParams(params);
    String requestUrl = urlBuilder.getFile(params.getDocumentId());
    Request request = authorizedRequestFor(requestUrl)
        .get()
        .build();

    OkHttpClient client = clientWithTimeout(config.getDefaultTimeout());

    return client.newCall(request);
  }

  /**
   * Builds the call for uploading a PDF file or URL.
   */
  Call buildUploadFileCall(UploadFileParams params) {
    validateUploadFileParams(params);
    FileParam file = params.getFile();
    Request request;
    if (file != null) {
      MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
          .setType(MultipartBody.FORM);
      bodyBuilder.addFormDataPart("jsonResponse", Boolean.TRUE.toString());
      addCommonFields(bodyBuilder, params.getPreSignedUrlExpiresIn(),
          params.getMetadata());

      MediaType mediaType = resolveFileMediaType(file);
      bodyBuilder.addFormDataPart(
          "file",
          file.getName(),
          RequestBody.create(file.getData(), mediaType)
      );

      request = authorizedRequestFor(urlBuilder.uploadFile())
          .post(bodyBuilder.build())
          .build();
    } else {
      UploadFileJsonPayload payload = new UploadFileJsonPayload(
          params.getUrl(),
          params.getMetadata(),
          params.getPreSignedUrlExpiresIn()
      );
      String jsonBody = PdfGateJson.gson().toJson(payload);
      RequestBody body = RequestBody.create(jsonBody, JSON_MEDIA_TYPE);
      request = authorizedRequestFor(urlBuilder.uploadFile())
          .post(body)
          .build();
    }

    OkHttpClient client = clientWithTimeout(config.getDefaultTimeout());

    return client.newCall(request);
  }

  /**
   * Validates generate PDF request parameters.
   */
  private void validateGeneratePdfParams(GeneratePdfParams params) {
    if (params == null) {
      throw new IllegalArgumentException("params must be provided.");
    }
    if (!params.isJsonResponse()) {
      throw new IllegalArgumentException("jsonResponse must be true.");
    }
    String html = params.getHtml();
    String url = params.getUrl();
    if (Strings.isBlank(html) && Strings.isBlank(url)) {
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
      Long preSignedUrlExpiresIn,
      Object metadata
  ) {
    bodyBuilder.addFormDataPart("jsonResponse", Boolean.TRUE.toString());
    addCommonFields(bodyBuilder, preSignedUrlExpiresIn, metadata);
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
    bodyBuilder.addFormDataPart("jsonResponse", Boolean.TRUE.toString());
    addCommonFields(bodyBuilder, preSignedUrlExpiresIn, metadata);
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
    bodyBuilder.addFormDataPart("jsonResponse", Boolean.TRUE.toString());
    addCommonFields(bodyBuilder, preSignedUrlExpiresIn, metadata);
  }

  /**
   * Adds shared multipart fields for compress PDF requests.
   */
  private void addCompressPdfCommonFields(
      MultipartBody.Builder bodyBuilder,
      Boolean linearize,
      Long preSignedUrlExpiresIn,
      Object metadata
  ) {
    if (linearize != null) {
      bodyBuilder.addFormDataPart("linearize", linearize.toString());
    }
    bodyBuilder.addFormDataPart("jsonResponse", Boolean.TRUE.toString());
    addCommonFields(bodyBuilder, preSignedUrlExpiresIn, metadata);
  }

  /**
   * Validates flatten PDF request parameters.
   */
  private void validateFlattenPdfParams(FlattenPdfParams params) {
    if (params == null) {
      throw new IllegalArgumentException("params must be provided.");
    }
    if (!params.isJsonResponse()) {
      throw new IllegalArgumentException("jsonResponse must be true.");
    }
    String documentId = params.getDocumentId();
    if (Strings.isBlank(documentId)) {
      throw new IllegalArgumentException("documentId must be provided.");
    }
  }

  /**
   * Validates watermark PDF request parameters.
   */
  private void validateWatermarkPdfParams(WatermarkPdfParams params) {
    if (params == null) {
      throw new IllegalArgumentException("params must be provided.");
    }
    if (!params.isJsonResponse()) {
      throw new IllegalArgumentException("jsonResponse must be true.");
    }
    if (params.getType() == null) {
      throw new IllegalArgumentException("type must be provided.");
    }
    String documentId = params.getDocumentId();
    if (Strings.isBlank(documentId)) {
      throw new IllegalArgumentException("documentId must be provided.");
    }
    if (params.getType() == WatermarkPdfParams.WatermarkType.TEXT) {
      if (Strings.isBlank(params.getText())) {
        throw new IllegalArgumentException("text must be provided when type is text.");
      }
    }
    if (params.getType() == WatermarkPdfParams.WatermarkType.IMAGE) {
      FileParam watermark = params.getWatermark();
      if (watermark == null) {
        throw new IllegalArgumentException("watermark file must be provided when type is image.");
      }
      if (Strings.isBlank(watermark.getName())) {
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
    if (!params.isJsonResponse()) {
      throw new IllegalArgumentException("jsonResponse must be true.");
    }
    String documentId = params.getDocumentId();
    if (Strings.isBlank(documentId)) {
      throw new IllegalArgumentException("documentId must be provided.");
    }
  }

  /**
   * Validates compress PDF request parameters.
   */
  private void validateCompressPdfParams(CompressPdfParams params) {
    if (params == null) {
      throw new IllegalArgumentException("params must be provided.");
    }
    if (!params.isJsonResponse()) {
      throw new IllegalArgumentException("jsonResponse must be true.");
    }
    String documentId = params.getDocumentId();
    if (Strings.isBlank(documentId)) {
      throw new IllegalArgumentException("documentId must be provided.");
    }
  }

  /**
   * Validates extract PDF form data request parameters.
   */
  private void validateExtractPdfFormDataParams(ExtractPdfFormDataParams params) {
    if (params == null) {
      throw new IllegalArgumentException("params must be provided.");
    }
    String documentId = params.getDocumentId();
    if (Strings.isBlank(documentId)) {
      throw new IllegalArgumentException("documentId must be provided.");
    }
  }

  /**
   * Validates create envelope request parameters.
   */
  private void validateCreateEnvelopeParams(CreateEnvelopeParams params) {
    if (params == null) {
      throw new IllegalArgumentException("params must be provided.");
    }
  }

  /**
   * Validates send envelope request parameters.
   */
  private void validateSendEnvelopeParams(SendEnvelopeParams params) {
    if (params == null) {
      throw new IllegalArgumentException("params must be provided.");
    }
    if (Strings.isBlank(params.getId())) {
      throw new IllegalArgumentException("id must be provided.");
    }
  }

  /**
   * Validates get envelope request parameters.
   */
  private void validateGetEnvelopeParams(GetEnvelopeParams params) {
    if (params == null) {
      throw new IllegalArgumentException("params must be provided.");
    }
    if (Strings.isBlank(params.getId())) {
      throw new IllegalArgumentException("id must be provided.");
    }
  }

  /**
   * Validates get document request parameters.
   */
  private void validateGetDocumentParams(GetDocumentParams params) {
    if (params == null) {
      throw new IllegalArgumentException("params must be provided.");
    }
    String documentId = params.getDocumentId();
    if (Strings.isBlank(documentId)) {
      throw new IllegalArgumentException("documentId must be provided.");
    }
  }

  /**
   * Validates get file request parameters.
   */
  private void validateGetFileParams(GetFileParams params) {
    if (params == null) {
      throw new IllegalArgumentException("params must be provided.");
    }
    String documentId = params.getDocumentId();
    if (Strings.isBlank(documentId)) {
      throw new IllegalArgumentException("documentId must be provided.");
    }
  }

  /**
   * Validates upload file request parameters.
   */
  private void validateUploadFileParams(UploadFileParams params) {
    if (params == null) {
      throw new IllegalArgumentException("params must be provided.");
    }
    FileParam file = params.getFile();
    String url = params.getUrl();
    if (file == null && Strings.isBlank(url)) {
      throw new IllegalArgumentException("Either the 'file' or 'url' parameters must be provided.");
    }
    if (file != null) {
      if (Strings.isBlank(file.getName())) {
        throw new IllegalArgumentException("file name must be provided.");
      }
      if (file.getData() == null || file.getData().length == 0) {
        throw new IllegalArgumentException("file data must be provided.");
      }
    }
  }

  private void addCommonFields(MultipartBody.Builder bodyBuilder, Long preSignedUrlExpiresIn,
                               Object metadata) {
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

  private static final class UploadFileJsonPayload {
    private final String url;
    private final Object metadata;
    private final Long preSignedUrlExpiresIn;
    private final Boolean jsonResponse = true;

    private UploadFileJsonPayload(String url, Object metadata, Long preSignedUrlExpiresIn) {
      this.url = url;
      this.metadata = metadata;
      this.preSignedUrlExpiresIn = preSignedUrlExpiresIn;
    }
  }

  /**
   * Creates a client where the same timeout applies to every phase of the request lifecycle.
   *
   * <p>Connect timeout covers establishing the TCP/TLS connection, write timeout covers sending
   * the request body, read timeout covers receiving the response body, and call timeout caps the
   * total end-to-end request duration.
   */
  private OkHttpClient clientWithTimeout(java.time.Duration timeout) {
    return httpClient.newBuilder()
        .connectTimeout(timeout)
        .callTimeout(timeout)
        .readTimeout(timeout)
        .writeTimeout(timeout)
        .build();
  }

  /**
   * Resolves a file's media type for multipart uploads.
   */
  private MediaType resolveFileMediaType(FileParam file) {
    String mimeType = file.getType();
    if (Strings.isBlank(mimeType)) {
      mimeType = URLConnection.guessContentTypeFromName(file.getName());
    }
    if (Strings.isBlank(mimeType)) {
      mimeType = "application/octet-stream";
    }
    return MediaType.get(mimeType);
  }
}
