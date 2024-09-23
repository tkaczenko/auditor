package io.github.tkaczenko.auditor.core.service.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.github.tkaczenko.auditor.core.HeadersHttpReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;

class HeadersReaderServiceTest {
  @Test
  void shouldReturnNull() {
    HeadersHttpReaderService subject = new HeadersHttpReaderService(Collections.emptyList()) {};

    String actual = subject.readToString("string");

    assertNull(actual);
  }

  @Test
  void shouldReturnValue() {
    HeadersHttpReaderService subject =
        new HeadersHttpReaderService(
            List.of(
                new HeadersHttpReader<String>() {
                  @Override
                  public HttpHeaders read(final String input) {
                    return new HttpHeaders(
                        CollectionUtils.toMultiValueMap(
                            Map.of(HttpHeaders.AUTHORIZATION, List.of("Basic"))));
                  }

                  @Override
                  public Class<String> getSupportedClass() {
                    return String.class;
                  }
                })) {};

    String actual = subject.readToString("string");

    assertEquals("{\"Authorization\":[\"Basic\"]}", actual);
  }
}
