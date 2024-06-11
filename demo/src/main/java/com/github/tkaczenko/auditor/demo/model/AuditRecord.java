package com.github.tkaczenko.auditor.demo.model;

import com.github.tkaczenko.auditor.core.model.AuditRequestResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "demo_audit_record", schema = "demo")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class AuditRecord extends AuditRequestResponse {

  @Column(name = "transaction_id", updatable = false)
  private String transactionId;
}
