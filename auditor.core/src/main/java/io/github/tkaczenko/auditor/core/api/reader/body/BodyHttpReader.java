package io.github.tkaczenko.auditor.core.api.reader.body;

import io.github.tkaczenko.auditor.core.api.reader.HttpReader;
import java.io.InputStream;
import java.nio.charset.Charset;
import lombok.SneakyThrows;
import org.springframework.util.FileCopyUtils;

/**
 * Provides a contract for reading the HTTP body and converting it to a {@link String}.
 *
 * @param <T> the type of the request body
 */
public interface BodyHttpReader<T> extends HttpReader<T> {
  /**
   * Reads the request body and returns it as a string.
   *
   * @param input the input to read from
   * @return the request body as a string
   */
  String readToString(T input);

  /**
   * Reads the request body from a byte array and returns it as a string.
   *
   * @param body the byte array containing the request body
   * @param charset the character set to use for decoding the request body
   * @return the request body as a string, or null if the body is empty
   */
  default String readBodyToString(byte[] body, Charset charset) {
    return body.length > 0 ? new String(body, charset) : null;
  }

  /**
   * Reads the request body from an input stream and returns it as a string.
   *
   * @param inputStream the input stream containing the request body
   * @param charset the character set to use for decoding the request body
   * @return the request body as a string
   */
  @SneakyThrows
  default String readBodyToString(InputStream inputStream, Charset charset) {
    return readBodyToString(FileCopyUtils.copyToByteArray(inputStream), charset);
  }
}
