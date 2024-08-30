package com.github.tkaczenko.auditor.core.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

/**
 * Provides the current date and time for auditing purposes.
 *
 * <p>This class is responsible for retrieving the current date and time from the {@link
 * DateTimeProvider} and returning it as an {@link Optional} of {@link LocalDateTime}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuditDateTimeProvider {

  private final DateTimeProvider dateTimeProvider;

  /**
   * Provides the current date and time for auditing purposes.
   *
   * <p>If the {@link DateTimeProvider} returns an empty value, it will log an error and return an
   * empty {@link Optional}.
   *
   * @return the current date and time
   */
  public Optional<LocalDateTime> fromNow() {
    return dateTimeProvider
        .getNow()
        .map(LocalDateTime::from)
        .or(
            () -> {
              log.error("DateTimeProvider#getNow is empty");
              return Optional.empty();
            });
  }
}
