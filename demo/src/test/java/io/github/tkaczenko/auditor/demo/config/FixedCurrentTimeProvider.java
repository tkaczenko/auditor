package io.github.tkaczenko.auditor.demo.config;

import io.github.tkaczenko.auditor.demo.service.CurrentTimeProvider;
import java.time.LocalDateTime;

public class FixedCurrentTimeProvider extends CurrentTimeProvider {

  private static final LocalDateTime DEFAULT_PRETEND_DATE_TIME =
      LocalDateTime.of(2024, 6, 13, 17, 0);

  private LocalDateTime pretendTime = DEFAULT_PRETEND_DATE_TIME;

  @Override
  public LocalDateTime getLocalDateTime() {
    return pretendTime;
  }
}
