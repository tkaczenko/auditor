package io.github.tkaczenko.auditor.demo.model;

import io.github.tkaczenko.auditor.core.model.AuditRequestResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "demo_audit_record", schema = "demo")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditRecord extends AuditRequestResponse {

  @Column(name = "transaction_id", updatable = false)
  private String transactionId;

  @Column(name = "trace_id", updatable = false)
  private String traceId;
}
