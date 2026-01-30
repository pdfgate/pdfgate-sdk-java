package com.pdfgate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;
import okhttp3.*;

public final class PdfGate {
    /** Builds OkHttp calls for PdfGate API requests. */
    private final PdfGateCallBuilder callBuilder;

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
        return new PdfGateJsonCall(callBuilder.buildGeneratePdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallBytes generatePdfCall(GeneratePdfBytesParams params) {
        return new PdfGateBytesCall(callBuilder.buildGeneratePdfCall(params));
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
        return new PdfGateJsonCall(callBuilder.buildFlattenPdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallBytes flattenPdfCall(FlattenPdfBytesParams params) {
        return new PdfGateBytesCall(callBuilder.buildFlattenPdfCall(params));
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
        return new PdfGateJsonCall(callBuilder.buildProtectPdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallBytes protectPdfCall(ProtectPdfBytesParams params) {
        return new PdfGateBytesCall(callBuilder.buildProtectPdfCall(params));
    }

    /**
     * Compresses a PDF and returns raw bytes.
     */
    public byte[] compressPdf(CompressPdfBytesParams params)
            throws IOException {
        try (Response response = compressPdfCall(params).execute()) {
            return PdfGateResponseParser.parseBytes(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Compresses a PDF and returns the document metadata from a JSON response.
     */
    public PdfGateDocument compressPdf(CompressPdfJsonParams params)
            throws IOException {
        try (Response response = compressPdfCall(params).execute()) {
            return PdfGateResponseParser.parseJson(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Compresses a PDF asynchronously and returns raw bytes.
     */
    public CompletableFuture<byte[]> compressPdfAsync(CompressPdfBytesParams params) {
        return enqueueAsFuture(compressPdfCall(params));
    }

    /**
     * Compresses a PDF asynchronously and returns the document metadata from a JSON response.
     */
    public CompletableFuture<PdfGateDocument> compressPdfAsync(CompressPdfJsonParams params) {
        return enqueueAsFuture(compressPdfCall(params));
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
    public CallBytes compressPdfCall(CompressPdfBytesParams params) {
        return new PdfGateBytesCall(callBuilder.buildCompressPdfCall(params));
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
        return new PdfGateJsonCall(callBuilder.buildWatermarkPdfCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallBytes watermarkPdfCall(WatermarkPdfBytesParams params) {
        return new PdfGateBytesCall(callBuilder.buildWatermarkPdfCall(params));
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
        return new PdfGateJsonObjectCall(callBuilder.buildExtractPdfFormDataCall(params));
    }

    /**
     * Retrieves document metadata.
     */
    public PdfGateDocument getDocument(GetDocumentParams params)
            throws IOException {
        try (Response response = getDocumentCall(params).execute()) {
            return PdfGateResponseParser.parseJson(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Retrieves document metadata asynchronously.
     */
    public CompletableFuture<PdfGateDocument> getDocumentAsync(GetDocumentParams params) {
        return enqueueAsFuture(getDocumentCall(params));
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
        try (Response response = getFileCall(params).execute()) {
            return PdfGateResponseParser.parseBytes(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    /**
     * Retrieves a stored document file asynchronously.
     */
    public CompletableFuture<byte[]> getFileAsync(GetFileParams params) {
        return enqueueAsFuture(getFileCall(params));
    }

    /**
     * Builds a call that expects a bytes' response.
     */
    public CallBytes getFileCall(GetFileParams params) {
        return new PdfGateBytesCall(callBuilder.buildGetFileCall(params));
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

}
