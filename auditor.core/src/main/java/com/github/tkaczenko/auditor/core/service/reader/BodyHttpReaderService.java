package com.github.tkaczenko.auditor.core.service.reader;

import com.github.tkaczenko.auditor.core.BodyHttpReader;
import com.github.tkaczenko.auditor.core.service.HttpReaderFactory;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service for reading the body of an HTTP request or response. */
@Service
@Slf4j
public class BodyHttpReaderService extends HttpReaderFactory<BodyHttpReader<?>> {

  /**
   * Constructs a new BodyHttpReaderService instance with the provided list of BodyHttpReader
   * implementations.
   *
   * @param bodyReaders the list of BodyHttpReader implementations to be used by this service
   */
  public BodyHttpReaderService(final List<BodyHttpReader<?>> bodyReaders) {
    super(bodyReaders);
  }

  /**
   * Provides a service for reading the body of an HTTP request or response.
   *
   * @param <T> the type of the input object to be read
   * @param input the input object to be read
   * @return the string representation of the input object's body
   */
  public <T> String readToString(T input) {
    return get(input.getClass())
        .map(reader -> ((BodyHttpReader<T>) reader).readToString(input))
        .orElse(null);
  }
}
