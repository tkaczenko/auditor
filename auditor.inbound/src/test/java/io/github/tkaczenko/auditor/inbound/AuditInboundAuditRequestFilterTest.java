package io.github.tkaczenko.auditor.inbound;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.isNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.tkaczenko.auditor.core.service.AuditDateTimeProvider;
import io.github.tkaczenko.auditor.core.service.AuditFacade;
import io.github.tkaczenko.auditor.core.service.reader.BodyHttpReaderService;
import io.github.tkaczenko.auditor.core.service.reader.HeadersHttpReaderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@ExtendWith(MockitoExtension.class)
class AuditInboundAuditRequestFilterTest {

  @Mock private FilterChain filterChain;

  @Mock private AuditDateTimeProvider auditDateTimeProvider;
  @Mock private AuditFacade auditFacade;
  @Mock private BodyHttpReaderService bodyReaderService;
  @Mock private HeadersHttpReaderService headersReaderService;
  @Mock private IpAddressSupplier ipAddressSupplier;

  private AuditInboundRequestFilter subject;

  @BeforeEach
  void setUp() {
    subject =
        new AuditInboundRequestFilter(
            auditDateTimeProvider,
            auditFacade,
            bodyReaderService,
            headersReaderService,
            ipAddressSupplier) {
          @Override
          public String getProvider() {
            return "provider";
          }

          @Override
          public String getRequestType() {
            return "requestType";
          }

          @Override
          public String[] getUrlPatterns() {
            return new String[] {"urlPattern"};
          }
        };
  }

  @Test
  void shouldSkipAudit() throws Exception {
    HttpServletRequest httpRequest = new MockHttpServletRequest();
    HttpServletResponse httpResponse = new MockHttpServletResponse();

    subject.doFilterInternal(httpRequest, httpResponse, filterChain);

    verify(filterChain, times(1))
        .doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
  }

  @Test
  void shouldDoAudit() throws Exception {
    HttpServletRequest httpRequest = new MockHttpServletRequest();
    HttpServletResponse httpResponse = new MockHttpServletResponse();
    when(auditDateTimeProvider.fromNow()).thenReturn(Optional.of(LocalDateTime.now()));

    subject.doFilterInternal(httpRequest, httpResponse, filterChain);

    verify(filterChain, times(1))
        .doFilter(
            any(ContentCachingRequestWrapper.class), any(ContentCachingResponseWrapper.class));
    verify(auditFacade, times(1)).setRequest(any(AuditFacade.AuditRequest.class));
    verify(auditFacade, times(1)).setResponse(any(AuditFacade.AuditResponse.class));
    verify(auditFacade, times(1)).save(isNull(), eq("provider"), eq("requestType"));
  }

  @Test
  @SuppressFBWarnings("RV")
  void shouldDoAuditIfExceptionOccurs() throws Exception {
    HttpServletRequest httpRequest = new MockHttpServletRequest();
    HttpServletResponse httpResponse = new MockHttpServletResponse();
    when(auditDateTimeProvider.fromNow()).thenReturn(Optional.of(LocalDateTime.now()));
    doThrow(new IOException("Something went wrong"))
        .when(filterChain)
        .doFilter(
            any(ContentCachingRequestWrapper.class), any(ContentCachingResponseWrapper.class));

    assertThrows(
        IOException.class, () -> subject.doFilterInternal(httpRequest, httpResponse, filterChain));

    verify(filterChain, times(1))
        .doFilter(
            any(ContentCachingRequestWrapper.class), any(ContentCachingResponseWrapper.class));
    verify(auditFacade, times(1)).setRequest(any(AuditFacade.AuditRequest.class));
    verify(auditFacade, times(1)).setResponse(any(AuditFacade.AuditResponse.class));
    verify(auditFacade, times(1)).save(isNotNull(), eq("provider"), eq("requestType"));
  }
}
