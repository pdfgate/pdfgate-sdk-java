package com.pdfgate;

import java.util.Objects;
import java.util.Optional;

/**
 * Field metadata returned for an envelope recipient.
 */
public final class EnvelopeFieldResponse {
  private String name;
  private DocumentFieldType type;
  private Object value;
  private Boolean checked;
  private String timezone;
  private String source;
  private String userValue;
  private String userTimezone;

  /**
   * Creates an empty field response for JSON deserialization.
   */
  public EnvelopeFieldResponse() {
  }

  /**
   * Returns the field name.
   *
   * @return the field name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the field type.
   *
   * @return the field type.
   */
  public DocumentFieldType getType() {
    return type;
  }

  /**
   * Returns the field value, if present.
   *
   * @return the field value, if present.
   */
  public Optional<Object> getValue() {
    return Optional.ofNullable(value);
  }

  /**
   * Returns whether the field is checked, if present.
   *
   * @return whether the field is checked, if present.
   */
  public Optional<Boolean> getChecked() {
    return Optional.ofNullable(checked);
  }

  /**
   * Returns the IANA timezone identifier for the stored value, if present.
   *
   * <p>For {@code datetime} fields the value is normalized to UTC, so this is
   * {@code "UTC"} once a value is captured.
   *
   * @return the timezone of the stored value, if present.
   */
  public Optional<String> getTimezone() {
    return Optional.ofNullable(timezone);
  }

  /**
   * Returns where the value originated, if present.
   *
   * <p>{@code "server"} for auto-filled fields or {@code "user"} for values
   * submitted by the recipient.
   *
   * @return the source of the value, if present.
   */
  public Optional<String> getSource() {
    return Optional.ofNullable(source);
  }

  /**
   * Returns the original value as submitted by the recipient, if present.
   *
   * <p>Populated for {@code datetime} fields before UTC normalization.
   *
   * @return the user-submitted value, if present.
   */
  public Optional<String> getUserValue() {
    return Optional.ofNullable(userValue);
  }

  /**
   * Returns the timezone the recipient submitted the value in, if present.
   *
   * <p>Populated for {@code datetime} fields.
   *
   * @return the user-submitted timezone, if present.
   */
  public Optional<String> getUserTimezone() {
    return Optional.ofNullable(userTimezone);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EnvelopeFieldResponse that = (EnvelopeFieldResponse) o;
    return Objects.equals(name, that.name)
        && type == that.type
        && Objects.equals(value, that.value)
        && Objects.equals(checked, that.checked)
        && Objects.equals(timezone, that.timezone)
        && Objects.equals(source, that.source)
        && Objects.equals(userValue, that.userValue)
        && Objects.equals(userTimezone, that.userTimezone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, value, checked, timezone, source, userValue, userTimezone);
  }
}
