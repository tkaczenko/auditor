package com.github.tkaczenko.auditor.demo.inbound;

import static com.github.tkaczenko.auditor.demo.util.FileUtils.readSystemResource;
import static com.github.tkaczenko.auditor.demo.util.Files.Inbound;

import com.github.tkaczenko.auditor.demo.AbstractIntegrationTest;
import com.github.tkaczenko.auditor.demo.IntegrationTestScenario;
import com.github.tkaczenko.auditor.demo.model.dto.ErrorResponse;
import com.github.tkaczenko.auditor.demo.model.dto.Response;
import io.restassured.response.ValidatableResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("MultiInboundIntegrationTests " + MultiInboundIntegrationTests.TEST_URL + " ")
public class MultiInboundIntegrationTests extends AbstractIntegrationTest {

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
  @Sql("/MultiInboundIntegrationTests/success.sql")
  @DisplayName("when inbound request comes -> should save AuditRecord and return 200")
  void whenInboundRequestComesShouldSaveAuditRecordAndReturn200() {
    try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
      CompletableFuture.runAsync(this::executeFirst, executor)
          .thenRunAsync(this::executeSecond, executor)
          .join();
    }
  }

  @SneakyThrows
  private void executeFirst() {
    runAndVerify(
        IntegrationTestScenario.builder()
            .api(
                IntegrationTestScenario.Api.builder()
                    .url(TEST_URL)
                    .status(HttpStatus.OK)
                    .requestBody(readSystemResource(Inbound.INBOUND_MULTI_1_SUCCESS_REQUEST))
                    .responseBody(
                        Response.class,
                        readSystemResource(Inbound.INBOUND_MULTI_1_SUCCESS_RESPONSE))
                    .build())
            .expectedTransactionId("transactionId6")
            .build());
  }

  @SneakyThrows
  private void executeSecond() {
    runAndVerify(
        IntegrationTestScenario.builder()
            .api(
                IntegrationTestScenario.Api.builder()
                    .url(TEST_URL)
                    .status(HttpStatus.OK)
                    .requestBody(readSystemResource(Inbound.INBOUND_MULTI_2_SUCCESS_REQUEST))
                    .responseBody(
                        Response.class,
                        readSystemResource(Inbound.INBOUND_MULTI_2_SUCCESS_RESPONSE))
                    .build())
            .expectedTransactionId("transactionId8")
            .build());
  }
}
