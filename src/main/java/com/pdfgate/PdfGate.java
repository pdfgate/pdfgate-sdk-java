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
   * @param config configuration for API domains and timeouts.
   */
  public PdfGate(String apiKey, PdfGateConfig config) {
    if (Strings.isBlank(apiKey)) {
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
   * Generates a PDF from HTML or a URL and returns a JSON document response.
   *
   * @param params parameters for the generate PDF request.
   * @return the generated document metadata.
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument generatePdf(GeneratePdfParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(generatePdfCall(params));
  }

  /**
   * Generates a PDF from HTML or a URL asynchronously and returns a JSON document response.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   *
   * @param params parameters for the generate PDF request.
   * @return a future that completes with the generated document metadata.
   */
  public CompletableFuture<PdfGateDocument> generatePdfAsync(GeneratePdfParams params) {
    return enqueuer.enqueueAsFuture(generatePdfCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   *
   * @param params parameters for the generate PDF request.
   * @return a call that yields a {@link PdfGateDocument} response.
   */
  public CallJson generatePdfCall(GeneratePdfParams params) {
    return new PdfGateJsonCall(callBuilder.buildGeneratePdfCall(params));
  }

  /**
   * Flattens a PDF and returns a JSON document response.
   *
   * <p>This SDK currently supports flattening by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * @param params parameters for the flatten PDF request.
   * @return the flattened document metadata.
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument flattenPdf(FlattenPdfParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(flattenPdfCall(params));
  }

  /**
   * Flattens a PDF asynchronously and returns a JSON document response.
   *
   * <p>This SDK currently supports flattening by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   *
   * @param params parameters for the flatten PDF request.
   * @return a future that completes with the flattened document metadata.
   */
  public CompletableFuture<PdfGateDocument> flattenPdfAsync(FlattenPdfParams params) {
    return enqueuer.enqueueAsFuture(flattenPdfCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   *
   * <p>This SDK currently supports flattening by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * @param params parameters for the flatten PDF request.
   * @return a call that yields a {@link PdfGateDocument} response.
   */
  public CallJson flattenPdfCall(FlattenPdfParams params) {
    return new PdfGateJsonCall(callBuilder.buildFlattenPdfCall(params));
  }

  /**
   * Protects a PDF and returns a JSON document response.
   *
   * <p>This SDK currently supports protecting by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * @param params parameters for the protect PDF request.
   * @return the protected document metadata.
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument protectPdf(ProtectPdfParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(protectPdfCall(params));
  }

  /**
   * Protects a PDF asynchronously and returns a JSON document response.
   *
   * <p>This SDK currently supports protecting by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   *
   * @param params parameters for the protect PDF request.
   * @return a future that completes with the protected document metadata.
   */
  public CompletableFuture<PdfGateDocument> protectPdfAsync(ProtectPdfParams params) {
    return enqueuer.enqueueAsFuture(protectPdfCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   *
   * <p>This SDK currently supports protecting by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * @param params parameters for the protect PDF request.
   * @return a call that yields a {@link PdfGateDocument} response.
   */
  public CallJson protectPdfCall(ProtectPdfParams params) {
    return new PdfGateJsonCall(callBuilder.buildProtectPdfCall(params));
  }

  /**
   * Compresses a PDF and returns a JSON document response.
   *
   * <p>This SDK currently supports compressing by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * @param params parameters for the compress PDF request.
   * @return the compressed document metadata.
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument compressPdf(CompressPdfParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(compressPdfCall(params));
  }

  /**
   * Compresses a PDF asynchronously and returns a JSON document response.
   *
   * <p>This SDK currently supports compressing by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   *
   * @param params parameters for the compress PDF request.
   * @return a future that completes with the compressed document metadata.
   */
  public CompletableFuture<PdfGateDocument> compressPdfAsync(CompressPdfParams params) {
    return enqueuer.enqueueAsFuture(compressPdfCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   *
   * <p>This SDK currently supports compressing by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * @param params parameters for the compress PDF request.
   * @return a call that yields a {@link PdfGateDocument} response.
   */
  public CallJson compressPdfCall(CompressPdfParams params) {
    return new PdfGateJsonCall(callBuilder.buildCompressPdfCall(params));
  }

  /**
   * Applies a watermark to a PDF and returns a JSON document response.
   *
   * <p>This SDK currently supports watermarking by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * @param params parameters for the watermark PDF request.
   * @return the watermarked document metadata.
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument watermarkPdf(WatermarkPdfParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(watermarkPdfCall(params));
  }

  /**
   * Applies a watermark to a PDF asynchronously and returns a JSON document response.
   *
   * <p>This SDK currently supports watermarking by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   *
   * @param params parameters for the watermark PDF request.
   * @return a future that completes with the watermarked document metadata.
   */
  public CompletableFuture<PdfGateDocument> watermarkPdfAsync(WatermarkPdfParams params) {
    return enqueuer.enqueueAsFuture(watermarkPdfCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   *
   * <p>This SDK currently supports watermarking by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * @param params parameters for the watermark PDF request.
   * @return a call that yields a {@link PdfGateDocument} response.
   */
  public CallJson watermarkPdfCall(WatermarkPdfParams params) {
    return new PdfGateJsonCall(callBuilder.buildWatermarkPdfCall(params));
  }

  /**
   * Creates an envelope and returns the envelope metadata response.
   *
   * @param params parameters for the create envelope request.
   * @return the created envelope metadata.
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PDFGateEnvelope createEnvelope(CreateEnvelopeParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(createEnvelopeCall(params));
  }

  /**
   * Creates an envelope asynchronously and returns the envelope metadata response.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   *
   * @param params parameters for the create envelope request.
   * @return a future that completes with the created envelope metadata.
   */
  public CompletableFuture<PDFGateEnvelope> createEnvelopeAsync(CreateEnvelopeParams params) {
    return enqueuer.enqueueAsFuture(createEnvelopeCall(params));
  }

  /**
   * Builds a call that expects an envelope JSON response.
   *
   * @param params parameters for the create envelope request.
   * @return a call that yields a {@link PDFGateEnvelope} response.
   */
  public CallEnvelope createEnvelopeCall(CreateEnvelopeParams params) {
    return new PdfGateEnvelopeCall(callBuilder.buildCreateEnvelopeCall(params));
  }

  /**
   * Sends an envelope and returns the updated envelope metadata response.
   *
   * @param params parameters for the send envelope request.
   * @return the updated envelope metadata.
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PDFGateEnvelope sendEnvelope(SendEnvelopeParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(sendEnvelopeCall(params));
  }

  /**
   * Sends an envelope asynchronously and returns the updated envelope metadata response.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   *
   * @param params parameters for the send envelope request.
   * @return a future that completes with the updated envelope metadata.
   */
  public CompletableFuture<PDFGateEnvelope> sendEnvelopeAsync(SendEnvelopeParams params) {
    return enqueuer.enqueueAsFuture(sendEnvelopeCall(params));
  }

  /**
   * Builds a call that expects an envelope JSON response.
   *
   * @param params parameters for the send envelope request.
   * @return a call that yields a {@link PDFGateEnvelope} response.
   */
  public CallEnvelope sendEnvelopeCall(SendEnvelopeParams params) {
    return new PdfGateEnvelopeCall(callBuilder.buildSendEnvelopeCall(params));
  }

  /**
   * Extracts PDF form field data and returns the JSON response.
   *
   * <p>This SDK currently supports extraction by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * @param params parameters for the extract form data request.
   * @return the JSON response containing form field data.
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public JsonObject extractPdfFormData(ExtractPdfFormDataParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(extractPdfFormDataCall(params));
  }

  /**
   * Extracts PDF form field data asynchronously and returns the JSON response.
   *
   * <p>This SDK currently supports extraction by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   *
   * @param params parameters for the extract form data request.
   * @return a future that completes with the JSON form data response.
   */
  public CompletableFuture<JsonObject> extractPdfFormDataAsync(ExtractPdfFormDataParams params) {
    return enqueuer.enqueueAsFuture(extractPdfFormDataCall(params));
  }

  /**
   * Builds a call that expects a JSON response containing form field data.
   *
   * <p>This SDK currently supports extraction by {@code documentId} only. To upload a file
   * directly, use {@link #uploadFile(UploadFileParams)} first and pass the resulting
   * document ID.
   *
   * @param params parameters for the extract form data request.
   * @return a call that yields JSON form field data.
   */
  public CallJsonObject extractPdfFormDataCall(ExtractPdfFormDataParams params) {
    return new PdfGateJsonObjectCall(callBuilder.buildExtractPdfFormDataCall(params));
  }

  /**
   * Retrieves document metadata (and optionally a fresh pre-signed URL).
   *
   * @param params parameters for the get document request.
   * @return the document metadata response.
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
   *
   * @param params parameters for the get document request.
   * @return a future that completes with the document metadata.
   */
  public CompletableFuture<PdfGateDocument> getDocumentAsync(GetDocumentParams params) {
    return enqueuer.enqueueAsFuture(getDocumentCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   *
   * @param params parameters for the get document request.
   * @return a call that yields a {@link PdfGateDocument} response.
   */
  public CallJson getDocumentCall(GetDocumentParams params) {
    return new PdfGateJsonCall(callBuilder.buildGetDocumentCall(params));
  }

  /**
   * Retrieves a stored document file.
   *
   * <p>Accessing stored files requires enabling "Save files" in the PDFGate Dashboard settings.
   *
   * @param params parameters for the get file request.
   * @return the stored PDF bytes.
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
   *
   * @param params parameters for the get file request.
   * @return a future that completes with the stored PDF bytes.
   */
  public CompletableFuture<byte[]> getFileAsync(GetFileParams params) {
    return enqueuer.enqueueAsFuture(getFileCall(params));
  }

  /**
   * Builds a call that expects a raw bytes response.
   *
   * @param params parameters for the get file request.
   * @return a call that yields raw PDF bytes.
   */
  public CallFile getFileCall(GetFileParams params) {
    return new PdfGateFileCall(callBuilder.buildGetFileCall(params));
  }

  /**
   * Uploads a PDF file passing the file or through a URL pointing to the file and
   * returns a JSON document response.
   *
   * @param params parameters for the upload file request.
   * @return the uploaded document metadata.
   * @throws PdfGateException when the request fails or the API returns a non-2xx response.
   */
  public PdfGateDocument uploadFile(UploadFileParams params)
      throws IOException {
    return PdfGateCallExecutor.execute(uploadFileCall(params));
  }

  /**
   * Uploads a PDF file passing the file or through a URL pointing to the file and
   * returns a JSON document response.
   *
   * <p>The returned future completes exceptionally with {@link PdfGateException} on errors.
   *
   * @param params parameters for the upload file request.
   * @return a future that completes with the uploaded document metadata.
   */
  public CompletableFuture<PdfGateDocument> uploadFileAsync(UploadFileParams params) {
    return enqueuer.enqueueAsFuture(uploadFileCall(params));
  }

  /**
   * Builds a call that expects a JSON document response.
   *
   * @param params parameters for the upload file request.
   * @return a call that yields a {@link PdfGateDocument} response.
   */
  public CallJson uploadFileCall(UploadFileParams params) {
    return new PdfGateJsonCall(callBuilder.buildUploadFileCall(params));
  }

  /**
   * Enqueues a JSON response call and maps the response to {@link PdfGateDocument}.
   *
   * @param call     the call to enqueue.
   * @param callback the callback for the response.
   */
  public void enqueue(CallJson call, PdfGateCallback<PdfGateDocument> callback) {
    enqueuer.enqueue(call, callback);
  }

  /**
   * Enqueues a bytes response call and returns the raw response bytes.
   *
   * @param call     the call to enqueue.
   * @param callback the callback for the response.
   */
  public void enqueue(CallFile call, PdfGateCallback<byte[]> callback) {
    enqueuer.enqueue(call, callback);
  }

  /**
   * Enqueues a JSON response call and maps the response to {@link JsonObject}.
   *
   * @param call     the call to enqueue.
   * @param callback the callback for the response.
   */
  public void enqueue(CallJsonObject call, PdfGateCallback<JsonObject> callback) {
    enqueuer.enqueue(call, callback);
  }

  /**
   * Enqueues a JSON response call and maps the response to {@link PDFGateEnvelope}.
   *
   * @param call     the call to enqueue.
   * @param callback the callback for the response.
   */
  public void enqueue(CallEnvelope call, PdfGateCallback<PDFGateEnvelope> callback) {
    enqueuer.enqueue(call, callback);
  }

}
