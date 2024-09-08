package io.github.tkaczenko.auditor.demo.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesConfig {

  @Data
  @Configuration
  @ConfigurationProperties("demo-client")
  public static class DemoClientProperties {

    private String url;
    private String clientId;
    private String clientSecret;
  }

  @Data
  @Configuration
  @ConfigurationProperties("feign")
  public static class FeignClientProperties {

    private String url;
    private String clientReference;
  }
}
