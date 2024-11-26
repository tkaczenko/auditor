package io.github.tkaczenko.auditor.core.internal.factory.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.github.tkaczenko.auditor.core.api.reader.body.BodyHttpReader;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class BodyReaderServiceTest {

  @Test
  void shouldReturnNull() {
    BodyHttpReaderService subject = new BodyHttpReaderService(Collections.emptyList()) {};

    String actual = subject.readToString("string");

    assertNull(actual);
  }

  @Test
  void shouldReturnValue() {
    var expected = "expected";
    BodyHttpReaderService subject =
        new BodyHttpReaderService(
            List.of(
                new BodyHttpReader<String>() {
                  @Override
                  public String readToString(final String input) {
                    return expected;
                  }

                  @Override
                  public Class<String> getSupportedClass() {
                    return String.class;
                  }
                })) {};

    String actual = subject.readToString("string");

    assertEquals(expected, actual);
  }
}
