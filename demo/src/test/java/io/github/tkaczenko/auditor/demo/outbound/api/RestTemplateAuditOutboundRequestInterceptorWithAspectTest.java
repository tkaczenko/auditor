package io.github.tkaczenko.auditor.demo.outbound.api;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.tkaczenko.auditor.core.internal.AuditFacade;
import io.github.tkaczenko.auditor.core.internal.factory.body.BodyHttpReaderService;
import io.github.tkaczenko.auditor.core.internal.factory.headers.HeadersHttpReaderService;
import io.github.tkaczenko.auditor.core.internal.service.AuditDateTimeProvider;
import io.github.tkaczenko.auditor.outbound.api.RestTemplateAuditOutboundRequestInterceptorWithAspect;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;

@ExtendWith(MockitoExtension.class)
class RestTemplateAuditOutboundRequestInterceptorWithAspectTest {

  @Mock private AuditDateTimeProvider auditDateTimeProvider;
  @Mock private AuditFacade auditFacade;
  @Mock private BodyHttpReaderService bodyReaderService;
  @Mock private HeadersHttpReaderService headersReaderService;

  @InjectMocks private RestTemplateAuditOutboundRequestInterceptorWithAspect subject;

  @Mock private ClientHttpRequestExecution execution;

  @Test
  void shouldDoAudit() throws Exception {
    HttpRequest httpRequest = new MockClientHttpRequest();
    try (ClientHttpResponse httpResponse = new MockClientHttpResponse()) {
      when(auditDateTimeProvider.fromNow()).thenReturn(Optional.of(LocalDateTime.now()));
      when(execution.execute(eq(httpRequest), any())).thenReturn(httpResponse);

      subject.intercept(httpRequest, new byte[] {}, execution);

      verify(execution, times(1)).execute(any(HttpRequest.class), any());
      verify(auditFacade, times(1)).setRequest(any(AuditFacade.AuditRequest.class));
      verify(auditFacade, times(1))
          .setResponse(argThat(auditResponse -> auditResponse.error() == null));
    }
  }

  @Test
  @SuppressFBWarnings("RV")
  void shouldDoAuditIfExceptionOccurs() throws Exception {
    HttpRequest httpRequest = new MockClientHttpRequest();
    when(auditDateTimeProvider.fromNow()).thenReturn(Optional.of(LocalDateTime.now()));
    doThrow(new IOException("Something went wrong"))
        .when(execution)
        .execute(any(HttpRequest.class), any());

    assertThrows(IOException.class, () -> subject.intercept(httpRequest, new byte[] {}, execution));

    verify(execution, times(1)).execute(any(HttpRequest.class), any());
    verify(auditFacade, times(1)).setRequest(any(AuditFacade.AuditRequest.class));
  }
}
