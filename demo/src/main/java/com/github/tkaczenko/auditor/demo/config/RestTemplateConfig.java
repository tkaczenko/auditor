package com.github.tkaczenko.auditor.demo.config;

import com.github.tkaczenko.auditor.demo.config.PropertiesConfig.DemoClientProperties;
import com.github.tkaczenko.auditor.outbound.resttemplate.aspect.RestTemplateAuditOutboundRequestInterceptorWithAspect;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

  private final DemoClientProperties demoClientProperties;

  @Bean
  public RestTemplate restTemplateClient(
      RestTemplateAuditOutboundRequestInterceptorWithAspect auditInterceptor) {
    return new RestTemplateBuilder()
        .setConnectTimeout(Duration.ofMillis(10000))
        .setReadTimeout(Duration.ofMillis(10000))
        .requestFactory(
            () -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
        .basicAuthentication(
            demoClientProperties.getClientId(), demoClientProperties.getClientSecret())
        .additionalInterceptors(auditInterceptor)
        .build();
  }
}
