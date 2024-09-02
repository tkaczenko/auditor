package com.github.tkaczenko.auditor.cleanup.service;

import com.github.tkaczenko.auditor.core.model.AuditRequestResponse;
import com.github.tkaczenko.auditor.core.repository.AuditRequestResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** This class is responsible for scheduling the cleanup of audit data from the database. */
@Component
@Slf4j
@RequiredArgsConstructor
public class CleanupScheduler {

  private final AuditRequestResponseRepository<? extends AuditRequestResponse>
      auditRequestResponseRepository;

  /**
   * This method is responsible for deleting all audit data from the database on a scheduled basis.
   */
  @Scheduled(cron = "${auditor.scheduling.cron:-}")
  @SchedulerLock(name = "cleanupAuditTask", lockAtLeastFor = "5m", lockAtMostFor = "10m")
  public void cleanupAuditCron() {
    auditRequestResponseRepository.deleteAll();
    if (log.isInfoEnabled()) {
      log.info("Deleted all audit data by cron");
    }
  }
}
