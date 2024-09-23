package io.github.tkaczenko.auditor.outbound.resttemplate.reader;

import io.github.tkaczenko.auditor.core.HeadersHttpReader;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/***
 * HTTP request headers reader from the provided {@link HttpRequest}
 */
@Component
public class RestTemplateRequestHeadersHttpReader implements HeadersHttpReader<HttpRequest> {

  /**
   * Reads the headers from the provided {@link HttpRequest} input.
   *
   * @param input the {@link HttpRequest} containing the request headers
   * @return a {@link MultiValueMap} containing the request headers
   */
  @Override
  public MultiValueMap<String, String> read(final HttpRequest input) {
    return input.getHeaders();
  }

  @Override
  public Class<HttpRequest> getSupportedClass() {
    return HttpRequest.class;
  }
}
