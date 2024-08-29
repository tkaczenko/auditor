package com.github.tkaczenko.auditor.core.service;

import com.github.tkaczenko.auditor.core.HttpReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides a factory for creating {@link HttpReader} instances based on the class of the object to
 * be read. The factory maintains a map of {@link HttpReader} implementations and their supported
 * classes, allowing it to retrieve the appropriate reader for a given class.
 *
 * @param <T> the type of {@link HttpReader} instances created by this factory
 */
@Slf4j
@RequiredArgsConstructor
public abstract class HttpReaderFactory<T extends HttpReader<?>> {

  private final Map<Class<?>, T> classToReader;

  /**
   * Constructs a new HttpReaderFactory instance with the provided list of HttpReader
   * implementations.
   *
   * @param readers the list of HttpReader implementations to be used by this service
   */
  protected HttpReaderFactory(final List<T> readers) {
    this.classToReader =
        readers.stream()
            .collect(
                Collectors.toUnmodifiableMap(HttpReader::getSupportedClass, Function.identity()));
    if (log.isDebugEnabled()) {
      log.debug("Http Readers: size {}, {}", readers.size(), readers);
    }
  }

  /**
   * Returns the {@link HttpReader} instance that can handle the given class.
   *
   * @param clazz the class for which to retrieve the appropriate {@link HttpReader}
   * @return an {@link Optional} containing the {@link HttpReader} instance, or an empty {@link
   *     Optional} if no suitable reader is found
   */
  public Optional<T> get(Class<?> clazz) {
    T readerOrDefault = classToReader.getOrDefault(clazz, getDefaultValue(clazz));
    if (readerOrDefault == null) {
      log.error("No reader found for class {}", clazz);
      return Optional.empty();
    }
    return Optional.of(readerOrDefault);
  }

  /**
   * Returns the default {@link HttpReader} instance that can handle the given class if no specific
   * reader is found in the map.
   *
   * @param clazz the class for which to retrieve the default {@link HttpReader}
   * @return the default {@link HttpReader} instance, or null if no suitable default is found
   */
  private T getDefaultValue(Class<?> clazz) {
    return classToReader.entrySet().stream()
        .filter(e -> e.getKey().isAssignableFrom(clazz))
        .findAny()
        .map(Map.Entry::getValue)
        .orElse(null);
  }
}
