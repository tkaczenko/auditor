package com.github.tkaczenko.auditor.demo.config;

import com.github.tkaczenko.auditor.outbound.feign.FeignAuditRequestLogger;
import feign.Logger;
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
