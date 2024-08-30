package com.github.tkaczenko.auditor.core.repository;

import com.github.tkaczenko.auditor.core.model.AuditRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRecordRepository extends AuditRequestResponseRepository<AuditRecord> {}
