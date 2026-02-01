package com.pdfgate;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Parameters for generating a PDF from raw HTML or a public URL.
 *
 * <p>Provide either {@code html} or {@code url}. Additional options control page size,
 * margins, rendering behavior, media emulation, and advanced wait conditions. Use
 * {@link Builder#buildWithFileResponse()} for raw PDF bytes or
 * {@link Builder#buildWithJsonResponse()} for a {@link PdfGateDocument} response.
 */
public abstract class GeneratePdfParams {
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

  /**
   * Creates a new builder for generate PDF parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the raw HTML content to render.
   */
  public String getHtml() {
    return html;
  }

  /**
   * Returns the public URL to render.
   */
  public String getUrl() {
    return url;
  }

  /**
   * Returns whether the API should return JSON instead of raw bytes.
   */
  public Boolean getJsonResponse() {
    return jsonResponse;
  }

  /**
   * Returns whether the API should return JSON instead of raw bytes.
   */
  public boolean isJsonResponse() {
    return Boolean.TRUE.equals(jsonResponse);
  }

  /**
   * Returns the pre-signed URL expiration time in seconds, if provided.
   */
  public Long getPreSignedUrlExpiresIn() {
    return preSignedUrlExpiresIn;
  }

  /**
   * Returns the requested page size preset.
   */
  public PageSizeType getPageSizeType() {
    return pageSizeType;
  }

  /**
   * Returns the custom page width in pixels, if provided.
   */
  public Integer getWidth() {
    return width;
  }

  /**
   * Returns the custom page height in pixels, if provided.
   */
  public Integer getHeight() {
    return height;
  }

  /**
   * Returns the page orientation, if provided.
   */
  public FileOrientation getOrientation() {
    return orientation;
  }

  /**
   * Returns the HTML header applied to each page, if provided.
   */
  public String getHeader() {
    return header;
  }

  /**
   * Returns the HTML footer applied to each page, if provided.
   */
  public String getFooter() {
    return footer;
  }

  /**
   * Returns the page margins, if provided.
   */
  public PdfPageMargin getMargin() {
    return margin;
  }

  /**
   * Returns the timeout in milliseconds for the render, if provided.
   */
  public Integer getTimeout() {
    return timeout;
  }

  /**
   * Returns JavaScript to run before rendering, if provided.
   */
  public String getJavascript() {
    return javascript;
  }

  /**
   * Returns additional CSS to apply during rendering, if provided.
   */
  public String getCss() {
    return css;
  }

  /**
   * Returns the CSS media type to emulate during rendering, if provided.
   */
  public EmulateMediaType getEmulateMediaType() {
    return emulateMediaType;
  }

  /**
   * Returns custom HTTP headers applied when loading the target URL, if provided.
   */
  public Map<String, String> getHttpHeaders() {
    return httpHeaders;
  }

  /**
   * Returns metadata to attach to the generated document, if provided.
   */
  public Object getMetadata() {
    return metadata;
  }

  /**
   * Returns the selector to wait for before rendering, if provided.
   */
  public String getWaitForSelector() {
    return waitForSelector;
  }

  /**
   * Returns the selector to click before rendering, if provided.
   */
  public String getClickSelector() {
    return clickSelector;
  }

  /**
   * Returns click chain setup used before rendering, if provided.
   */
  public ClickSelectorChainSetup getClickSelectorChainSetup() {
    return clickSelectorChainSetup;
  }

  /**
   * Returns whether to wait for network idle before rendering, if provided.
   */
  public Boolean getWaitForNetworkIdle() {
    return waitForNetworkIdle;
  }

  /**
   * Returns whether interactive form fields should be enabled, if provided.
   */
  public Boolean getEnableFormFields() {
    return enableFormFields;
  }

  /**
   * Returns the delay in milliseconds before rendering, if provided.
   */
  public Integer getDelay() {
    return delay;
  }

  /**
   * Returns whether images must load before rendering, if provided.
   */
  public Boolean getLoadImages() {
    return loadImages;
  }

  /**
   * Returns the scale factor applied during rendering, if provided.
   */
  public Double getScale() {
    return scale;
  }

  /**
   * Returns the page ranges to include, if provided.
   */
  public String getPageRanges() {
    return pageRanges;
  }

  /**
   * Returns whether to print background graphics, if provided.
   */
  public Boolean getPrintBackground() {
    return printBackground;
  }

  /**
   * Returns the custom user agent string, if provided.
   */
  public String getUserAgent() {
    return userAgent;
  }

  /**
   * Returns authentication credentials for protected content, if provided.
   */
  public GeneratePdfAuthentication getAuthentication() {
    return authentication;
  }

  /**
   * Returns the viewport size for rendering, if provided.
   */
  public Viewport getViewport() {
    return viewport;
  }

  /**
   * Supported page sizes for generated PDFs.
   */
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

  /**
   * Orientation options for generated PDFs.
   */
  public enum FileOrientation {
    @SerializedName("portrait")
    PORTRAIT,
    @SerializedName("landscape")
    LANDSCAPE
  }

  /**
   * Media types for CSS emulation during rendering.
   */
  public enum EmulateMediaType {
    @SerializedName("screen")
    SCREEN,
    @SerializedName("print")
    PRINT
  }

  /**
   * Margins to apply to a PDF page.
   */
  public static final class PdfPageMargin {
    private final String top;
    private final String bottom;
    private final String left;
    private final String right;

    /**
     * Creates a margin set for a rendered PDF.
     */
    public PdfPageMargin(String top, String bottom, String left, String right) {
      this.top = top;
      this.bottom = bottom;
      this.left = left;
      this.right = right;
    }

    /**
     * Returns the top margin value.
     */
    public String getTop() {
      return top;
    }

    /**
     * Returns the bottom margin value.
     */
    public String getBottom() {
      return bottom;
    }

    /**
     * Returns the left margin value.
     */
    public String getLeft() {
      return left;
    }

    /**
     * Returns the right margin value.
     */
    public String getRight() {
      return right;
    }
  }

  /**
   * Sequence of selectors to click in order.
   */
  public static final class ClickSelectorChain {
    private final List<String> selectors;

    /**
     * Creates a selector chain.
     */
    public ClickSelectorChain(List<String> selectors) {
      this.selectors = selectors;
    }

    /**
     * Returns the selectors to click in order.
     */
    public List<String> getSelectors() {
      return selectors;
    }
  }

  /**
   * Configuration for click selector chains.
   */
  public static final class ClickSelectorChainSetup {
    private final Boolean ignoreFailingChains;
    private final List<ClickSelectorChain> chains;

    /**
     * Creates a click selector chain setup.
     */
    public ClickSelectorChainSetup(Boolean ignoreFailingChains, List<ClickSelectorChain> chains) {
      this.ignoreFailingChains = ignoreFailingChains;
      this.chains = chains;
    }

    /**
     * Returns whether failing chains are ignored.
     */
    public Boolean getIgnoreFailingChains() {
      return ignoreFailingChains;
    }

    /**
     * Returns the chains to execute, if provided.
     */
    public List<ClickSelectorChain> getChains() {
      return chains;
    }
  }

  /**
   * Authentication credentials for accessing protected web content.
   */
  public static final class GeneratePdfAuthentication {
    private final String username;
    private final String password;

    /**
     * Creates authentication credentials for the render request.
     */
    public GeneratePdfAuthentication(String username, String password) {
      this.username = username;
      this.password = password;
    }

    /**
     * Returns the username.
     */
    public String getUsername() {
      return username;
    }

    /**
     * Returns the password.
     */
    public String getPassword() {
      return password;
    }
  }

  /**
   * Viewport dimensions for rendering.
   */
  public static final class Viewport {
    private final Integer width;
    private final Integer height;

    /**
     * Creates a viewport definition for rendering.
     */
    public Viewport(Integer width, Integer height) {
      this.width = width;
      this.height = height;
    }

    /**
     * Returns the viewport width in pixels.
     */
    public Integer getWidth() {
      return width;
    }

    /**
     * Returns the viewport height in pixels.
     */
    public Integer getHeight() {
      return height;
    }
  }

  /**
   * Builder for {@link GeneratePdfParams}.
   */
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

    /**
     * Sets raw HTML content to render.
     */
    public Builder html(String html) {
      this.html = html;
      return this;
    }

    /**
     * Sets the public URL to render.
     */
    public Builder url(String url) {
      this.url = url;
      return this;
    }

    /**
     * Sets the pre-signed URL expiration time in seconds.
     */
    public Builder preSignedUrlExpiresIn(Long preSignedUrlExpiresIn) {
      this.preSignedUrlExpiresIn = preSignedUrlExpiresIn;
      return this;
    }

    /**
     * Sets the page size preset.
     */
    public Builder pageSizeType(PageSizeType pageSizeType) {
      this.pageSizeType = pageSizeType;
      return this;
    }

    /**
     * Sets the custom page width in pixels.
     */
    public Builder width(Integer width) {
      this.width = width;
      return this;
    }

    /**
     * Sets the custom page height in pixels.
     */
    public Builder height(Integer height) {
      this.height = height;
      return this;
    }

    /**
     * Sets the page orientation.
     */
    public Builder orientation(FileOrientation orientation) {
      this.orientation = orientation;
      return this;
    }

    /**
     * Sets the header HTML to apply on each page.
     */
    public Builder header(String header) {
      this.header = header;
      return this;
    }

    /**
     * Sets the footer HTML to apply on each page.
     */
    public Builder footer(String footer) {
      this.footer = footer;
      return this;
    }

    /**
     * Sets the page margins.
     */
    public Builder margin(PdfPageMargin margin) {
      this.margin = margin;
      return this;
    }

    /**
     * Sets the render timeout in milliseconds.
     */
    public Builder timeout(Integer timeout) {
      this.timeout = timeout;
      return this;
    }

    /**
     * Sets JavaScript to run before rendering.
     */
    public Builder javascript(String javascript) {
      this.javascript = javascript;
      return this;
    }

    /**
     * Sets additional CSS to apply during rendering.
     */
    public Builder css(String css) {
      this.css = css;
      return this;
    }

    /**
     * Sets the CSS media type to emulate.
     */
    public Builder emulateMediaType(EmulateMediaType emulateMediaType) {
      this.emulateMediaType = emulateMediaType;
      return this;
    }

    /**
     * Sets custom HTTP headers used when loading the target URL.
     */
    public Builder httpHeaders(Map<String, String> httpHeaders) {
      this.httpHeaders = httpHeaders;
      return this;
    }

    /**
     * Sets metadata to attach to the generated document.
     */
    public Builder metadata(Object metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets a selector to wait for before rendering.
     */
    public Builder waitForSelector(String waitForSelector) {
      this.waitForSelector = waitForSelector;
      return this;
    }

    /**
     * Sets a selector to click before rendering.
     */
    public Builder clickSelector(String clickSelector) {
      this.clickSelector = clickSelector;
      return this;
    }

    /**
     * Sets the click selector chain setup.
     */
    public Builder clickSelectorChainSetup(ClickSelectorChainSetup clickSelectorChainSetup) {
      this.clickSelectorChainSetup = clickSelectorChainSetup;
      return this;
    }

    /**
     * Sets whether to wait for network idle before rendering.
     */
    public Builder waitForNetworkIdle(Boolean waitForNetworkIdle) {
      this.waitForNetworkIdle = waitForNetworkIdle;
      return this;
    }

    /**
     * Sets whether to include interactive form fields in the output.
     */
    public Builder enableFormFields(Boolean enableFormFields) {
      this.enableFormFields = enableFormFields;
      return this;
    }

    /**
     * Sets the delay in milliseconds before rendering.
     */
    public Builder delay(Integer delay) {
      this.delay = delay;
      return this;
    }

    /**
     * Sets whether to wait for all images to load before rendering.
     */
    public Builder loadImages(Boolean loadImages) {
      this.loadImages = loadImages;
      return this;
    }

    /**
     * Sets the scale factor applied during rendering.
     */
    public Builder scale(Double scale) {
      this.scale = scale;
      return this;
    }

    /**
     * Sets the page ranges to include (for example, "1-5" or "1,3,5").
     */
    public Builder pageRanges(String pageRanges) {
      this.pageRanges = pageRanges;
      return this;
    }

    /**
     * Sets whether to print background graphics.
     */
    public Builder printBackground(Boolean printBackground) {
      this.printBackground = printBackground;
      return this;
    }

    /**
     * Sets a custom user agent string.
     */
    public Builder userAgent(String userAgent) {
      this.userAgent = userAgent;
      return this;
    }

    /**
     * Sets authentication credentials for protected web content.
     */
    public Builder authentication(GeneratePdfAuthentication authentication) {
      this.authentication = authentication;
      return this;
    }

    /**
     * Sets the viewport dimensions for rendering.
     */
    public Builder viewport(Viewport viewport) {
      this.viewport = viewport;
      return this;
    }

    /**
     * Builds parameters that request raw PDF bytes.
     */
    public GeneratePdfFileParams buildWithFileResponse() {
      this.jsonResponse = false;
      return new GeneratePdfFileParams(this);
    }

    /**
     * Builds parameters that request a JSON document response.
     */
    public GeneratePdfJsonParams buildWithJsonResponse() {
      this.jsonResponse = true;
      return new GeneratePdfJsonParams(this);
    }
  }
}
