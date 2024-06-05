package com.github.tkaczenko.auditor.demo.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesConfiguration {

  @Data
  @Configuration
  @ConfigurationProperties("demo-client")
  public static class DemoClientProperties {

    private String url;

    public static class HttpHeaders {

      public static final String TRANSACTION_ID = "TRANSACTION-ID";
    }
  }
}
