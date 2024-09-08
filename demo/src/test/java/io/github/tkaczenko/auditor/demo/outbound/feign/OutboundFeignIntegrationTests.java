package io.github.tkaczenko.auditor.demo.outbound.feign;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static io.github.tkaczenko.auditor.demo.util.FileUtils.readSystemResource;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.github.tkaczenko.auditor.demo.AbstractIntegrationTest;
import io.github.tkaczenko.auditor.demo.IntegrationTestScenario;
import io.github.tkaczenko.auditor.demo.config.PropertiesConfig.FeignClientProperties;
import io.github.tkaczenko.auditor.demo.model.dto.ErrorResponse;
import io.github.tkaczenko.auditor.demo.model.dto.Response;
import io.github.tkaczenko.auditor.demo.util.Files.Inbound;
import io.github.tkaczenko.auditor.demo.util.Files.Outbound;
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

@WireMockTest(httpPort = OutboundFeignIntegrationTests.WIREMOCK_PORT)
@DisplayName("OutboundFeignIntegrationTests " + OutboundFeignIntegrationTests.TEST_URL + " ")
public class OutboundFeignIntegrationTests extends AbstractIntegrationTest {

  public static final String TEST_URL = "/test/outbound/feign";
  public static final int WIREMOCK_PORT = 9562;

  @Autowired private FeignClientProperties feignClientProperties;

  @Override
  protected void stubScenario(
      String scenario, String from, String to, IntegrationTestScenario.Provider provider) {
    stubFor(
        post(urlPathEqualTo("/feign"))
            .inScenario(scenario)
            .whenScenarioStateIs(from)
            .withQueryParam("clientReference", equalTo(feignClientProperties.getClientReference()))
            .withRequestBody(equalToJson(provider.getRequestBody()))
            .willReturn(
                aResponse()
                    .withStatus(provider.getStatus().value())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(provider.getResponseBody()))
            .willSetStateTo(to));
  }

  @Override
  protected void verifyApiResponseBody(
      IntegrationTestScenario.Api api, ValidatableResponse validatableResponse) {
    if (api.getResponseBodyObject() instanceof Response expected) {
      validatableResponse.body(
          "responseTransactionId", CoreMatchers.equalTo(expected.getResponseTransactionId()));
    } else if (api.getResponseBodyObject() instanceof ErrorResponse expected) {
      validateOptionalFieldInBody(expected.getError(), "error", validatableResponse);
    }
  }

  @Test
  @SneakyThrows
  @Sql("/OutboundFeignIntegrationTests/success.sql")
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
                    .requestBody(readSystemResource(Inbound.INBOUND_SUCCESS_REQUEST_TO_FEIGN))
                    .responseBody(
                        Response.class,
                        readSystemResource(Inbound.INBOUND_SUCCESS_RESPONSE_TO_FEIGN))
                    .build())
            .provider(
                IntegrationTestScenario.Provider.builder()
                    .status(HttpStatus.OK)
                    .requestBody(readSystemResource(Outbound.Feign.FEIGN_SUCCESS_REQUEST))
                    .responseBody(readSystemResource(Outbound.Feign.FEIGN_SUCCESS_RESPONSE))
                    .build())
            .expectedTransactionId("transactionId4")
            .build());
  }
}
