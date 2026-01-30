package com.pdfgate;

final class UrlBuilder {
    private static final String API_VERSION = "v1";
    private final String baseUrl;

    UrlBuilder(String apiKey, PdfGateConfig config) {
        String domain = getDomainFromApiKey(apiKey, config);
        if (domain == null || domain.isBlank()) {
            throw new IllegalArgumentException("domain must be provided.");
        }
        this.baseUrl = normalizeBase(domain);
    }

    private static String getDomainFromApiKey(String apiKey, PdfGateConfig config) {
        if (apiKey.startsWith("live_")) {
            return config.getProductionApiDomain();
        }
        if (apiKey.startsWith("test_")) {
            return config.getSandboxApiDomain();
        }
        throw new IllegalArgumentException(
                "Invalid API key format. Expected to start with 'live_' or 'test_'."
        );
    }

    String generatePdf() {
        return baseUrl + "/" + API_VERSION + "/generate/pdf";
    }

    String flattenPdf() {
        return baseUrl + "/forms/flatten";
    }

    String extractPdfFormData() {
        return baseUrl + "/forms/extract-data";
    }

    private static String normalizeBase(String domain) {
        String trimmed = domain.trim();
        if (trimmed.endsWith("/")) {
            return trimmed.substring(0, trimmed.length() - 1);
        }
        return trimmed;
    }
}
