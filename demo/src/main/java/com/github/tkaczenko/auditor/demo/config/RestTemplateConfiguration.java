package com.github.tkaczenko.auditor.demo.config;

import com.github.tkaczenko.auditor.outbound.resttemplate.aspect.RestTemplateAuditOutboundRequestInterceptorWithAspect;
import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

  @Bean
  public RestTemplate restTemplateClient(
      RestTemplateBuilder builder,
      RestTemplateAuditOutboundRequestInterceptorWithAspect auditInterceptor) {
    return builder
        .setConnectTimeout(Duration.ofMillis(10000))
        .setReadTimeout(Duration.ofMillis(10000))
        .requestFactory(
            () -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
        .basicAuthentication("clientId", "clientSecret")
        .additionalInterceptors(auditInterceptor)
        .build();
  }
}
