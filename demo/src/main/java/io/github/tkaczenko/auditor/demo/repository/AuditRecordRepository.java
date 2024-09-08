package io.github.tkaczenko.auditor.demo.repository;

import io.github.tkaczenko.auditor.core.repository.AuditRequestResponseRepository;
import io.github.tkaczenko.auditor.demo.model.AuditRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRecordRepository extends AuditRequestResponseRepository<AuditRecord> {}
