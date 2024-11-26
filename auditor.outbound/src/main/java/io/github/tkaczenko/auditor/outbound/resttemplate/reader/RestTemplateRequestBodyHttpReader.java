package io.github.tkaczenko.auditor.outbound.resttemplate.reader;

import io.github.tkaczenko.auditor.core.api.reader.body.BodyHttpReader;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;

/***
 * HTTP request body reader from the provided {@code byte[]}
 */
@Component
public class RestTemplateRequestBodyHttpReader implements BodyHttpReader<byte[]> {

  /**
   * Reads the body from the provided {@code byte[]} input.
   *
   * @param input the {@code byte[]} containing the request headers
   * @return a {@link String} containing the request body
   */
  @Override
  public String readToString(final byte[] input) {
    return readBodyToString(input, StandardCharsets.UTF_8);
  }

  @Override
  public Class<byte[]> getSupportedClass() {
    return byte[].class;
  }
}
