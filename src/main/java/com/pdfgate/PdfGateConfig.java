package com.pdfgate;

import java.time.Duration;
import java.util.Objects;

public final class PdfGateConfig {
    private static final String DEFAULT_PRODUCTION_API_DOMAIN = "https://api.pdfgate.com";
    private static final String DEFAULT_SANDBOX_API_DOMAIN = "https://api-sandbox.pdfgate.com";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);
    private static final Duration DEFAULT_GENERATE_PDF_TIMEOUT = Duration.ofMinutes(15);

    private final String productionApiDomain;
    private final String sandboxApiDomain;
    private final Duration defaultTimeout;
    private final Duration generatePdfTimeout;

    private PdfGateConfig(
            String productionApiDomain,
            String sandboxApiDomain,
            Duration defaultTimeout,
            Duration generatePdfTimeout
    ) {
        this.productionApiDomain = requireNonBlank(productionApiDomain, "productionApiDomain");
        this.sandboxApiDomain = requireNonBlank(sandboxApiDomain, "sandboxApiDomain");
        this.defaultTimeout = Objects.requireNonNull(defaultTimeout, "defaultTimeout");
        this.generatePdfTimeout = Objects.requireNonNull(generatePdfTimeout, "generatePdfTimeout");
    }

    public static PdfGateConfig defaultConfig() {
        return new PdfGateConfig(
                DEFAULT_PRODUCTION_API_DOMAIN,
                DEFAULT_SANDBOX_API_DOMAIN,
                DEFAULT_TIMEOUT,
                DEFAULT_GENERATE_PDF_TIMEOUT
        );
    }

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
                generatePdfTimeout
        );
    }

    public String getProductionApiDomain() {
        return productionApiDomain;
    }

    public String getSandboxApiDomain() {
        return sandboxApiDomain;
    }

    public Duration getDefaultTimeout() {
        return defaultTimeout;
    }

    public Duration getGeneratePdfTimeout() {
        return generatePdfTimeout;
    }

    private static String requireNonBlank(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " must be provided.");
        }
        return value;
    }
}
