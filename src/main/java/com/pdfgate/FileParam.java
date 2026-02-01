package com.pdfgate;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a binary file payload for multipart PDF uploads.
 */
public final class FileParam {
  private final String name;
  private final byte[] data;
  private final String type;

  public FileParam(String name, byte[] data) {
    this(name, data, null);
  }

  public FileParam(String name, byte[] data, String type) {
    this.name = Objects.requireNonNull(name, "name");
    this.data = Objects.requireNonNull(data, "data");
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public byte[] getData() {
    return data;
  }

  public String getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileParam fileParam = (FileParam) o;
    return Objects.equals(name, fileParam.name)
        && Arrays.equals(data, fileParam.data)
        && Objects.equals(type, fileParam.type);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(name, type);
    result = 31 * result + Arrays.hashCode(data);
    return result;
  }
}
