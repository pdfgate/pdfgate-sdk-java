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
        && Objects.equals(checked, that.checked);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, value, checked);
  }
}
