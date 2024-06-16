package com.github.tkaczenko.auditor.outbound.resttemplate;

import com.github.tkaczenko.auditor.core.Auditable;
import com.github.tkaczenko.auditor.core.service.AuditDateTimeProvider;
import com.github.tkaczenko.auditor.core.service.AuditFacade;
import com.github.tkaczenko.auditor.core.service.reader.BodyHttpReaderService;
import com.github.tkaczenko.auditor.core.service.reader.HeadersHttpReaderService;
import com.github.tkaczenko.auditor.outbound.resttemplate.aspect.RestTemplateAuditOutboundRequestInterceptorWithAspect;
import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

/**
 * This class provides an implementation of the {@link
 * RestTemplateAuditOutboundRequestInterceptorWithAspect} that intercepts outbound requests made
 * using the Spring RestTemplate and performs auditing functionality.
 *
 * <p>The interceptor is responsible for capturing the request and response details, including the
 * request headers, body, and the response status code, and passing this information to the {@link
 * AuditFacade} for storage and processing.
 *
 * <p>This implementation extends the base {@link
 * RestTemplateAuditOutboundRequestInterceptorWithAspect} class and provides explicit call to the
 * save outbound requests functionality.
 */
public abstract class RestTemplateAuditOutboundOutboundRequestInterceptorWithAspect
    extends RestTemplateAuditOutboundRequestInterceptorWithAspect implements Auditable {

  /**
   * Constructor.
   *
   * @param auditFacade for storage and processing
   * @param auditDateTimeProvider for audit datetime
   * @param bodyReaderService for reading body
   * @param headersReaderService for reading headers
   */
  public RestTemplateAuditOutboundOutboundRequestInterceptorWithAspect(
      final AuditFacade auditFacade,
      final AuditDateTimeProvider auditDateTimeProvider,
      final BodyHttpReaderService bodyReaderService,
      final HeadersHttpReaderService headersReaderService) {
    super(auditFacade, auditDateTimeProvider, bodyReaderService, headersReaderService);
  }

  @Override
  public ClientHttpResponse intercept(
      final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution)
      throws IOException {
    ClientHttpResponse response = super.intercept(request, body, execution);

    auditFacade.save(null, getProvider(), getRequestType());
    return response;
  }
}
