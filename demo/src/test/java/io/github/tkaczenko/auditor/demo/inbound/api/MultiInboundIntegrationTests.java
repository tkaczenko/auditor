package io.github.tkaczenko.auditor.demo.inbound.api;

import static io.github.tkaczenko.auditor.demo.util.FileUtils.readSystemResource;
import static io.github.tkaczenko.auditor.demo.util.Files.Inbound.Multi;

import io.github.tkaczenko.auditor.demo.AbstractIntegrationTest;
import io.github.tkaczenko.auditor.demo.IntegrationTestScenario;
import io.github.tkaczenko.auditor.demo.cleanup.api.dto.ErrorResponse;
import io.github.tkaczenko.auditor.demo.cleanup.api.dto.Response;
import io.restassured.response.ValidatableResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "server.tomcat.threads.max=1")
@DisplayName("MultiInboundIntegrationTests " + MultiInboundIntegrationTests.TEST_URL + " ")
public class MultiInboundIntegrationTests extends AbstractIntegrationTest {

  public static final String TEST_URL = "/test/inbound";

  @Autowired private MultiInboundService multiInboundService;

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
      CompletableFuture.runAsync(
              () -> this.multiInboundService.executeFirst(this::runAndVerify), executor)
          .thenRunAsync(() -> this.multiInboundService.executeSecond(this::runAndVerify), executor)
          .join();
    }
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    public MultiInboundService multiInboundService() {
      return new MultiInboundService();
    }
  }

  static class MultiInboundService {

    @Transactional
    @SneakyThrows
    public void executeFirst(Consumer<IntegrationTestScenario> runAndVerify) {
      runAndVerify.accept(
          IntegrationTestScenario.builder()
              .api(
                  IntegrationTestScenario.Api.builder()
                      .url(TEST_URL)
                      .status(HttpStatus.OK)
                      .requestBody(readSystemResource(Multi.INBOUND_MULTI_1_SUCCESS_REQUEST))
                      .responseBody(
                          Response.class,
                          readSystemResource(Multi.INBOUND_MULTI_1_SUCCESS_RESPONSE))
                      .build())
              .expectedTransactionId("transactionId6")
              .build());
    }

    @Transactional
    @SneakyThrows
    public void executeSecond(Consumer<IntegrationTestScenario> runAndVerify) {
      runAndVerify.accept(
          IntegrationTestScenario.builder()
              .api(
                  IntegrationTestScenario.Api.builder()
                      .url(TEST_URL)
                      .status(HttpStatus.OK)
                      .requestBody(readSystemResource(Multi.INBOUND_MULTI_2_SUCCESS_REQUEST))
                      .responseBody(
                          Response.class,
                          readSystemResource(Multi.INBOUND_MULTI_2_SUCCESS_RESPONSE))
                      .build())
              .expectedTransactionId("transactionId8")
              .build());
    }
  }
}
