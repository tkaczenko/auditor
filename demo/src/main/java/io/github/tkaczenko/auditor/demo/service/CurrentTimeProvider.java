package io.github.tkaczenko.auditor.demo.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;
import java.util.TimeZone;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

@Component
public class CurrentTimeProvider implements DateTimeProvider {

  public static final ZoneId CST = TimeZone.getTimeZone("America/Chicago").toZoneId();

  public CurrentTimeProvider() {
    TimeZone.setDefault(TimeZone.getTimeZone(CST));
  }

  public LocalDateTime getLocalDateTime() {
    return LocalDateTime.now();
  }

  @Override
  public Optional<TemporalAccessor> getNow() {
    return Optional.of(getLocalDateTime());
  }
}
