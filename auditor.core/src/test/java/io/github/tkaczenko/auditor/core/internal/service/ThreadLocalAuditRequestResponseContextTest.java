package io.github.tkaczenko.auditor.core.internal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.github.tkaczenko.auditor.core.internal.dto.AuditRequestResponseDto;
import org.junit.jupiter.api.Test;

class ThreadLocalAuditRequestResponseContextTest {

  @Test
  void initializeAuditRequestBuilderThreadLocal() {
    AuditRequestResponseDto.AuditRequestResponseDtoBuilder expected =
        ThreadLocalAuditRequestResponseContext.initializeAuditRequestBuilderThreadLocal();
    AuditRequestResponseDto.AuditRequestResponseDtoBuilder actual =
        ThreadLocalAuditRequestResponseContext.getAuditRequestResponseBuilderThreadLocal();

    assertEquals(expected, actual);
  }

  @Test
  void setAuditRequestBuilderThreadLocal() {
    AuditRequestResponseDto.AuditRequestResponseDtoBuilder expected =
        ThreadLocalAuditRequestResponseContext.initializeAuditRequestBuilderThreadLocal();
    expected.url("url");

    ThreadLocalAuditRequestResponseContext.setAuditRequestResponseBuilderThreadLocal(expected);

    assertEquals(
        expected,
        ThreadLocalAuditRequestResponseContext.getAuditRequestResponseBuilderThreadLocal());
  }

  @Test
  void clearAuditRequestBuilderThreadLocal() {
    ThreadLocalAuditRequestResponseContext.initializeAuditRequestBuilderThreadLocal();

    ThreadLocalAuditRequestResponseContext.clearAuditRequestBuilderThreadLocal();

    assertNull(ThreadLocalAuditRequestResponseContext.getAuditRequestResponseBuilderThreadLocal());
  }

  @Test
  void getAuditRequestBuilderThreadLocal() {
    AuditRequestResponseDto.AuditRequestResponseDtoBuilder expected =
        ThreadLocalAuditRequestResponseContext.initializeAuditRequestBuilderThreadLocal();

    assertEquals(
        expected,
        ThreadLocalAuditRequestResponseContext.getAuditRequestResponseBuilderThreadLocal());
  }
}
