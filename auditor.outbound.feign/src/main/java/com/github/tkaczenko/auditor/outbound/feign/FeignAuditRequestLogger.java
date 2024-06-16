package com.github.tkaczenko.auditor.outbound.feign;

import com.github.tkaczenko.auditor.core.service.AuditDateTimeProvider;
import com.github.tkaczenko.auditor.core.service.AuditFacade;
import com.github.tkaczenko.auditor.core.service.reader.BodyHttpReaderService;
import com.github.tkaczenko.auditor.core.service.reader.HeadersHttpReaderService;
import feign.Logger;
import feign.Request;
import feign.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Logs the Feign request and response details for auditing purposes. This class extends the Feign
 * Logger class to provide custom logging functionality. It captures the request and response
 * details, including the URL, HTTP method, headers, and body, and stores them in the AuditFacade
 * for later retrieval and processing.
 */
@Component
@Scope("prototype")
@Slf4j
@RequiredArgsConstructor
public class FeignAuditRequestLogger extends Logger {

  private final AuditDateTimeProvider auditDateTimeProvider;
  private final AuditFacade auditFacade;
  private final BodyHttpReaderService bodyReaderService;
  private final HeadersHttpReaderService headersReaderService;

  private final ThreadLocal<Long> startTime = new ThreadLocal<>();

  @Override
  protected void logRequest(String configKey, Level logLevel, Request request) {
    startTime.set(System.nanoTime());
    setRequest(request);
    if (log.isDebugEnabled()) {
      super.logRequest(configKey, logLevel, request);
    }
  }

  private void setRequest(final Request request) {
    Optional<LocalDateTime> createDateTime = auditDateTimeProvider.fromNow();
    if (createDateTime.isEmpty()) {
      return;
    }
    auditFacade.setRequest(
        AuditFacade.AuditRequest.builder()
            .url(request.url())
            .requestMethod(request.httpMethod().name())
            .requestHeaders(headersReaderService.readToString(request))
            .requestBody(bodyReaderService.readToString(request))
            .createDateTime(createDateTime.get())
            .build());
  }

  @Override
  protected Response logAndRebufferResponse(
      String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
    Long startTimeMillis = startTime.get();
    if (startTimeMillis == null) {
      log.warn("Start time not set for the request.");
      return response;
    }
    long callDurationMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTimeMillis);
    try (Response rebufferResponse =
        super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime)) {
      setResponse(rebufferResponse, callDurationMillis);
      return rebufferResponse;
    }
  }

  private void setResponse(final Response rebufferResponse, final long callDurationMillis) {
    auditFacade.setResponse(
        AuditFacade.AuditResponse.builder()
            .error(null)
            .callDurationMillis(callDurationMillis)
            .httpStatus(rebufferResponse.status())
            .responseHeaders(headersReaderService.readToString(rebufferResponse))
            .responseBody(bodyReaderService.readToString(rebufferResponse))
            .build());
  }

  @Override
  protected void log(final String configKey, final String format, final Object... args) {
    if (log.isDebugEnabled()) {
      log.debug(String.format(methodTag(configKey) + format, args));
    }
  }
}
