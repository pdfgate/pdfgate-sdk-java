package com.pdfgate;

/**
 * Parameters for listing stored recipients by email.
 */
public final class ListRecipientsParams {
  /**
   * Email address to look up (case-insensitive).
   */
  private final String email;

  private ListRecipientsParams(Builder builder) {
    this.email = builder.email;
  }

  /**
   * Creates a new builder for list recipients parameters.
   *
   * @return the builder for list recipients parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the email address to look up.
   *
   * @return the email address to look up.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Builder for {@link ListRecipientsParams}.
   */
  public static final class Builder {
    /**
     * Email address to look up (case-insensitive).
     */
    private String email;

    /**
     * Creates a builder for list recipients parameters.
     */
    public Builder() {
    }

    /**
     * Sets the email address to look up. The lookup is case-insensitive.
     *
     * @param email the email address to look up.
     * @return this builder.
     */
    public Builder email(String email) {
      this.email = email;
      return this;
    }

    /**
     * Builds the list recipients parameters.
     *
     * @return the list recipients parameters.
     */
    public ListRecipientsParams build() {
      return new ListRecipientsParams(this);
    }
  }
}
