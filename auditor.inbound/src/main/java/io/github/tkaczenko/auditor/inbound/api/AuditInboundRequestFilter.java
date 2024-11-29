package io.github.tkaczenko.auditor.inbound.api;

import io.github.tkaczenko.auditor.core.api.Auditable;
import io.github.tkaczenko.auditor.core.internal.AuditFacade;
import io.github.tkaczenko.auditor.core.internal.factory.body.BodyHttpReaderService;
import io.github.tkaczenko.auditor.core.internal.factory.headers.HeadersHttpReaderService;
import io.github.tkaczenko.auditor.core.internal.service.AuditDateTimeProvider;
import io.github.tkaczenko.auditor.core.internal.service.ThreadLocalAuditRequestResponseContext;
import io.github.tkaczenko.auditor.inbound.internal.service.IpAddressSupplier;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * This filter is responsible for auditing incoming requests and responses, capturing relevant
 * information such as request details, response details, and any errors that may occur during the
 * request processing. The filter uses various services and utilities to gather the necessary
 * information and persists it through the AuditFacade service.
 */
@Slf4j
@AllArgsConstructor
public abstract class AuditInboundRequestFilter extends OncePerRequestFilter implements Auditable {

  private final AuditDateTimeProvider auditDateTimeProvider;
  private final AuditFacade auditFacade;
  private final BodyHttpReaderService bodyReaderService;
  private final HeadersHttpReaderService headersReaderService;
  private final IpAddressSupplier ipAddressSupplier;

  /**
   * This method returns the URL patterns that this filter should be applied to. The filter will
   * only be applied to requests that match the specified URL patterns.
   *
   * @return an array of URL patterns as strings
   */
  public abstract String[] getUrlPatterns();

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();
    return Arrays.stream(getUrlPatterns()).anyMatch(urlPattern -> !path.matches(urlPattern));
  }

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain filterChain)
      throws ServletException, IOException {
    ThreadLocalAuditRequestResponseContext.clearAuditRequestBuilderThreadLocal();

    Optional<LocalDateTime> createDateTime = auditDateTimeProvider.fromNow();
    if (createDateTime.isEmpty()) {
      filterChain.doFilter(request, response);
      return;
    }

    ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper cachingResponseWrapper =
        new ContentCachingResponseWrapper(response);

    String error = null;
    long callDurationMillis = 0L;
    long startTime = System.nanoTime();
    try {
      filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);
      callDurationMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
    } catch (Exception e) {
      callDurationMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
      error = ExceptionUtils.getStackTrace(e);
      throw e;
    } finally {
      setRequest(cachingRequestWrapper, createDateTime.get());
      setResponse(cachingResponseWrapper, error, callDurationMillis);
      cachingResponseWrapper.copyBodyToResponse();

      auditFacade.save(error, getProvider(), getRequestType());
    }
  }

  private void setRequest(
      final ContentCachingRequestWrapper cachingRequestWrapper,
      final LocalDateTime createDateTime) {
    auditFacade.setRequest(
        AuditFacade.AuditRequest.builder()
            .url(cachingRequestWrapper.getRequestURL().toString())
            .requestMethod(cachingRequestWrapper.getMethod())
            .requestBody(bodyReaderService.readToString(cachingRequestWrapper))
            .requestHeaders(headersReaderService.readToString(cachingRequestWrapper))
            .remoteAddress(ipAddressSupplier.getIpAddress(cachingRequestWrapper))
            .createDateTime(createDateTime)
            .build());
  }

  private void setResponse(
      final ContentCachingResponseWrapper cachingResponseWrapper,
      final String error,
      final Long callDurationMillis) {
    auditFacade.setResponse(
        AuditFacade.AuditResponse.builder()
            .error(error)
            .callDurationMillis(callDurationMillis)
            .httpStatus(cachingResponseWrapper.getStatus())
            .responseHeaders(headersReaderService.readToString(cachingResponseWrapper))
            .responseBody(bodyReaderService.readToString(cachingResponseWrapper))
            .build());
  }
}
