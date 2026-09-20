package com.pdfgate;

/**
 * Recipient parameters for a document inside a create envelope request.
 *
 * <p>A recipient is identified either by {@code email} and {@code name} or by the
 * {@code recipientId} of a stored recipient — never both.
 */
public final class EnvelopeRecipient {
  private final String email;
  private final String name;
  private final String recipientId;
  private final Boolean embedded;
  private final String role;
  private final Integer reminderIntervalDays;
  private final Integer reminderAttempts;
  private final Integer signingOrder;

  /**
   * Initializes envelope recipient parameters from the builder.
   *
   * @param builder builder with configured values.
   */
  private EnvelopeRecipient(Builder builder) {
    this.email = builder.email;
    this.name = builder.name;
    this.recipientId = builder.recipientId;
    this.embedded = builder.embedded;
    this.role = builder.role;
    this.reminderIntervalDays = builder.reminderIntervalDays;
    this.reminderAttempts = builder.reminderAttempts;
    this.signingOrder = builder.signingOrder;
  }

  /**
   * Creates a new builder for envelope recipient parameters.
   *
   * @return the builder for envelope recipient parameters.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the recipient email address.
   *
   * @return the recipient email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns the recipient display name.
   *
   * @return the recipient display name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the id of the stored recipient to reuse, if present.
   *
   * @return the id of the stored recipient to reuse, if present.
   */
  public String getRecipientId() {
    return recipientId;
  }

  /**
   * Returns whether the recipient signs through an embed link, if present.
   *
   * @return whether the recipient signs through an embed link, if present.
   */
  public Boolean getEmbedded() {
    return embedded;
  }

  /**
   * Returns the recipient role, if present.
   *
   * @return the recipient role, if present.
   */
  public String getRole() {
    return role;
  }

  /**
   * Returns the number of days between signing reminders, if present.
   *
   * @return the reminder interval in days, if present.
   */
  public Integer getReminderIntervalDays() {
    return reminderIntervalDays;
  }

  /**
   * Returns the maximum number of reminder attempts, if present.
   *
   * @return the maximum number of reminder attempts, if present.
   */
  public Integer getReminderAttempts() {
    return reminderAttempts;
  }

  /**
   * Returns the signing order of the recipient, if present.
   *
   * @return the signing order of the recipient, if present.
   */
  public Integer getSigningOrder() {
    return signingOrder;
  }

  /**
   * Builder for {@link EnvelopeRecipient}.
   */
  public static final class Builder {
    private String email;
    private String name;
    private String recipientId;
    private Boolean embedded;
    private String role;
    private Integer reminderIntervalDays;
    private Integer reminderAttempts;
    private Integer signingOrder;

    private Builder() {
    }

    /**
     * Sets the recipient email address. Not allowed together with
     * {@link #recipientId(String)}.
     *
     * @param email recipient email address.
     * @return this builder.
     */
    public Builder email(String email) {
      this.email = email;
      return this;
    }

    /**
     * Sets the recipient display name. Not allowed together with
     * {@link #recipientId(String)}.
     *
     * @param name recipient display name.
     * @return this builder.
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the id of a stored recipient to reuse. Not allowed together with
     * {@link #email(String)} or {@link #name(String)}.
     *
     * @param recipientId id of the stored recipient to reuse.
     * @return this builder.
     */
    public Builder recipientId(String recipientId) {
      this.recipientId = recipientId;
      return this;
    }

    /**
     * Sets whether the recipient signs through an embed link. Embedded recipients
     * sign inside your own application and receive no emails from PDFGate; create
     * their signing links with {@link PdfGate#createEmbedLink(CreateEmbedLinkParams)}
     * after sending the envelope.
     *
     * @param embedded whether the recipient signs through an embed link.
     * @return this builder.
     */
    public Builder embedded(Boolean embedded) {
      this.embedded = embedded;
      return this;
    }

    /**
     * Sets the recipient role.
     *
     * @param role recipient role.
     * @return this builder.
     */
    public Builder role(String role) {
      this.role = role;
      return this;
    }

    /**
     * Sets the number of days between signing reminders.
     *
     * @param reminderIntervalDays reminder interval in days.
     * @return this builder.
     */
    public Builder reminderIntervalDays(Integer reminderIntervalDays) {
      this.reminderIntervalDays = reminderIntervalDays;
      return this;
    }

    /**
     * Sets the maximum number of reminder attempts.
     *
     * @param reminderAttempts maximum number of reminder attempts.
     * @return this builder.
     */
    public Builder reminderAttempts(Integer reminderAttempts) {
      this.reminderAttempts = reminderAttempts;
      return this;
    }

    /**
     * Sets the signing order of the recipient, starting from 1. Recipients sign one
     * after another in this order and a recipient is activated once everyone with a
     * lower value has signed. Recipients with the same value can sign in parallel.
     * Provide it for every recipient of a document or for none. Omitted, all
     * recipients can sign immediately.
     *
     * @param signingOrder signing order of the recipient, starting from 1.
     * @return this builder.
     */
    public Builder signingOrder(Integer signingOrder) {
      this.signingOrder = signingOrder;
      return this;
    }

    /**
     * Builds the envelope recipient parameters.
     *
     * @return the envelope recipient parameters.
     */
    public EnvelopeRecipient build() {
      return new EnvelopeRecipient(this);
    }
  }
}
