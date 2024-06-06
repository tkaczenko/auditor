package com.github.tkaczenko.auditor.demo;

import static com.github.tkaczenko.auditor.demo.util.FileUtils.readSystemResource;
import static com.github.tkaczenko.auditor.demo.util.Files.Inbound;

import com.github.tkaczenko.auditor.demo.common.AbstractTest;
import com.github.tkaczenko.auditor.demo.common.TestScenario;
import com.github.tkaczenko.auditor.demo.model.dto.ErrorResponse;
import com.github.tkaczenko.auditor.demo.model.dto.Response;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("InboundItest " + InboundTests.TEST_URL + " ")
public class InboundTests extends AbstractTest {

  public static final String TEST_URL = "/test/inbound";

  @Override
  protected void stubScenario(
      String scenario, String from, String to, TestScenario.Provider provider) {
    // no outbound calls
  }

  @Override
  protected void verifyApiResponseBody(
      final TestScenario.Api api, final ValidatableResponse validatableResponse) {
    if (api.getResponseBodyObject() instanceof Response) {
      Response expected = (Response) api.getResponseBodyObject();
      validatableResponse.body(
          "responseTransactionId", CoreMatchers.equalTo(expected.getResponseTransactionId()));
    } else if (api.getResponseBodyObject() instanceof ErrorResponse) {
      ErrorResponse expected = (ErrorResponse) api.getResponseBodyObject();
      validateOptionalFieldInBody(expected.getError(), "error", validatableResponse);
    }
  }

  @Test
  @SneakyThrows
  @Sql("/InboundTests/success.sql")
  @DisplayName("when inbound request comes -> should save AuditRecord and return 200")
  void whenInboundRequestComesShouldSaveAuditRecordAndReturn200() {
    runAndVerify(
        TestScenario.builder()
            .api(
                TestScenario.Api.builder()
                    .url(TEST_URL)
                    .status(HttpStatus.OK)
                    .requestBody(readSystemResource(Inbound.INBOUND_SUCCESS_REQUEST))
                    .actualTransactionId("transactionId1")
                    .build())
            .expectedTransactionId("transactionId0")
            .build());
  }
}
