package io.github.tkaczenko.auditor.demo.cleanup;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.tkaczenko.auditor.demo.repository.AuditRecordRepository;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@DirtiesContext
@Transactional
@ActiveProfiles("test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"auditor.scheduling.cron=*/5 * * * * *"})
@Slf4j
class CleanupIntegrationTests {

  @Autowired private AuditRecordRepository auditRecordRepository;

  @Test
  @SneakyThrows
  @Sql(
      value = "/CleanupIntegrationTests/success.sql",
      executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @DisplayName("when cron comes -> should clean up audit data")
  void whenInboundRequestComesShouldSaveAuditRecordAndReturn200() {
    assertThat(auditRecordRepository.count()).isPositive();
    Awaitility.await()
        .atMost(10, TimeUnit.SECONDS)
        .untilAsserted(() -> assertThat(auditRecordRepository.count()).isZero());
  }
}
