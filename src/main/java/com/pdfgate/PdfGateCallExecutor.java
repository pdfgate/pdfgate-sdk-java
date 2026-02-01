package com.pdfgate;

import java.io.IOException;

import com.google.gson.JsonObject;
import okhttp3.Response;

/**
 * Executes PdfGate API calls and parses responses.
 */
final class PdfGateCallExecutor {
    private PdfGateCallExecutor() {
    }

    static byte[] execute(CallFile call) throws PdfGateException {
        try (Response response = call.execute()) {
            return PdfGateResponseParser.parseBytes(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    static PdfGateDocument execute(CallJson call) throws PdfGateException {
        try (Response response = call.execute()) {
            return PdfGateResponseParser.parseJson(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }

    static JsonObject execute(CallJsonObject call) throws PdfGateException {
        try (Response response = call.execute()) {
            return PdfGateResponseParser.parseJsonObject(response);
        } catch (PdfGateException e) {
            throw e;
        } catch (IOException e) {
            throw PdfGateException.fromException(e);
        }
    }
}
