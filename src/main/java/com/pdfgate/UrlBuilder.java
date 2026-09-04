package com.pdfgate;

final class UrlBuilder {
  private static final String API_VERSION = "v1";
  private final String baseUrl;

  UrlBuilder(String apiKey, PdfGateConfig config) {
    String domain = getDomainFromApiKey(apiKey, config);
    if (Strings.isBlank(domain)) {
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

  private static String normalizeBase(String domain) {
    String trimmed = domain.trim();
    if (trimmed.endsWith("/")) {
      return trimmed.substring(0, trimmed.length() - 1);
    }
    return trimmed;
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

  String addFormFields() {
    return baseUrl + "/forms/fields";
  }

  String watermarkPdf() {
    return baseUrl + "/watermark/pdf";
  }

  String protectPdf() {
    return baseUrl + "/protect/pdf";
  }

  String compressPdf() {
    return baseUrl + "/compress/pdf";
  }

  String createEnvelope() {
    return baseUrl + "/envelope";
  }

  String sendEnvelope(String id) {
    return baseUrl + "/envelope/" + id + "/send";
  }

  String getEnvelope(String id) {
    return baseUrl + "/envelope/" + id;
  }

  String voidEnvelope(String id) {
    return baseUrl + "/envelope/" + id + "/void";
  }

  String deleteEnvelope(String id) {
    return baseUrl + "/envelope/" + id;
  }

  String getDocument(String documentId) {
    return baseUrl + "/document/" + documentId;
  }

  String getFile(String documentId) {
    return baseUrl + "/file/" + documentId;
  }

  String uploadFile() {
    return baseUrl + "/upload";
  }

  String webhook() {
    return baseUrl + "/webhook";
  }

  String webhook(String id) {
    return baseUrl + "/webhook/" + id;
  }
}
