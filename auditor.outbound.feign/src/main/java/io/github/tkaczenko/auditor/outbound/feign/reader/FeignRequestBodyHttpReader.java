package io.github.tkaczenko.auditor.outbound.feign.reader;

import feign.Request;
import io.github.tkaczenko.auditor.core.BodyHttpReader;
import org.springframework.stereotype.Component;

/** HTTP request body reader from the provided {@link Request} */
@Component
public class FeignRequestBodyHttpReader implements BodyHttpReader<Request> {

  /**
   * Reads the body from the provided {@link Request} input.
   *
   * @param input the {@link Request} containing the request body
   * @return a {@link String} containing the request body
   */
  @Override
  public String readToString(final Request input) {
    return input.charset() == null ? null : new String(input.body(), input.charset());
  }

  @Override
  public Class<Request> getSupportedClass() {
    return Request.class;
  }
}
