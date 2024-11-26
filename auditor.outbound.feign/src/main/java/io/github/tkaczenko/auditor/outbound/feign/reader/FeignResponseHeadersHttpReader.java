package io.github.tkaczenko.auditor.outbound.feign.reader;

import feign.Response;
import io.github.tkaczenko.auditor.core.api.reader.headers.HeadersHttpReader;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/** HTTP response headers reader from the provided {@link Response} */
@Component
public class FeignResponseHeadersHttpReader implements HeadersHttpReader<Response> {

  /**
   * Reads the headers from the provided {@link Response} input.
   *
   * @param input the {@link Response} containing the response headers
   * @return a {@link String} containing the response headers
   */
  @Override
  public MultiValueMap<String, String> read(final Response input) {
    return getHttpHeaders(input.headers());
  }

  @Override
  public Class<Response> getSupportedClass() {
    return Response.class;
  }
}
