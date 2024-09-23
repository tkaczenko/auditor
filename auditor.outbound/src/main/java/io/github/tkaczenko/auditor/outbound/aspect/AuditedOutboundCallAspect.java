package io.github.tkaczenko.auditor.outbound.aspect;

import io.github.tkaczenko.auditor.core.service.AuditFacade;
import io.github.tkaczenko.auditor.core.service.AuditRequestContext;
import io.github.tkaczenko.auditor.outbound.AuditedOutboundCall;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * This aspect intercepts method calls annotated with {@link AuditedOutboundCall} and performs
 * auditing of the outbound call. It captures the provider, request type, and any errors that occur
 * during the call, and saves this information using the {@link AuditFacade}.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditedOutboundCallAspect {

  private final AuditFacade auditFacade;

  /**
   * This method is responsible for intercepting method calls annotated with `@AuditedOutboundCall`
   * and performing auditing of the outbound call. It captures the provider, request type, and any
   * errors that occur during the call, and saves this information using the `AuditFacade`.
   *
   * @param jp the `ProceedingJoinPoint` object that represents the intercepted method call
   * @param auditedOutboundCall the `AuditedOutboundCall` annotation that provides the provider and
   *     request type information
   * @return the response from the intercepted method call
   */
  @SneakyThrows
  @Around(value = "@annotation(auditedOutboundCall)", argNames = "jp,auditedOutboundCall")
  public Object auditedOutboundCall(
      ProceedingJoinPoint jp, AuditedOutboundCall auditedOutboundCall) {
    AuditRequestContext.initializeAuditRequestBuilderThreadLocal();

    String provider = auditedOutboundCall.provider();
    String requestType = auditedOutboundCall.requestType();

    Object response;
    String error = null;
    try {
      response = jp.proceed(jp.getArgs());
    } catch (Exception e) {
      error = ExceptionUtils.getStackTrace(e);
      log.error("Calling external {} failed for {} due: {}", provider, requestType, error);
      throw e;
    } finally {
      auditFacade.save(error, provider, requestType);
    }

    return response;
  }
}
