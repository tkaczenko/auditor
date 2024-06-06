package com.github.tkaczenko.auditor.demo.common;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.response.ValidatableResponse;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@SuppressWarnings("checkstyle:VisibilityModifierCheck")
public abstract class AbstractTest extends TestVerifier {

  @LocalServerPort private int port;

  protected abstract void stubScenario(
      String scenario, String from, String to, TestScenario.Provider provider);

  protected abstract void verifyApiResponseBody(
          TestScenario.Api api, ValidatableResponse validatableResponse);

  protected void runAndVerify(TestScenario testScenario) {
    String expectedTransactionId = testScenario.getExpectedTransactionId();
    String actualTransactionId = testScenario.getApi().getActualTransactionId();
    if (log.isInfoEnabled()) {
      log.info(
          "Run and verify call to {} for expectedTransactionId={}, actualTransactionId={}",
          testScenario.getApi().getUrl(),
          expectedTransactionId,
          actualTransactionId);
    }

    stubScenarios(testScenario.getIntegration());
    runAndVerifyApiCall(testScenario.getApi());
    verifyAuditRecords(expectedTransactionId, actualTransactionId);
  }

  private void runAndVerifyApiCall(TestScenario.Api api) {
    ValidatableResponse validatableResponse =
        given()
            .log()
            .all()
            .when()
            .port(port)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(api.getRequestBody())
            .post(api.getUrl())
            .then()
            .assertThat()
            .statusCode(equalTo(api.getStatus().value()));

    if (log.isInfoEnabled()) {
      log.info("Validating HTTP status of call to {}: expected={}", api.getUrl(), api.getStatus());
    }
    verifyApiResponseBody(api, validatableResponse);
  }

  protected void validateOptionalFieldInBody(
      String expected, String actualFieldName, ValidatableResponse validatableResponse) {
    Optional.ofNullable(expected)
        .map(value -> validatableResponse.body(actualFieldName, equalTo(value)));
  }

  private void stubScenarios(TestScenario.Integration integration) {
    if (integration == null) {
      if (log.isInfoEnabled()) {
        log.info("No scenario to stub");
      }
      return;
    }
    String scenario = integration.getScenario();
    List<String> states = integration.getStates();
    List<TestScenario.Provider> providers = integration.getProviders();

    if (log.isInfoEnabled()) {
      log.info("Stubbing provider scenario {}", scenario);
    }
    for (int i = 0; i < providers.size(); i++) {
      String from = states.get(i);
      String to = states.get(i + 1);
      TestScenario.Provider provider = providers.get(i);

      stubScenario(scenario, from, to, provider);
    }
  }
}
