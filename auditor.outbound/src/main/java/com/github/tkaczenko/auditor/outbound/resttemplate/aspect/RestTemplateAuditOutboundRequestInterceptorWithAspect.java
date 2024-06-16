package com.github.tkaczenko.auditor.outbound.resttemplate.aspect;

import com.github.tkaczenko.auditor.core.service.AuditDateTimeProvider;
import com.github.tkaczenko.auditor.core.service.AuditFacade;
import com.github.tkaczenko.auditor.core.service.reader.BodyHttpReaderService;
import com.github.tkaczenko.auditor.core.service.reader.HeadersHttpReaderService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * This class is a Spring-based interceptor that intercepts outbound REST template requests and
 * audits the request and response details. It uses the AuditFacade to record the request and
 * response information, including the request URL, method, headers, body, response status, headers,
 * body, and the call duration.
 *
 * <p>The interceptor is responsible for setting the request details in the AuditFacade before the
 * request is executed, and setting the response details after the request has been executed. If an
 * exception occurs during the request execution, the interceptor will still record the error
 * details in the AuditFacade.
 *
 * <p>This interceptor is designed to be used with the Spring RestTemplate to provide comprehensive
 * auditing of outbound REST requests.
 */
@Slf4j
@Component("restTemplateAuditOutboundRequestInterceptorWithAspect")
@RequiredArgsConstructor
@SuppressWarnings("checkstyle:VisibilityModifierCheck")
public class RestTemplateAuditOutboundRequestInterceptorWithAspect
    implements ClientHttpRequestInterceptor {

  /**
   * The AuditFacade is responsible for recording the audit information for the outbound REST
   * template request and response. It provides methods to set the request and response details,
   * which are then persisted by the underlying audit service.
   */
  protected final AuditFacade auditFacade;

  private final AuditDateTimeProvider auditDateTimeProvider;
  private final BodyHttpReaderService bodyReaderService;
  private final HeadersHttpReaderService headersReaderService;

  /**
   * The intercept method is responsible for intercepting the outbound REST template request,
   * auditing the request and response details, and then executing the request. It performs the
   * following steps:
   *
   * <ol>
   *   <li>Sets the request details in the AuditFacade before the request is executed.
   *   <li>Executes the request using the provided ClientHttpRequestExecution.
   *   <li>If an exception occurs during the request execution, it records the error details in the
   *       AuditFacade.
   *   <li>Sets the response details in the AuditFacade after the request has been executed.
   *   <li>Returns the ClientHttpResponse object.
   * </ol>
   *
   * @param request the HttpRequest object representing the outbound REST template request
   * @param body the request body as a byte array
   * @param execution the ClientHttpRequestExecution object used to execute the request
   * @return the ClientHttpResponse object representing the response from the outbound REST template
   *     request
   * @throws IOException if an I/O error occurs during the request execution
   */
  @Override
  public ClientHttpResponse intercept(
      final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution)
      throws IOException {
    setRequest(request, body);

    ClientHttpResponse response = null;
    String error = null;
    Long callDurationMillis = 0L;
    long startTime = System.nanoTime();
    try {
      response = execution.execute(request, body);
      callDurationMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
    } catch (Exception e) {
      callDurationMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
      error = ExceptionUtils.getStackTrace(e);
      throw e;
    } finally {
      setResponse(response, error, callDurationMillis);
    }

    return response;
  }

  private void setRequest(HttpRequest request, byte[] body) {
    Optional<LocalDateTime> createDateTime = auditDateTimeProvider.fromNow();
    if (createDateTime.isEmpty()) {
      return;
    }
    auditFacade.setRequest(
        AuditFacade.AuditRequest.builder()
            .url(request.getURI().toString())
            .requestMethod(request.getMethod().name())
            .requestHeaders(headersReaderService.readToString(request))
            .requestBody(bodyReaderService.readToString(body))
            .createDateTime(createDateTime.get())
            .build());
  }

  @SneakyThrows
  private void setResponse(
      @Nullable final ClientHttpResponse response,
      final String error,
      final Long callDurationMillis) {
    if (response == null) {
      return;
    }
    auditFacade.setResponse(
        AuditFacade.AuditResponse.builder()
            .error(error)
            .callDurationMillis(callDurationMillis)
            .httpStatus(response.getStatusCode().value())
            .responseHeaders(headersReaderService.readToString(response))
            .responseBody(bodyReaderService.readToString(response))
            .build());
  }
}
