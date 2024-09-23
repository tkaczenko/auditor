package io.github.tkaczenko.auditor.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import io.github.tkaczenko.auditor.core.model.dto.AuditRequestResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuditFacadeTest {

  private static final AuditFacade.AuditRequest EXPECTED_AUDIT_REQUEST =
      AuditFacade.AuditRequest.builder()
          .url("url")
          .requestMethod("method")
          .requestBody("requestBody")
          .requestHeaders("{\"Authorization\":[\"Basic\"]}")
          .remoteAddress("remoteAddress")
          .build();
  private static final AuditFacade.AuditResponse EXPECTED_AUDIT_RESPONSE =
      AuditFacade.AuditResponse.builder()
          .error("error")
          .responseBody("responseBody")
          .responseHeaders("{\"Content-Type\":[\"application/json\"]}")
          .callDurationMillis(10L)
          .httpStatus(200)
          .build();

  private static final AuditRequestResponseDto EXPECTED_AUDIT_REQUEST_RESPONSE =
      AuditRequestResponseDto.builder()
          .url("url")
          .requestMethod("method")
          .requestBody("requestBody")
          .requestHeaders("{\"Authorization\":[\"Basic\"]}")
          .remoteAddress("remoteAddress")
          .error("error")
          .responseBody("responseBody")
          .responseHeaders("{\"Content-Type\":[\"application/json\"]}")
          .spendTimeInMs(10L)
          .provider("provider")
          .requestType("requestType")
          .httpStatus(200)
          .build();

  @Mock private AuditService auditService;

  @InjectMocks private AuditFacade auditFacade;

  @Test
  void setRequest() {
    auditFacade.setRequest(EXPECTED_AUDIT_REQUEST);

    AuditRequestResponseDto actual =
        AuditRequestContext.getAuditRequestResponseBuilderThreadLocal().build();

    assertEquals(EXPECTED_AUDIT_REQUEST.url(), actual.getUrl());
    assertEquals(EXPECTED_AUDIT_REQUEST.requestMethod(), actual.getRequestMethod());
    assertEquals(EXPECTED_AUDIT_REQUEST.requestBody(), actual.getRequestBody());
    assertEquals(EXPECTED_AUDIT_REQUEST.requestHeaders(), actual.getRequestHeaders());
    assertEquals(EXPECTED_AUDIT_REQUEST.remoteAddress(), actual.getRemoteAddress());
  }

  @Test
  void setResponse() {
    auditFacade.setResponse(EXPECTED_AUDIT_RESPONSE);

    AuditRequestResponseDto actual =
        AuditRequestContext.getAuditRequestResponseBuilderThreadLocal().build();

    assertEquals(EXPECTED_AUDIT_RESPONSE.error(), actual.getError());
    assertEquals(EXPECTED_AUDIT_RESPONSE.responseBody(), actual.getResponseBody());
    assertEquals(EXPECTED_AUDIT_RESPONSE.responseHeaders(), actual.getResponseHeaders());
    assertEquals(EXPECTED_AUDIT_RESPONSE.callDurationMillis(), actual.getSpendTimeInMs());
    assertEquals(EXPECTED_AUDIT_RESPONSE.httpStatus(), actual.getHttpStatus());
  }

  @Test
  void save() {
    auditFacade.setRequest(EXPECTED_AUDIT_REQUEST);
    auditFacade.setResponse(EXPECTED_AUDIT_RESPONSE);

    String expectedProvider = "provider";
    String expectedRequestType = "requestType";
    auditFacade.save(null, expectedProvider, expectedRequestType);

    verify(auditService).save(EXPECTED_AUDIT_REQUEST_RESPONSE);
  }
}
