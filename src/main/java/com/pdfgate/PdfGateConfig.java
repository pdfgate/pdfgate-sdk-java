package com.pdfgate;

import java.time.Duration;
import java.util.Objects;

/**
 * Configuration for the PDFGate client.
 *
 * <p>Use {@link #defaultConfig()} to start with defaults and override values using the
 * factory methods when needed.
 */
public final class PdfGateConfig {
  private static final String DEFAULT_PRODUCTION_API_DOMAIN = "https://api.pdfgate.com";
  private static final String DEFAULT_SANDBOX_API_DOMAIN = "https://api-sandbox.pdfgate.com";
  private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);
  private static final Duration DEFAULT_GENERATE_PDF_TIMEOUT = Duration.ofMinutes(15);
  private static final Duration DEFAULT_FLATTEN_PDF_TIMEOUT = Duration.ofMinutes(3);
  private static final Duration DEFAULT_COMPRESS_PDF_TIMEOUT = Duration.ofMinutes(3);
  private static final Duration DEFAULT_PROTECT_PDF_TIMEOUT = Duration.ofMinutes(3);

  private final String productionApiDomain;
  private final String sandboxApiDomain;
  private final Duration defaultTimeout;
  private final Duration generatePdfTimeout;
  private final Duration flattenPdfTimeout;
  private final Duration compressPdfTimeout;
  private final Duration protectPdfTimeout;

  private PdfGateConfig(
      String productionApiDomain,
      String sandboxApiDomain,
      Duration defaultTimeout,
      Duration generatePdfTimeout,
      Duration flattenPdfTimeout,
      Duration compressPdfTimeout,
      Duration protectPdfTimeout
  ) {
    this.productionApiDomain = requireNonBlank(productionApiDomain, "productionApiDomain");
    this.sandboxApiDomain = requireNonBlank(sandboxApiDomain, "sandboxApiDomain");
    this.defaultTimeout = Objects.requireNonNull(defaultTimeout, "defaultTimeout");
    this.generatePdfTimeout = Objects.requireNonNull(generatePdfTimeout, "generatePdfTimeout");
    this.flattenPdfTimeout = Objects.requireNonNull(flattenPdfTimeout, "flattenPdfTimeout");
    this.compressPdfTimeout = Objects.requireNonNull(compressPdfTimeout, "compressPdfTimeout");
    this.protectPdfTimeout = Objects.requireNonNull(protectPdfTimeout, "protectPdfTimeout");
  }

  /**
   * Returns the default configuration for production and sandbox domains.
   *
   * @return the default configuration for production and sandbox domains.
   */
  public static PdfGateConfig defaultConfig() {
    return new PdfGateConfig(
        DEFAULT_PRODUCTION_API_DOMAIN,
        DEFAULT_SANDBOX_API_DOMAIN,
        DEFAULT_TIMEOUT,
        DEFAULT_GENERATE_PDF_TIMEOUT,
        DEFAULT_FLATTEN_PDF_TIMEOUT,
        DEFAULT_COMPRESS_PDF_TIMEOUT,
        DEFAULT_PROTECT_PDF_TIMEOUT
    );
  }

  /**
   * Creates a configuration with custom domains and default timeout.
   *
   * @param productionApiDomain production API base URL.
   * @param sandboxApiDomain sandbox API base URL.
   * @param defaultTimeout default timeout for requests.
   * @param generatePdfTimeout timeout for generate PDF requests.
   * @return the configured {@link PdfGateConfig}.
   */
  public static PdfGateConfig of(
      String productionApiDomain,
      String sandboxApiDomain,
      Duration defaultTimeout,
      Duration generatePdfTimeout
  ) {
    return new PdfGateConfig(
        productionApiDomain,
        sandboxApiDomain,
        defaultTimeout,
        generatePdfTimeout,
        DEFAULT_FLATTEN_PDF_TIMEOUT,
        DEFAULT_COMPRESS_PDF_TIMEOUT,
        DEFAULT_PROTECT_PDF_TIMEOUT
    );
  }

  /**
   * Creates a configuration with custom domains and flatten timeout.
   *
   * @param productionApiDomain production API base URL.
   * @param sandboxApiDomain sandbox API base URL.
   * @param defaultTimeout default timeout for requests.
   * @param generatePdfTimeout timeout for generate PDF requests.
   * @param flattenPdfTimeout timeout for flatten PDF requests.
   * @return the configured {@link PdfGateConfig}.
   */
  public static PdfGateConfig of(
      String productionApiDomain,
      String sandboxApiDomain,
      Duration defaultTimeout,
      Duration generatePdfTimeout,
      Duration flattenPdfTimeout
  ) {
    return new PdfGateConfig(
        productionApiDomain,
        sandboxApiDomain,
        defaultTimeout,
        generatePdfTimeout,
        flattenPdfTimeout,
        DEFAULT_COMPRESS_PDF_TIMEOUT,
        DEFAULT_PROTECT_PDF_TIMEOUT
    );
  }

  /**
   * Creates a configuration with custom domains and protect timeout.
   *
   * @param productionApiDomain production API base URL.
   * @param sandboxApiDomain sandbox API base URL.
   * @param defaultTimeout default timeout for requests.
   * @param generatePdfTimeout timeout for generate PDF requests.
   * @param flattenPdfTimeout timeout for flatten PDF requests.
   * @param protectPdfTimeout timeout for protect PDF requests.
   * @return the configured {@link PdfGateConfig}.
   */
  public static PdfGateConfig of(
      String productionApiDomain,
      String sandboxApiDomain,
      Duration defaultTimeout,
      Duration generatePdfTimeout,
      Duration flattenPdfTimeout,
      Duration protectPdfTimeout
  ) {
    return new PdfGateConfig(
        productionApiDomain,
        sandboxApiDomain,
        defaultTimeout,
        generatePdfTimeout,
        flattenPdfTimeout,
        DEFAULT_COMPRESS_PDF_TIMEOUT,
        protectPdfTimeout
    );
  }

  /**
   * Creates a configuration with custom domains and per-endpoint timeouts.
   *
   * @param productionApiDomain production API base URL.
   * @param sandboxApiDomain sandbox API base URL.
   * @param defaultTimeout default timeout for requests.
   * @param generatePdfTimeout timeout for generate PDF requests.
   * @param flattenPdfTimeout timeout for flatten PDF requests.
   * @param compressPdfTimeout timeout for compress PDF requests.
   * @param protectPdfTimeout timeout for protect PDF requests.
   * @return the configured {@link PdfGateConfig}.
   */
  public static PdfGateConfig of(
      String productionApiDomain,
      String sandboxApiDomain,
      Duration defaultTimeout,
      Duration generatePdfTimeout,
      Duration flattenPdfTimeout,
      Duration compressPdfTimeout,
      Duration protectPdfTimeout
  ) {
    return new PdfGateConfig(
        productionApiDomain,
        sandboxApiDomain,
        defaultTimeout,
        generatePdfTimeout,
        flattenPdfTimeout,
        compressPdfTimeout,
        protectPdfTimeout
    );
  }

  private static String requireNonBlank(String value, String label) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(label + " must be provided.");
    }
    return value;
  }

  /**
   * Returns the production API base URL.
   *
   * @return the production API base URL.
   */
  public String getProductionApiDomain() {
    return productionApiDomain;
  }

  /**
   * Returns the sandbox API base URL.
   *
   * @return the sandbox API base URL.
   */
  public String getSandboxApiDomain() {
    return sandboxApiDomain;
  }

  /**
   * Returns the default timeout applied when no endpoint-specific timeout exists.
   *
   * @return the default timeout applied when no endpoint-specific timeout exists.
   */
  public Duration getDefaultTimeout() {
    return defaultTimeout;
  }

  /**
   * Returns the timeout used for generate PDF requests.
   *
   * @return the timeout used for generate PDF requests.
   */
  public Duration getGeneratePdfTimeout() {
    return generatePdfTimeout;
  }

  /**
   * Returns the timeout used for flatten PDF requests.
   *
   * @return the timeout used for flatten PDF requests.
   */
  public Duration getFlattenPdfTimeout() {
    return flattenPdfTimeout;
  }

  /**
   * Returns the timeout used for compress PDF requests.
   *
   * @return the timeout used for compress PDF requests.
   */
  public Duration getCompressPdfTimeout() {
    return compressPdfTimeout;
  }

  /**
   * Returns the timeout used for protect PDF requests.
   *
   * @return the timeout used for protect PDF requests.
   */
  public Duration getProtectPdfTimeout() {
    return protectPdfTimeout;
  }
}
