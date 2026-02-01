package com.pdfgate;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public abstract class GeneratePdfParams {
    public enum PageSizeType {
        @SerializedName("a0")
        A0,
        @SerializedName("a1")
        A1,
        @SerializedName("a2")
        A2,
        @SerializedName("a3")
        A3,
        @SerializedName("a4")
        A4,
        @SerializedName("a5")
        A5,
        @SerializedName("a6")
        A6,
        @SerializedName("ledger")
        LEDGER,
        @SerializedName("tabloid")
        TABLOID,
        @SerializedName("legal")
        LEGAL,
        @SerializedName("letter")
        LETTER
    }

    public enum FileOrientation {
        @SerializedName("portrait")
        PORTRAIT,
        @SerializedName("landscape")
        LANDSCAPE
    }

    public enum EmulateMediaType {
        @SerializedName("screen")
        SCREEN,
        @SerializedName("print")
        PRINT
    }

    public static final class PdfPageMargin {
        private final String top;
        private final String bottom;
        private final String left;
        private final String right;

        public PdfPageMargin(String top, String bottom, String left, String right) {
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
        }

        public String getTop() {
            return top;
        }

        public String getBottom() {
            return bottom;
        }

        public String getLeft() {
            return left;
        }

        public String getRight() {
            return right;
        }
    }

    public static final class ClickSelectorChain {
        private final List<String> selectors;

        public ClickSelectorChain(List<String> selectors) {
            this.selectors = selectors;
        }

        public List<String> getSelectors() {
            return selectors;
        }
    }

    public static final class ClickSelectorChainSetup {
        private final Boolean ignoreFailingChains;
        private final List<ClickSelectorChain> chains;

        public ClickSelectorChainSetup(Boolean ignoreFailingChains, List<ClickSelectorChain> chains) {
            this.ignoreFailingChains = ignoreFailingChains;
            this.chains = chains;
        }

        public Boolean getIgnoreFailingChains() {
            return ignoreFailingChains;
        }

        public List<ClickSelectorChain> getChains() {
            return chains;
        }
    }

    public static final class GeneratePdfAuthentication {
        private final String username;
        private final String password;

        public GeneratePdfAuthentication(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public static final class Viewport {
        private final Integer width;
        private final Integer height;

        public Viewport(Integer width, Integer height) {
            this.width = width;
            this.height = height;
        }

        public Integer getWidth() {
            return width;
        }

        public Integer getHeight() {
            return height;
        }
    }

    private final String html;
    private final String url;
    private final Boolean jsonResponse;
    private final Long preSignedUrlExpiresIn;
    private final PageSizeType pageSizeType;
    private final Integer width;
    private final Integer height;
    private final FileOrientation orientation;
    private final String header;
    private final String footer;
    private final PdfPageMargin margin;
    private final Integer timeout;
    private final String javascript;
    private final String css;
    private final EmulateMediaType emulateMediaType;
    private final Map<String, String> httpHeaders;
    private final Object metadata;
    private final String waitForSelector;
    private final String clickSelector;
    private final ClickSelectorChainSetup clickSelectorChainSetup;
    private final Boolean waitForNetworkIdle;
    private final Boolean enableFormFields;
    private final Integer delay;
    private final Boolean loadImages;
    private final Double scale;
    private final String pageRanges;
    private final Boolean printBackground;
    private final String userAgent;
    private final GeneratePdfAuthentication authentication;
    private final Viewport viewport;

    protected GeneratePdfParams(Builder builder) {
        this.html = builder.html;
        this.url = builder.url;
        this.jsonResponse = builder.jsonResponse;
        this.preSignedUrlExpiresIn = builder.preSignedUrlExpiresIn;
        this.pageSizeType = builder.pageSizeType;
        this.width = builder.width;
        this.height = builder.height;
        this.orientation = builder.orientation;
        this.header = builder.header;
        this.footer = builder.footer;
        this.margin = builder.margin;
        this.timeout = builder.timeout;
        this.javascript = builder.javascript;
        this.css = builder.css;
        this.emulateMediaType = builder.emulateMediaType;
        this.httpHeaders = builder.httpHeaders;
        this.metadata = builder.metadata;
        this.waitForSelector = builder.waitForSelector;
        this.clickSelector = builder.clickSelector;
        this.clickSelectorChainSetup = builder.clickSelectorChainSetup;
        this.waitForNetworkIdle = builder.waitForNetworkIdle;
        this.enableFormFields = builder.enableFormFields;
        this.delay = builder.delay;
        this.loadImages = builder.loadImages;
        this.scale = builder.scale;
        this.pageRanges = builder.pageRanges;
        this.printBackground = builder.printBackground;
        this.userAgent = builder.userAgent;
        this.authentication = builder.authentication;
        this.viewport = builder.viewport;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getHtml() {
        return html;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getJsonResponse() {
        return jsonResponse;
    }

    public boolean isJsonResponse() {
        return Boolean.TRUE.equals(jsonResponse);
    }

    public Long getPreSignedUrlExpiresIn() {
        return preSignedUrlExpiresIn;
    }

    public PageSizeType getPageSizeType() {
        return pageSizeType;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public FileOrientation getOrientation() {
        return orientation;
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

    public PdfPageMargin getMargin() {
        return margin;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public String getJavascript() {
        return javascript;
    }

    public String getCss() {
        return css;
    }

    public EmulateMediaType getEmulateMediaType() {
        return emulateMediaType;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public Object getMetadata() {
        return metadata;
    }

    public String getWaitForSelector() {
        return waitForSelector;
    }

    public String getClickSelector() {
        return clickSelector;
    }

    public ClickSelectorChainSetup getClickSelectorChainSetup() {
        return clickSelectorChainSetup;
    }

    public Boolean getWaitForNetworkIdle() {
        return waitForNetworkIdle;
    }

    public Boolean getEnableFormFields() {
        return enableFormFields;
    }

    public Integer getDelay() {
        return delay;
    }

    public Boolean getLoadImages() {
        return loadImages;
    }

    public Double getScale() {
        return scale;
    }

    public String getPageRanges() {
        return pageRanges;
    }

    public Boolean getPrintBackground() {
        return printBackground;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public GeneratePdfAuthentication getAuthentication() {
        return authentication;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public static final class Builder {
        private String html;
        private String url;
        private Boolean jsonResponse;
        private Long preSignedUrlExpiresIn;
        private PageSizeType pageSizeType;
        private Integer width;
        private Integer height;
        private FileOrientation orientation;
        private String header;
        private String footer;
        private PdfPageMargin margin;
        private Integer timeout;
        private String javascript;
        private String css;
        private EmulateMediaType emulateMediaType;
        private Map<String, String> httpHeaders;
        private Object metadata;
        private String waitForSelector;
        private String clickSelector;
        private ClickSelectorChainSetup clickSelectorChainSetup;
        private Boolean waitForNetworkIdle;
        private Boolean enableFormFields;
        private Integer delay;
        private Boolean loadImages;
        private Double scale;
        private String pageRanges;
        private Boolean printBackground;
        private String userAgent;
        private GeneratePdfAuthentication authentication;
        private Viewport viewport;

        private Builder() {
        }

        public Builder html(String html) {
            this.html = html;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder preSignedUrlExpiresIn(Long preSignedUrlExpiresIn) {
            this.preSignedUrlExpiresIn = preSignedUrlExpiresIn;
            return this;
        }

        public Builder pageSizeType(PageSizeType pageSizeType) {
            this.pageSizeType = pageSizeType;
            return this;
        }

        public Builder width(Integer width) {
            this.width = width;
            return this;
        }

        public Builder height(Integer height) {
            this.height = height;
            return this;
        }

        public Builder orientation(FileOrientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder header(String header) {
            this.header = header;
            return this;
        }

        public Builder footer(String footer) {
            this.footer = footer;
            return this;
        }

        public Builder margin(PdfPageMargin margin) {
            this.margin = margin;
            return this;
        }

        public Builder timeout(Integer timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder javascript(String javascript) {
            this.javascript = javascript;
            return this;
        }

        public Builder css(String css) {
            this.css = css;
            return this;
        }

        public Builder emulateMediaType(EmulateMediaType emulateMediaType) {
            this.emulateMediaType = emulateMediaType;
            return this;
        }

        public Builder httpHeaders(Map<String, String> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public Builder metadata(Object metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder waitForSelector(String waitForSelector) {
            this.waitForSelector = waitForSelector;
            return this;
        }

        public Builder clickSelector(String clickSelector) {
            this.clickSelector = clickSelector;
            return this;
        }

        public Builder clickSelectorChainSetup(ClickSelectorChainSetup clickSelectorChainSetup) {
            this.clickSelectorChainSetup = clickSelectorChainSetup;
            return this;
        }

        public Builder waitForNetworkIdle(Boolean waitForNetworkIdle) {
            this.waitForNetworkIdle = waitForNetworkIdle;
            return this;
        }

        public Builder enableFormFields(Boolean enableFormFields) {
            this.enableFormFields = enableFormFields;
            return this;
        }

        public Builder delay(Integer delay) {
            this.delay = delay;
            return this;
        }

        public Builder loadImages(Boolean loadImages) {
            this.loadImages = loadImages;
            return this;
        }

        public Builder scale(Double scale) {
            this.scale = scale;
            return this;
        }

        public Builder pageRanges(String pageRanges) {
            this.pageRanges = pageRanges;
            return this;
        }

        public Builder printBackground(Boolean printBackground) {
            this.printBackground = printBackground;
            return this;
        }

        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder authentication(GeneratePdfAuthentication authentication) {
            this.authentication = authentication;
            return this;
        }

        public Builder viewport(Viewport viewport) {
            this.viewport = viewport;
            return this;
        }

        public GeneratePdfFileParams buildBytes() {
            this.jsonResponse = false;
            return new GeneratePdfFileParams(this);
        }

        public GeneratePdfJsonParams buildWithJsonResponse() {
            this.jsonResponse = true;
            return new GeneratePdfJsonParams(this);
        }
    }
}
