package com.github.tkaczenko.auditor.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.github.tkaczenko.auditor.core.model.dto.AuditRequestResponseDto;
import org.junit.jupiter.api.Test;

class AuditRequestResponseContextTest {

  @Test
  void initializeAuditRequestBuilderThreadLocal() {
    AuditRequestResponseDto.AuditRequestResponseDtoBuilder expected =
        AuditRequestContext.initializeAuditRequestBuilderThreadLocal();
    AuditRequestResponseDto.AuditRequestResponseDtoBuilder actual =
        AuditRequestContext.getAuditRequestResponseBuilderThreadLocal();

    assertEquals(expected, actual);
  }

  @Test
  void setAuditRequestBuilderThreadLocal() {
    AuditRequestResponseDto.AuditRequestResponseDtoBuilder expected =
        AuditRequestContext.initializeAuditRequestBuilderThreadLocal();
    expected.url("url");

    AuditRequestContext.setAuditRequestResponseBuilderThreadLocal(expected);

    assertEquals(expected, AuditRequestContext.getAuditRequestResponseBuilderThreadLocal());
  }

  @Test
  void clearAuditRequestBuilderThreadLocal() {
    AuditRequestContext.initializeAuditRequestBuilderThreadLocal();

    AuditRequestContext.clearAuditRequestBuilderThreadLocal();

    assertNull(AuditRequestContext.getAuditRequestResponseBuilderThreadLocal());
  }

  @Test
  void getAuditRequestBuilderThreadLocal() {
    AuditRequestResponseDto.AuditRequestResponseDtoBuilder expected =
        AuditRequestContext.initializeAuditRequestBuilderThreadLocal();

    assertEquals(expected, AuditRequestContext.getAuditRequestResponseBuilderThreadLocal());
  }
}
