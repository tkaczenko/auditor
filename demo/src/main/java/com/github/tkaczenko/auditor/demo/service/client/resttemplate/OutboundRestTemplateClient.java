package com.github.tkaczenko.auditor.demo.service.client.resttemplate;

import static com.github.tkaczenko.auditor.demo.config.PropertiesConfiguration.DemoClientProperties.HttpHeaders.TRANSACTION_ID;

import com.github.tkaczenko.auditor.demo.config.PropertiesConfiguration;
import com.github.tkaczenko.auditor.demo.service.client.resttemplate.dto.RestTemplateResponse;
import com.github.tkaczenko.auditor.outbound.AuditedOutboundCall;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Component
public class OutboundRestTemplateClient {

  private final RestTemplate restTemplateClient;
  private final PropertiesConfiguration.DemoClientProperties demoClientProperties;

  @SuppressFBWarnings("EI2")
  public OutboundRestTemplateClient(
      final RestTemplate restTemplateClient,
      final PropertiesConfiguration.DemoClientProperties demoClientProperties) {
    this.restTemplateClient = restTemplateClient;
    this.demoClientProperties = demoClientProperties;
  }

  @AuditedOutboundCall(
      provider = "outbound-rest-template-provider",
      requestType = "outbound-rest-template-request-type")
  public RestTemplateResponse getReport(String transactionId) {
    var requestEntity =
        new HttpEntity<>(
            CollectionUtils.toMultiValueMap(Map.of(TRANSACTION_ID, List.of(transactionId))));
    String url = demoClientProperties.getUrl();
    return restTemplateClient
        .exchange(url, HttpMethod.GET, requestEntity, RestTemplateResponse.class)
        .getBody();
  }
}
