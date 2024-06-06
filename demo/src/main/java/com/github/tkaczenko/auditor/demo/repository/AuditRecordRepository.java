package com.github.tkaczenko.auditor.demo.repository;

import com.github.tkaczenko.auditor.core.repository.AuditRequestResponseRepository;
import com.github.tkaczenko.auditor.demo.model.AuditRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRecordRepository extends AuditRequestResponseRepository<AuditRecord> {}
