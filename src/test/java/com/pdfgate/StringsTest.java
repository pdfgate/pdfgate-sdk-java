package com.pdfgate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringsTest {
  @Test
  public void isBlankMatchesExpectedJava8Behavior() {
    Assertions.assertTrue(Strings.isBlank(null), "null should be blank");
    Assertions.assertTrue(Strings.isBlank(""), "empty string should be blank");
    Assertions.assertTrue(Strings.isBlank("   \t  "), "whitespace-only string should be blank");
    Assertions.assertFalse(Strings.isBlank("pdfgate"), "non-empty string should not be blank");
  }
}
