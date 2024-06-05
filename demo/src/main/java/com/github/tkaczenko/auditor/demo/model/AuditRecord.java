package com.github.tkaczenko.auditor.demo.model;

import com.github.tkaczenko.auditor.core.model.AuditRequestResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "demo_audit_record", schema = "demo")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class AuditRecord extends AuditRequestResponse {

  @Column(name = "transaction_id", updatable = false)
  private String transactionId;
}
