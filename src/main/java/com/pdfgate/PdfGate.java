package com.pdfgate;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import okhttp3.OkHttpClient;

/**
 * Client for the PDFGate HTTP API.
 *
 * <p>Provides synchronous and asynchronous helpers for each endpoint, plus call builders
 * for custom execution. Non-2xx responses are surfaced as {@link PdfGateException} with
 * status code and response body details.
 */
public final class PdfGate {
  /**
   * Builds OkHttp calls for PdfGate API requests.
   */
  private final PdfGateCallBuilder callBuilder;
  /**
   * Enqueues calls and adapts responses for async usage.
   */
  private final PdfGateEnqueuer enqueuer;

  /**
   * Creates a client with the default configuration.
   *
   * @param apiKey API key used for authentication.
   */
  public PdfGate(String apiKey) {
    this(apiKey, PdfGateConfig.defaultConfig());
  }

  /**
   * Creates a client with a custom configuration.
   *
   * @param apiKey API key used for authentication.
   * @param config configuration for base URL, timeouts, and headers.
   */
  public PdfGate(String apiKey, PdfGateConfig config) {
    if (apiKey == null || apiKey.isBlank()) {
      throw new IllegalArgumentException("apiKey must be provided.");
    }
    if (config == null) {
      throw new IllegalArgumentException("config must be provided.");
    }
    UrlBuilder urlBuilder = new UrlBuilder(apiKey, config);
    OkHttpClient httpClient = new OkHttpClient.Builder()
        .connectTimeout(config.getDefaultTimeout())
        .build();
    this.callBuilder = new PdfGateCallBuilder(apiKey, httpClient, config, urlBuilder);
    this.enqueuer = new PdfGateEnqueuer();
  }

  /**
   * Generates a PDF from HTML or a URL and returns raw bytes.
   *
   * <p>The API expects either {@code html} or {@code url} to be set in {@code params}.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public byte[] generatePdf(GeneratePdfFileParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(generatePdfCall(params));
  }

  /**
   * Generates a PDF from HTML or a URL and returns a JSON document response.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument generatePdf(GeneratePdfJsonParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(generatePdfCall(params));
  }

  /**
   * Generates a PDF from HTML or a URL asynchronously and returns raw bytes.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<byte[]> generatePdfAsync(GeneratePdfFileParams params) {
    return enqueuer.enqueueAsFuture(generatePdfCall(params));
  }

  /**
   * Generates a PDF from HTML or a URL asynchronously and returns a JSON document response.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<PdfGateDocument> generatePdfAsync(GeneratePdfJsonParams params) {
    return enqueuer.enqueueAsFuture(generatePdfCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   */
  public CallJson generatePdfCall(GeneratePdfJsonParams params) {
    return new PdfGateJsonCall(callBuilder.buildGeneratePdfCall(params));
  }

  /**
   * Builds a call that expects a raw bytes response.
   */
  public CallFile generatePdfCall(GeneratePdfFileParams params) {
    return new PdfGateFileCall(callBuilder.buildGeneratePdfCall(params));
  }

  /**
   * Flattens a PDF and returns raw bytes.
   *
   * <p>Flattening converts interactive fields into a static PDF.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public byte[] flattenPdf(FlattenPdfFileParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(flattenPdfCall(params));
  }

  /**
   * Flattens a PDF and returns a JSON document response.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument flattenPdf(FlattenPdfJsonParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(flattenPdfCall(params));
  }

  /**
   * Flattens a PDF asynchronously and returns raw bytes.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<byte[]> flattenPdfAsync(FlattenPdfFileParams params) {
    return enqueuer.enqueueAsFuture(flattenPdfCall(params));
  }

  /**
   * Flattens a PDF asynchronously and returns a JSON document response.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<PdfGateDocument> flattenPdfAsync(FlattenPdfJsonParams params) {
    return enqueuer.enqueueAsFuture(flattenPdfCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   */
  public CallJson flattenPdfCall(FlattenPdfJsonParams params) {
    return new PdfGateJsonCall(callBuilder.buildFlattenPdfCall(params));
  }

  /**
   * Builds a call that expects a raw bytes response.
   */
  public CallFile flattenPdfCall(FlattenPdfFileParams params) {
    return new PdfGateFileCall(callBuilder.buildFlattenPdfCall(params));
  }

  /**
   * Protects a PDF and returns raw bytes.
   *
   * <p>Use {@link ProtectPdfParams} to set encryption options and permission restrictions.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public byte[] protectPdf(ProtectPdfFileParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(protectPdfCall(params));
  }

  /**
   * Protects a PDF and returns a JSON document response.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument protectPdf(ProtectPdfJsonParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(protectPdfCall(params));
  }

  /**
   * Protects a PDF asynchronously and returns raw bytes.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<byte[]> protectPdfAsync(ProtectPdfFileParams params) {
    return enqueuer.enqueueAsFuture(protectPdfCall(params));
  }

  /**
   * Protects a PDF asynchronously and returns a JSON document response.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<PdfGateDocument> protectPdfAsync(ProtectPdfJsonParams params) {
    return enqueuer.enqueueAsFuture(protectPdfCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   */
  public CallJson protectPdfCall(ProtectPdfJsonParams params) {
    return new PdfGateJsonCall(callBuilder.buildProtectPdfCall(params));
  }

  /**
   * Builds a call that expects a raw bytes response.
   */
  public CallFile protectPdfCall(ProtectPdfFileParams params) {
    return new PdfGateFileCall(callBuilder.buildProtectPdfCall(params));
  }

