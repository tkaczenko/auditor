package com.github.tkaczenko.auditor.outbound.resttemplate.reader;

import com.github.tkaczenko.auditor.core.BodyHttpReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/***
 * HTTP response body reader from the provided {@link ClientHttpResponse}
 */
@Component
public class RestTemplateResponseBodyHttpReader implements BodyHttpReader<ClientHttpResponse> {

  /**
   * Reads the body from the provided {@link ClientHttpResponse} input.
   *
   * @param input the {@link ClientHttpResponse} containing the request body
   * @return a {@link String} containing the response body
   */
  @Override
  public String readToString(final ClientHttpResponse input) {
    return readBodyToString(getResponseBody(input), StandardCharsets.UTF_8);
  }

  @Override
  public Class<ClientHttpResponse> getSupportedClass() {
    return ClientHttpResponse.class;
  }

  @SneakyThrows
  private InputStream getResponseBody(final ClientHttpResponse response) {
    // In case of HTTP error, try to response.getBody() twice due to switching fromNow inputStream
    // to errorStream
    try {
      return response.getBody();
    } catch (IOException ex) {
      return response.getBody();
    }
  }
}
