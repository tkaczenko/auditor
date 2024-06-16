package com.github.tkaczenko.auditor.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

/**
 * Provides methods to read HTTP headers from a given input of type T.
 *
 * @param <T> the type of the input object
 */
public interface HeadersHttpReader<T> extends HttpReader<T> {

  /**
   * Reads the HTTP headers from the given input of type T.
   *
   * @param input the input object of type T
   * @return a MultiValueMap containing the HTTP headers
   */
  MultiValueMap<String, String> read(T input);

  /**
   * Converts a {@code Map<String, List<String>>} to a {@code MultiValueMap<String, String>}.
   *
   * @param headers the {@code Map<String, List<String>>} to be converted
   * @return a {@code MultiValueMap<String, String>} containing the headers, or null if the input
   *     map is empty
   */
  default MultiValueMap<String, String> getHeaders(final Map<String, List<String>> headers) {
    return headers.isEmpty() ? null : new HttpHeaders(CollectionUtils.toMultiValueMap(headers));
  }

  /**
   * Converts a {@code Map<String, Collection<String>>} to a {@code MultiValueMap<String, String>}.
   *
   * @param stringToCollection the {@code Map<String, Collection<String>>} to be converted
   * @return a {@code MultiValueMap<String, String>} containing the headers
   */
  default MultiValueMap<String, String> getHttpHeaders(
      Map<String, Collection<String>> stringToCollection) {
    return getHeaders(
        stringToCollection.entrySet().stream()
            .collect(
                Collectors.toUnmodifiableMap(
                    Map.Entry::getKey, e -> new ArrayList<>(e.getValue()))));
  }
}
