package io.github.tkaczenko.auditor.demo.inbound.api;

import static io.github.tkaczenko.auditor.demo.util.FileUtils.readSystemResource;
import static io.github.tkaczenko.auditor.demo.util.Files.Inbound;

import io.github.tkaczenko.auditor.demo.AbstractIntegrationTest;
import io.github.tkaczenko.auditor.demo.IntegrationTestScenario;
import io.github.tkaczenko.auditor.demo.cleanup.api.dto.ErrorResponse;
import io.github.tkaczenko.auditor.demo.cleanup.api.dto.Response;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DisplayName("InboundIntegrationTests " + InboundIntegrationTests.TEST_URL + " ")
public class InboundIntegrationTests extends AbstractIntegrationTest {

  public static final String TEST_URL = "/test/inbound";

  @Override
  protected void stubScenario(
      String scenario, String from, String to, IntegrationTestScenario.Provider provider) {
    // no outbound calls
  }

  @Override
  protected void verifyApiResponseBody(
      final IntegrationTestScenario.Api api, final ValidatableResponse validatableResponse) {
    if (api.getResponseBodyObject() instanceof Response expected) {
      validatableResponse.body(
          "responseTransactionId", CoreMatchers.equalTo(expected.getResponseTransactionId()));
    } else if (api.getResponseBodyObject() instanceof ErrorResponse expected) {
      validateOptionalFieldInBody(expected.getError(), "error", validatableResponse);
    }
  }

  @Test
  @SneakyThrows
  @Sql("/InboundIntegrationTests/success.sql")
  @DisplayName("when inbound request comes -> should save AuditRecord and return 200")
  void whenInboundRequestComesShouldSaveAuditRecordAndReturn200() {
    runAndVerify(
        IntegrationTestScenario.builder()
            .api(
                IntegrationTestScenario.Api.builder()
                    .url(TEST_URL)
                    .status(HttpStatus.OK)
                    .requestBody(readSystemResource(Inbound.INBOUND_SUCCESS_REQUEST))
                    .responseBody(
                        Response.class, readSystemResource(Inbound.INBOUND_SUCCESS_RESPONSE))
                    .build())
            .expectedTransactionId("transactionId0")
            .build());
  }
}
