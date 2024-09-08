package io.github.tkaczenko.auditor.demo.config;

import feign.Logger;
import io.github.tkaczenko.auditor.outbound.feign.FeignAuditRequestLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

  @Value("${feign.logging-level:BASIC}")
  private Logger.Level loggingLevel;

  @Bean
  public Logger.Level loggingLevel() {
    return loggingLevel;
  }

  @Bean
  public Logger logger(FeignAuditRequestLogger feignAuditRequestLogger) {
    return feignAuditRequestLogger;
  }
}
