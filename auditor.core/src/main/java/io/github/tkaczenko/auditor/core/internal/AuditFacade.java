package io.github.tkaczenko.auditor.core.internal;

import io.github.tkaczenko.auditor.core.internal.dto.AuditRequestResponseDto;
import io.github.tkaczenko.auditor.core.internal.service.AuditService;
import io.github.tkaczenko.auditor.core.internal.service.ThreadLocalAuditRequestResponseContext;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Provides a facade for auditing requests and responses. This class is responsible for managing the
 * audit process, including setting request and response details, and saving the audit information.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuditFacade {

  private final AuditService auditService;

  /**
   * Sets the audit request details, including the URL, request method, headers, body, and remote
   * address. This method is responsible for initializing and populating the audit request builder
   * with the provided request information.
   *
   * @param auditRequest the audit request object containing the necessary details
   */
  public void setRequest(AuditRequest auditRequest) {
    var auditRequestBuilder =
        Optional.ofNullable(
                ThreadLocalAuditRequestResponseContext.getAuditRequestResponseBuilderThreadLocal())
            .orElseGet(
                ThreadLocalAuditRequestResponseContext::initializeAuditRequestBuilderThreadLocal);

    auditRequestBuilder
        .createDateTime(auditRequest.createDateTime)
        .url(auditRequest.url)
        .requestMethod(auditRequest.requestMethod)
        .requestHeaders(auditRequest.requestHeaders)
        .requestBody(auditRequest.requestBody)
        .remoteAddress(auditRequest.remoteAddress);

    ThreadLocalAuditRequestResponseContext.setAuditRequestResponseBuilderThreadLocal(
        auditRequestBuilder);
  }

  /**
   * Sets the audit response details, including the error, HTTP status, response headers, response
   * body, and the call duration in milliseconds. This method is responsible for updating the audit
   * request builder with the provided response information.
   *
   * @param auditResponse the audit response object containing the necessary details
   */
  public void setResponse(AuditResponse auditResponse) {
    var auditRequestBuilder =
        ThreadLocalAuditRequestResponseContext.getAuditRequestResponseBuilderThreadLocal();

    auditRequestBuilder
        .error(auditResponse.error())
        .httpStatus(auditResponse.httpStatus())
        .responseHeaders(auditResponse.responseHeaders())
        .responseBody(auditResponse.responseBody())
        .spendTimeInMs(auditResponse.callDurationMillis());

    ThreadLocalAuditRequestResponseContext.setAuditRequestResponseBuilderThreadLocal(
        auditRequestBuilder);
  }

  /**
   * Saves the audit request and response details to the audit service.
   *
   * @param error the error message, if any, associated with the request/response
   * @param provider the provider or service name associated with the request/response
   * @param requestType the type of request, e.g., "GET_REPORT", "POST_TRANSACTION", etc.
   */
  public void save(String error, String provider, String requestType) {
    var auditRequestBuilder =
        ThreadLocalAuditRequestResponseContext.getAuditRequestResponseBuilderThreadLocal();

    AuditRequestResponseDto dto =
        auditRequestBuilder.provider(provider).requestType(requestType).build();

    if (dto.getUrl() == null) {
      // Do NOT save non-HTTP error
      log.warn("Do NOT save AuditRequest due to: {}", error);
      return;
    }

    if (error != null && dto.getError() == null) {
      // Set error if stacktrace exists
      dto.setError(error);
    }

    auditService.save(dto);
    ThreadLocalAuditRequestResponseContext.initializeAuditRequestBuilderThreadLocal();
  }

  /**
   * Represents a record for an audit request, containing the necessary details such as the URL,
   * request method, headers, body, remote address, and the creation datetime.
   *
   * @param url url
   * @param requestMethod HTTP request method
   * @param requestHeaders HTTP request headers
   * @param requestBody HTTP request body
   * @param remoteAddress remote address
   * @param createDateTime creation datetime
   */
  @Builder
  public record AuditRequest(
      String url,
      String requestMethod,
      String requestHeaders,
      String requestBody,
      String remoteAddress,
      LocalDateTime createDateTime) {}

  /**
   * Represents a record for an audit response, containing the necessary details such as the error
   * message, HTTP status, response headers, response body, and the call duration in milliseconds.
   *
   * @param error error message
   * @param httpStatus HTTP status
   * @param responseHeaders HTTP response headers
   * @param responseBody HTTP response body
   * @param callDurationMillis call duration
   */
  @Builder
  public record AuditResponse(
      String error,
      Integer httpStatus,
      String responseHeaders,
      String responseBody,
      Long callDurationMillis) {}
}
