package com.pdfgate;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import okhttp3.OkHttpClient;

public final class PdfGate {
    /** Builds OkHttp calls for PdfGate API requests. */
    private final PdfGateCallBuilder callBuilder;
    /** Enqueues calls and adapts responses for async usage. */
    private final PdfGateEnqueuer enqueuer;

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
        UrlBuilder urlBuilder = new UrlBuilder(apiKey, config);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(config.getDefaultTimeout())
                .build();
        this.callBuilder = new PdfGateCallBuilder(apiKey, httpClient, config, urlBuilder);
        this.enqueuer = new PdfGateEnqueuer();
    }

    /**
     * Generates a PDF and returns raw bytes.
     */
    public byte[] generatePdf(GeneratePdfFileParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(generatePdfCall(params));
    }

    /**
     * Generates a PDF and returns the document metadata from a JSON response.
     */
    public PdfGateDocument generatePdf(GeneratePdfJsonParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(generatePdfCall(params));
    }

    /**
     * Generates a PDF asynchronously and returns raw bytes.
     */
    public CompletableFuture<byte[]> generatePdfAsync(GeneratePdfFileParams params) {
        return enqueuer.enqueueAsFuture(generatePdfCall(params));
    }

    /**
     * Generates a PDF asynchronously and returns the document metadata from a JSON response.
     */
    public CompletableFuture<PdfGateDocument> generatePdfAsync(GeneratePdfJsonParams params) {
        return enqueuer.enqueueAsFuture(generatePdfCall(params));
    }

    /**
     * Builds a call that expects a JSON response.
     */
    public CallJson generatePdfCall(GeneratePdfJsonParams params) {
        return new PdfGateJsonCall(callBuilder.buildGeneratePdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallFile generatePdfCall(GeneratePdfFileParams params) {
        return new PdfGateFileCall(callBuilder.buildGeneratePdfCall(params));
    }

    /**
     * Flattens a PDF provided as a file and returns raw bytes.
     */
    public byte[] flattenPdf(FlattenPdfFileParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(flattenPdfCall(params));
    }

    /**
     * Flattens a PDF provided as a file and returns the document metadata from a JSON response.
     */
    public PdfGateDocument flattenPdf(FlattenPdfJsonParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(flattenPdfCall(params));
    }

    /**
     * Flattens a PDF asynchronously and returns raw bytes.
     */
    public CompletableFuture<byte[]> flattenPdfAsync(FlattenPdfFileParams params) {
        return enqueuer.enqueueAsFuture(flattenPdfCall(params));
    }

    /**
     * Flattens a PDF provided as a file asynchronously and returns the document metadata from a JSON response.
     */
    public CompletableFuture<PdfGateDocument> flattenPdfAsync(FlattenPdfJsonParams params) {
        return enqueuer.enqueueAsFuture(flattenPdfCall(params));
    }

    /**
     * Builds a call that expects a JSON response.
     */
    public CallJson flattenPdfCall(FlattenPdfJsonParams params) {
        return new PdfGateJsonCall(callBuilder.buildFlattenPdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallFile flattenPdfCall(FlattenPdfFileParams params) {
        return new PdfGateFileCall(callBuilder.buildFlattenPdfCall(params));
    }

    /**
     * Protects a PDF and returns raw bytes.
     */
    public byte[] protectPdf(ProtectPdfFileParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(protectPdfCall(params));
    }

    /**
     * Protects a PDF and returns the document metadata from a JSON response.
     */
    public PdfGateDocument protectPdf(ProtectPdfJsonParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(protectPdfCall(params));
    }

    /**
     * Protects a PDF asynchronously and returns raw bytes.
     */
    public CompletableFuture<byte[]> protectPdfAsync(ProtectPdfFileParams params) {
        return enqueuer.enqueueAsFuture(protectPdfCall(params));
    }

    /**
     * Protects a PDF asynchronously and returns the document metadata from a JSON response.
     */
    public CompletableFuture<PdfGateDocument> protectPdfAsync(ProtectPdfJsonParams params) {
        return enqueuer.enqueueAsFuture(protectPdfCall(params));
    }

    /**
     * Builds a call that expects a JSON response.
     */
    public CallJson protectPdfCall(ProtectPdfJsonParams params) {
        return new PdfGateJsonCall(callBuilder.buildProtectPdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallFile protectPdfCall(ProtectPdfFileParams params) {
        return new PdfGateFileCall(callBuilder.buildProtectPdfCall(params));
    }

    /**
     * Compresses a PDF and returns raw bytes.
     */
    public byte[] compressPdf(CompressPdfFileParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(compressPdfCall(params));
    }

    /**
     * Compresses a PDF and returns the document metadata from a JSON response.
     */
    public PdfGateDocument compressPdf(CompressPdfJsonParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(compressPdfCall(params));
    }

    /**
     * Compresses a PDF asynchronously and returns raw bytes.
     */
    public CompletableFuture<byte[]> compressPdfAsync(CompressPdfFileParams params) {
        return enqueuer.enqueueAsFuture(compressPdfCall(params));
    }

    /**
     * Compresses a PDF asynchronously and returns the document metadata from a JSON response.
     */
    public CompletableFuture<PdfGateDocument> compressPdfAsync(CompressPdfJsonParams params) {
        return enqueuer.enqueueAsFuture(compressPdfCall(params));
    }

    /**
     * Builds a call that expects a JSON response.
     */
    public CallJson compressPdfCall(CompressPdfJsonParams params) {
        return new PdfGateJsonCall(callBuilder.buildCompressPdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallFile compressPdfCall(CompressPdfFileParams params) {
        return new PdfGateFileCall(callBuilder.buildCompressPdfCall(params));
    }

    /**
     * Applies a watermark to a PDF and returns raw bytes.
     */
    public byte[] watermarkPdf(WatermarkPdfFileParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(watermarkPdfCall(params));
    }

    /**
     * Applies a watermark to a PDF and returns the document metadata from a JSON response.
     */
    public PdfGateDocument watermarkPdf(WatermarkPdfJsonParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(watermarkPdfCall(params));
    }

    /**
     * Applies a watermark to a PDF asynchronously and returns raw bytes.
     */
    public CompletableFuture<byte[]> watermarkPdfAsync(WatermarkPdfFileParams params) {
        return enqueuer.enqueueAsFuture(watermarkPdfCall(params));
    }

    /**
     * Applies a watermark to a PDF asynchronously and returns the document metadata from a JSON response.
     */
    public CompletableFuture<PdfGateDocument> watermarkPdfAsync(WatermarkPdfJsonParams params) {
        return enqueuer.enqueueAsFuture(watermarkPdfCall(params));
    }

    /**
     * Builds a call that expects a JSON response.
     */
    public CallJson watermarkPdfCall(WatermarkPdfJsonParams params) {
        return new PdfGateJsonCall(callBuilder.buildWatermarkPdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallFile watermarkPdfCall(WatermarkPdfFileParams params) {
        return new PdfGateFileCall(callBuilder.buildWatermarkPdfCall(params));
    }

    /**
     * Extracts PDF form field data and returns the JSON response.
     */
    public JsonObject extractPdfFormData(ExtractPdfFormDataParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(extractPdfFormDataCall(params));
    }

    /**
     * Extracts PDF form field data asynchronously and returns the JSON response.
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
     * Retrieves document metadata.
     */
    public PdfGateDocument getDocument(GetDocumentParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(getDocumentCall(params));
    }

    /**
     * Retrieves document metadata asynchronously.
     */
    public CompletableFuture<PdfGateDocument> getDocumentAsync(GetDocumentParams params) {
        return enqueuer.enqueueAsFuture(getDocumentCall(params));
    }

    /**
     * Builds a call that expects a JSON response.
     */
    public CallJson getDocumentCall(GetDocumentParams params) {
        return new PdfGateJsonCall(callBuilder.buildGetDocumentCall(params));
    }

    /**
     * Retrieves a stored document file.
     */
    public byte[] getFile(GetFileParams params)
            throws IOException {
        return PdfGateCallExecutor.execute(getFileCall(params));
    }

    /**
     * Retrieves a stored document file asynchronously.
     */
    public CompletableFuture<byte[]> getFileAsync(GetFileParams params) {
        return enqueuer.enqueueAsFuture(getFileCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
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
