package io.github.tkaczenko.auditor.core.internal.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AuditDateTimeProviderTest {

  @Test
  void shouldBePresent() {
    AuditDateTimeProvider subject =
        new AuditDateTimeProvider(() -> Optional.of(LocalDateTime.now()));

    assertTrue(subject.fromNow().isPresent());
  }

  @Test
  void shouldBeEmpty() {
    AuditDateTimeProvider subject = new AuditDateTimeProvider(Optional::empty);

    assertTrue(subject.fromNow().isEmpty());
  }
}
