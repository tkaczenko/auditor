package io.github.tkaczenko.auditor.core.repository;

import io.github.tkaczenko.auditor.core.model.AuditRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRecordRepository extends AuditRequestResponseRepository<AuditRecord> {}
