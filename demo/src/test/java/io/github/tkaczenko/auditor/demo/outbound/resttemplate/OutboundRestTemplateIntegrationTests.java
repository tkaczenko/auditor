package io.github.tkaczenko.auditor.demo.outbound.resttemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.absent;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.github.tkaczenko.auditor.demo.util.FileUtils.readSystemResource;
import static io.github.tkaczenko.auditor.demo.util.Files.Inbound;
import static io.github.tkaczenko.auditor.demo.util.Files.Outbound.RestTemplate;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.github.tkaczenko.auditor.demo.AbstractIntegrationTest;
import io.github.tkaczenko.auditor.demo.IntegrationTestScenario;
import io.github.tkaczenko.auditor.demo.config.PropertiesConfig.DemoClientProperties;
import io.github.tkaczenko.auditor.demo.model.dto.ErrorResponse;
import io.github.tkaczenko.auditor.demo.model.dto.Response;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@WireMockTest(httpPort = OutboundRestTemplateIntegrationTests.WIREMOCK_PORT)
@DisplayName(
    "OutboundRestTemplateIntegrationTests " + OutboundRestTemplateIntegrationTests.TEST_URL + " ")
public class OutboundRestTemplateIntegrationTests extends AbstractIntegrationTest {

  public static final String TEST_URL = "/test/outbound/restTemplate";
  public static final int WIREMOCK_PORT = 9561;

  @Autowired private DemoClientProperties demoClientProperties;

  @Override
  protected void stubScenario(
      String scenario, String from, String to, IntegrationTestScenario.Provider provider) {
    stubFor(
        get(urlEqualTo("/demo-client"))
            .inScenario(scenario)
            .whenScenarioStateIs(from)
            .withBasicAuth(
                demoClientProperties.getClientId(), demoClientProperties.getClientSecret())
            .withRequestBody(absent())
            .willReturn(
                aResponse()
                    .withStatus(provider.getStatus().value())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(provider.getResponseBody()))
            .willSetStateTo(to));
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
  @Sql("/OutboundRestTemplateIntegrationTests/success.sql")
  @DisplayName(
      "when inbound request comes -> "
          + "should save AuditRecord for outbound request "
          + "and return 200")
  void whenInboundRequestComesShouldSaveAuditRecordAndReturn200() {
    runAndVerify(
        IntegrationTestScenario.builder()
            .api(
                IntegrationTestScenario.Api.builder()
                    .url(TEST_URL)
                    .status(HttpStatus.OK)
                    .requestBody(
                        readSystemResource(Inbound.INBOUND_SUCCESS_REQUEST_TO_REST_TEMPLATE))
                    .responseBody(
                        Response.class,
                        readSystemResource(Inbound.INBOUND_SUCCESS_RESPONSE_TO_REST_TEMPLATE))
                    .build())
            .provider(
                IntegrationTestScenario.Provider.builder()
                    .status(HttpStatus.OK)
                    .responseBody(readSystemResource(RestTemplate.REST_TEMPLATE_SUCCESS_RESPONSE))
                    .build())
            .expectedTransactionId("transactionId2")
            .build());
  }
}
