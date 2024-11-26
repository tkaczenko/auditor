package io.github.tkaczenko.auditor.core.internal.service;

import io.github.tkaczenko.auditor.core.internal.dto.AuditRequestResponseDto;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides a context for managing the audit request response. This class is responsible for
 * initializing, setting, and clearing the thread-local {@link
 * AuditRequestResponseDto.AuditRequestResponseDtoBuilder} instance.
 *
 * <p>The {@link ThreadLocalAuditRequestResponseContext} class serves as a utility for managing the
 * lifecycle of the {@link AuditRequestResponseDto.AuditRequestResponseDtoBuilder} instance. It
 * ensures that the builder is properly initialized, set, and cleared within the current thread's
 * context.
 *
 * <p>This class follows the Utility Class pattern, as it contains only static methods and does not
 * require instantiation. It provides a centralized and thread-safe way to work with the
 * AuditRequestResponseDto builder.
 */
@Slf4j
@UtilityClass
public class ThreadLocalAuditRequestResponseContext {

  private static final ThreadLocal<AuditRequestResponseDto.AuditRequestResponseDtoBuilder>
      auditRequestResponseBuilderThreadLocal = new ThreadLocal<>();

  /**
   * Initializes the thread-local {@link AuditRequestResponseDto.AuditRequestResponseDtoBuilder}
   * instance and returns it.
   *
   * @return the initialized AuditRequestResponseDto.AuditRequestResponseDtoBuilder instance
   */
  public static AuditRequestResponseDto.AuditRequestResponseDtoBuilder
      initializeAuditRequestBuilderThreadLocal() {
    var auditRequestResponseBuilder = AuditRequestResponseDto.builder();

    auditRequestResponseBuilderThreadLocal.set(auditRequestResponseBuilder);
    if (log.isDebugEnabled()) {
      log.debug("Initialized: {}", auditRequestResponseBuilder);
    }

    return auditRequestResponseBuilder;
  }

  /**
   * Clears the thread-local {@link AuditRequestResponseDto.AuditRequestResponseDtoBuilder}
   * instance.
   */
  public static void clearAuditRequestBuilderThreadLocal() {
    auditRequestResponseBuilderThreadLocal.remove();
    if (log.isDebugEnabled()) {
      log.debug("Removed");
    }
  }

  /**
   * Retrieves the thread-local {@link AuditRequestResponseDto.AuditRequestResponseDtoBuilder}
   * instance.
   *
   * @return the AuditRequestResponseDto.AuditRequestResponseDtoBuilder instance
   */
  public static AuditRequestResponseDto.AuditRequestResponseDtoBuilder
      getAuditRequestResponseBuilderThreadLocal() {
    return auditRequestResponseBuilderThreadLocal.get();
  }

  /**
   * Sets the thread-local {@link AuditRequestResponseDto.AuditRequestResponseDtoBuilder} instance.
   *
   * @param builder the AuditRequestResponseDto.AuditRequestResponseDtoBuilder instance to set
   */
  public static void setAuditRequestResponseBuilderThreadLocal(
      AuditRequestResponseDto.AuditRequestResponseDtoBuilder builder) {
    auditRequestResponseBuilderThreadLocal.set(builder);
    if (log.isDebugEnabled()) {
      log.debug("Set: {}", auditRequestResponseBuilderThreadLocal.get());
    }
  }
}
