package com.github.tkaczenko.auditor.outbound.feign.reader;

import com.github.tkaczenko.auditor.core.BodyHttpReader;
import feign.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * HTTP response body reader from the provided {@link Response}
 */
@Component
public class FeignResponseBodyHttpReader implements BodyHttpReader<Response> {

  /**
   * Reads the body from the provided {@link Response} input.
   *
   * @param input the {@link Response} containing the response body
   * @return a {@link String} containing the response body
   */
  @Override
  public String readToString(final Response input) {
    try {
      return Optional.ofNullable(input)
          .map(Response::body)
          .map(this::asInputStream)
          .map(inputStream -> readBodyToString(inputStream, input.charset()))
          .orElse(null);
    } catch (UncheckedIOException e) {
      return null;
    }
  }

  @Override
  public Class<Response> getSupportedClass() {
    return Response.class;
  }

  private InputStream asInputStream(Response.Body body) {
    try {
      return body.asInputStream();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
