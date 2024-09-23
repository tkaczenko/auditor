package io.github.tkaczenko.auditor.core.service.reader;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.tkaczenko.auditor.core.HeadersHttpReader;
import io.github.tkaczenko.auditor.core.service.HttpReaderFactory;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service for reading the headers of an HTTP request or response. */
@Service
@Slf4j
public class HeadersHttpReaderService extends HttpReaderFactory<HeadersHttpReader<?>> {

  private static final ObjectWriter WRITER;
  private static final ObjectMapper MAPPER;

  static {
    MAPPER = new ObjectMapper();
    MAPPER.findAndRegisterModules();
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    WRITER = MAPPER.writer();
  }

  /**
   * Constructs a new HeadersHttpReaderService instance with the provided list of HeadersHttpReader
   * implementations.
   *
   * @param headersReaders the list of HeadersHttpReader implementations to be used by this service
   */
  public HeadersHttpReaderService(final List<HeadersHttpReader<?>> headersReaders) {
    super(headersReaders);
  }

  /**
   * Provides a service for reading HTTP headers and converting it to a string.
   *
   * @param input the input object
   * @param <T> the type of the input object
   * @return the HTTP headers as a string, or null if the read operation fails
   */
  public <T> String readToString(T input) {
    return get(input.getClass())
        .map(reader -> ((HeadersHttpReader<T>) reader).read(input))
        .map(this::write)
        .orElse(null);
  }

  @SneakyThrows
  private String write(final Object value) {
    return value == null ? null : WRITER.writeValueAsString(value);
  }
}
