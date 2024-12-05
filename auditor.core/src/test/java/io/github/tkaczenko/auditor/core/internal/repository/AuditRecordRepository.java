package io.github.tkaczenko.auditor.core.internal.repository;

import io.github.tkaczenko.auditor.core.internal.entity.AuditRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRecordRepository extends AuditRequestResponseRepository<AuditRecord> {}
