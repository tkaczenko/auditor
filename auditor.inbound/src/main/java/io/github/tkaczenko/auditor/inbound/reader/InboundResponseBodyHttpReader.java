package io.github.tkaczenko.auditor.inbound.reader;

import io.github.tkaczenko.auditor.core.BodyHttpReader;
import java.nio.charset.Charset;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

/***
 * HTTP response body reader from the provided {@link ContentCachingResponseWrapper}
 */
@Component
public class InboundResponseBodyHttpReader
    implements BodyHttpReader<ContentCachingResponseWrapper> {

  /**
   * Provides an implementation of {@link BodyHttpReader} that reads the response body from a {@link
   * ContentCachingResponseWrapper} instance.
   */
  @Override
  public String readToString(final ContentCachingResponseWrapper input) {
    byte[] body = input.getContentAsByteArray();
    return readBodyToString(body, Charset.forName(input.getCharacterEncoding()));
  }

  @Override
  public Class<ContentCachingResponseWrapper> getSupportedClass() {
    return ContentCachingResponseWrapper.class;
  }
}
