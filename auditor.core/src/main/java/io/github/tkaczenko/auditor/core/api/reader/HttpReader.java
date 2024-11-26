package io.github.tkaczenko.auditor.core.api.reader;

/**
 * Provides an interface for reading and processing HTTP data.
 *
 * @param <T> the type of object that the HTTP data will be read from.
 */
public interface HttpReader<T> {
  /**
   * Returns the class that this HttpReader supports for reading.
   *
   * @return the supported class
   */
  Class<T> getSupportedClass();
}