  /**
   * Compresses a PDF and returns raw bytes.
   *
   * <p>Set {@code linearize} in {@link CompressPdfParams} to enable Fast Web View output.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public byte[] compressPdf(CompressPdfFileParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(compressPdfCall(params));
  }

  /**
   * Compresses a PDF and returns a JSON document response.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument compressPdf(CompressPdfJsonParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(compressPdfCall(params));
  }

  /**
   * Compresses a PDF asynchronously and returns raw bytes.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<byte[]> compressPdfAsync(CompressPdfFileParams params) {
    return enqueuer.enqueueAsFuture(compressPdfCall(params));
  }

  /**
   * Compresses a PDF asynchronously and returns a JSON document response.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<PdfGateDocument> compressPdfAsync(CompressPdfJsonParams params) {
    return enqueuer.enqueueAsFuture(compressPdfCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   */
  public CallJson compressPdfCall(CompressPdfJsonParams params) {
    return new PdfGateJsonCall(callBuilder.buildCompressPdfCall(params));
  }

  /**
   * Builds a call that expects a raw bytes response.
   */
  public CallFile compressPdfCall(CompressPdfFileParams params) {
    return new PdfGateFileCall(callBuilder.buildCompressPdfCall(params));
  }

  /**
   * Applies a watermark to a PDF and returns raw bytes.
   *
   * <p>Set {@code type} to {@code text} or {@code image}. Text watermarks require {@code text};
   * image watermarks require a {@code watermark} file. Optional controls include font, size,
   * opacity, position, and rotation.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public byte[] watermarkPdf(WatermarkPdfFileParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(watermarkPdfCall(params));
  }

  /**
   * Applies a watermark to a PDF and returns a JSON document response.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument watermarkPdf(WatermarkPdfJsonParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(watermarkPdfCall(params));
  }

  /**
   * Applies a watermark to a PDF asynchronously and returns raw bytes.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<byte[]> watermarkPdfAsync(WatermarkPdfFileParams params) {
    return enqueuer.enqueueAsFuture(watermarkPdfCall(params));
  }

  /**
   * Applies a watermark to a PDF asynchronously and returns a JSON document response.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<PdfGateDocument> watermarkPdfAsync(WatermarkPdfJsonParams params) {
    return enqueuer.enqueueAsFuture(watermarkPdfCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   */
  public CallJson watermarkPdfCall(WatermarkPdfJsonParams params) {
    return new PdfGateJsonCall(callBuilder.buildWatermarkPdfCall(params));
  }

  /**
   * Builds a call that expects a raw bytes response.
   */
  public CallFile watermarkPdfCall(WatermarkPdfFileParams params) {
    return new PdfGateFileCall(callBuilder.buildWatermarkPdfCall(params));
  }

  /**
   * Extracts PDF form field data and returns the JSON response.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public JsonObject extractPdfFormData(ExtractPdfFormDataParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(extractPdfFormDataCall(params));
  }

  /**
   * Extracts PDF form field data asynchronously and returns the JSON response.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<JsonObject> extractPdfFormDataAsync(ExtractPdfFormDataParams params) {
    return enqueuer.enqueueAsFuture(extractPdfFormDataCall(params));
  }

  /**
   * Builds a call that expects a JSON response containing form field data.
   */
  public CallJsonObject extractPdfFormDataCall(ExtractPdfFormDataParams params) {
    return new PdfGateJsonObjectCall(callBuilder.buildExtractPdfFormDataCall(params));
  }

  /**
   * Retrieves document metadata (and optionally a fresh pre-signed URL).
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument getDocument(GetDocumentParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(getDocumentCall(params));
  }

  /**
   * Retrieves document metadata asynchronously.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<PdfGateDocument> getDocumentAsync(GetDocumentParams params) {
    return enqueuer.enqueueAsFuture(getDocumentCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   */
  public CallJson getDocumentCall(GetDocumentParams params) {
    return new PdfGateJsonCall(callBuilder.buildGetDocumentCall(params));
  }

  /**
   * Retrieves a stored document file.
   *
   * <p>Accessing stored files requires enabling "Save files" in the PDFGate Dashboard settings.
   *
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public byte[] getFile(GetFileParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(getFileCall(params));
  }

  /**
   * Retrieves a stored document file asynchronously.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   */
  public CompletableFuture<byte[]> getFileAsync(GetFileParams params) {
    return enqueuer.enqueueAsFuture(getFileCall(params));
  }

  /**
   * Builds a call that expects a raw bytes response.
   */
  public CallFile getFileCall(GetFileParams params) {
    return new PdfGateFileCall(callBuilder.buildGetFileCall(params));
  }

  /**
   * Enqueues a JSON response call and maps the response to {@link PdfGateDocument}.
   */
  public void enqueue(CallJson call, PdfGateCallback<PdfGateDocument> callback) {
    enqueuer.enqueue(call, callback);
  }

  /**
   * Enqueues a bytes response call and returns the raw response bytes.
   */
  public void enqueue(CallFile call, PdfGateCallback<byte[]> callback) {
    enqueuer.enqueue(call, callback);
  }

  /**
   * Enqueues a JSON response call and maps the response to {@link JsonObject}.
   */
  public void enqueue(CallJsonObject call, PdfGateCallback<JsonObject> callback) {
    enqueuer.enqueue(call, callback);
  }

}
