package io.github.tkaczenko.auditor.inbound.reader;

import io.github.tkaczenko.auditor.core.HeadersHttpReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.ContentCachingResponseWrapper;

/***
 * HTTP response headers reader from the provided {@link ContentCachingResponseWrapper}
 */
@Component
public class InboundResponseHeadersHttpReader
    implements HeadersHttpReader<ContentCachingResponseWrapper> {

  /**
   * Generates a {@link MultiValueMap} of HTTP headers from the provided {@link
   * ContentCachingResponseWrapper}.
   *
   * @param input the {@link ContentCachingResponseWrapper} containing the HTTP headers to be read
   * @return a {@link MultiValueMap} of the HTTP headers
   */
  @Override
  public MultiValueMap<String, String> read(final ContentCachingResponseWrapper input) {
    Map<String, List<String>> headers = new HashMap<>();
    Collection<String> headerNames = input.getHeaderNames();
    for (String headerName : headerNames) {
      headers.put(headerName, new ArrayList<>(input.getHeaders(headerName)));
    }
    return getHeaders(headers);
  }

  @Override
  public Class<ContentCachingResponseWrapper> getSupportedClass() {
    return ContentCachingResponseWrapper.class;
  }
}
