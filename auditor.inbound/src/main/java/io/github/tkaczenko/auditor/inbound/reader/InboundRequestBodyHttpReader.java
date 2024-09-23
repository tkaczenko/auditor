package io.github.tkaczenko.auditor.inbound.reader;

import io.github.tkaczenko.auditor.core.BodyHttpReader;
import java.nio.charset.Charset;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

/***
 * HTTP request body reader from the provided {@link ContentCachingRequestWrapper}
 */
@Component
public class InboundRequestBodyHttpReader implements BodyHttpReader<ContentCachingRequestWrapper> {

  /**
   * Reads the request body from the provided {@link ContentCachingRequestWrapper} and returns it as
   * a string.
   *
   * @param input the {@link ContentCachingRequestWrapper} containing the request body
   * @return the request body as a string
   */
  @Override
  public String readToString(final ContentCachingRequestWrapper input) {
    byte[] body = input.getContentAsByteArray();
    return readBodyToString(body, Charset.forName(input.getCharacterEncoding()));
  }

  @Override
  public Class<ContentCachingRequestWrapper> getSupportedClass() {
    return ContentCachingRequestWrapper.class;
  }
}
