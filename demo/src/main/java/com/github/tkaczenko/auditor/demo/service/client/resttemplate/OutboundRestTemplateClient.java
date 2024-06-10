package com.github.tkaczenko.auditor.demo.service.client.resttemplate;


import com.github.tkaczenko.auditor.demo.config.PropertiesConfiguration;
import com.github.tkaczenko.auditor.demo.service.client.resttemplate.dto.RestTemplateResponse;
import com.github.tkaczenko.auditor.outbound.AuditedOutboundCall;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
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
  public RestTemplateResponse get() {
    String url = demoClientProperties.getUrl();
    return restTemplateClient
        .exchange(url, HttpMethod.GET, HttpEntity.EMPTY, RestTemplateResponse.class)
        .getBody();
  }
}
