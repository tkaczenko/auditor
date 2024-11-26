package io.github.tkaczenko.auditor.demo.repository;

import io.github.tkaczenko.auditor.core.internal.repository.AuditRequestResponseRepository;
import io.github.tkaczenko.auditor.demo.entity.AuditRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRecordRepository extends AuditRequestResponseRepository<AuditRecord> {}
