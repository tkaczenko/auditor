package io.github.tkaczenko.auditor.outbound.resttemplate.reader;

import io.github.tkaczenko.auditor.core.api.reader.headers.HeadersHttpReader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/***
 * HTTP response headers reader from the provided {@link ClientHttpResponse}
 */
@Component
public class RestTemplateResponseHeadersHttpReader
    implements HeadersHttpReader<ClientHttpResponse> {

  /**
   * Reads the headers from the provided {@link ClientHttpResponse} input.
   *
   * @param input the {@link ClientHttpResponse} containing the response headers
   * @return a {@link HttpHeaders} containing the response headers
   */
  @Override
  public HttpHeaders read(final ClientHttpResponse input) {
    return input.getHeaders();
  }

  @Override
  public Class<ClientHttpResponse> getSupportedClass() {
    return ClientHttpResponse.class;
  }
}
