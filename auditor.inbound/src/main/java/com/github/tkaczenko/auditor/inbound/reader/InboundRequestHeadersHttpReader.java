package com.github.tkaczenko.auditor.inbound.reader;

import com.github.tkaczenko.auditor.core.HeadersHttpReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.ContentCachingRequestWrapper;

/***
 * HTTP request headers reader from the provided {@link ContentCachingRequestWrapper}
 */
@Component
public class InboundRequestHeadersHttpReader
    implements HeadersHttpReader<ContentCachingRequestWrapper> {

  /**
   * Reads the headers from the provided {@link ContentCachingRequestWrapper} input.
   *
   * @param input the {@link ContentCachingRequestWrapper} containing the request headers
   * @return a {@link MultiValueMap} containing the request headers
   */
  @Override
  public MultiValueMap<String, String> read(final ContentCachingRequestWrapper input) {
    Map<String, List<String>> headers = new HashMap<>();
    List<String> headerNames = Collections.list(input.getHeaderNames());
    for (String headerName : headerNames) {
      headers.put(headerName, Collections.list(input.getHeaders(headerName)));
    }
    return getHeaders(headers);
  }

  @Override
  public Class<ContentCachingRequestWrapper> getSupportedClass() {
    return ContentCachingRequestWrapper.class;
  }
}
