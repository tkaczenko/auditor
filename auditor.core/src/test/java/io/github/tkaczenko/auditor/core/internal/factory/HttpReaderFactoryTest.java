package io.github.tkaczenko.auditor.core.internal.factory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.tkaczenko.auditor.core.api.reader.HttpReader;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class HttpReaderFactoryTest {

  @Test
  void shouldBeEmpty() {
    var subject = new HttpReaderFactory<HttpReader<Object>>(Collections.emptyList()) {};

    assertTrue(subject.get(String.class).isEmpty());
  }

  @Test
  void shouldBePresent() {
    var subject = new HttpReaderFactory<HttpReader<String>>(List.of(() -> String.class)) {};

    assertTrue(subject.get(String.class).isPresent());
  }
}
