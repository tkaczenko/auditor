package io.github.tkaczenko.auditor.outbound.feign.reader;

import feign.Request;
import io.github.tkaczenko.auditor.core.HeadersHttpReader;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/** HTTP response headers reader from the provided {@link Request} */
@Component
public class FeignRequestHeadersHttpReader implements HeadersHttpReader<Request> {

  /**
   * Reads the headers from the provided {@link Request} input.
   *
   * @param input the {@link Request} containing the request headers
   * @return a {@link String} containing the request headers
   */
  @Override
  public MultiValueMap<String, String> read(final Request input) {
    return getHttpHeaders(input.headers());
  }

  @Override
  public Class<Request> getSupportedClass() {
    return Request.class;
  }
}
