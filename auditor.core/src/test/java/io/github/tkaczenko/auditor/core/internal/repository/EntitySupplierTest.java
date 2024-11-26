package io.github.tkaczenko.auditor.core.internal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.tkaczenko.auditor.core.api.Auditable;
import io.github.tkaczenko.auditor.core.internal.entity.AuditRecord;
import io.github.tkaczenko.auditor.core.internal.entity.AuditRequestResponse;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@DataJpaTest(
    includeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = {AuditRecordRepository.class})
    })
class EntitySupplierTest {

  @Autowired private AuditRecordRepository auditRecordRepository;

  @Autowired
  private AuditRequestResponseRepository<? extends AuditRequestResponse>
      auditRequestResponseRepository;

  @Test
  void createNewEntity() {
    AuditRecord newEntity = auditRecordRepository.createNewEntity();
    AuditRequestResponse newEntity1 = auditRequestResponseRepository.createNewEntity();

    assertNotNull(newEntity);
    assertEquals(AuditRecord.class, newEntity.getClass());
    assertNotNull(newEntity1);
    assertEquals(AuditRecord.class, newEntity1.getClass());
  }

  @Configuration
  @ComponentScan(basePackageClasses = {Auditable.class})
  @EntityScan(basePackageClasses = {AuditRequestResponse.class})
  @EnableJpaRepositories(basePackageClasses = {AuditRequestResponseRepository.class})
  static class TestConfig {

    @Component
    public class FixedCurrentTimeProvider implements DateTimeProvider {

      private static final LocalDateTime DEFAULT_PRETEND_DATE_TIME =
          LocalDateTime.of(2024, 6, 13, 17, 0);

      private static final LocalDateTime pretendTime = DEFAULT_PRETEND_DATE_TIME;

      public LocalDateTime getLocalDateTime() {
        return pretendTime;
      }

      @Override
      public Optional<TemporalAccessor> getNow() {
        return Optional.of(getLocalDateTime());
      }
    }
  }
}
