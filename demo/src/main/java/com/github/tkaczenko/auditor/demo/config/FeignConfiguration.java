package com.github.tkaczenko.auditor.demo.config;

import com.github.tkaczenko.auditor.core.service.AuditDateTimeProvider;
import com.github.tkaczenko.auditor.core.service.AuditService;
import com.github.tkaczenko.auditor.core.service.reader.BodyHttpReaderService;
import com.github.tkaczenko.auditor.core.service.reader.HeadersHttpReaderService;
import com.github.tkaczenko.auditor.outbound.feign.FeignAuditRequestLogger;
import feign.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

  @Value("${feign.logging-level:BASIC}")
  private Logger.Level loggingLevel;

  @Bean
  public Logger logger(
      AuditDateTimeProvider auditDateTimeProvider,
      AuditService auditService,
      BodyHttpReaderService bodyReaderService,
      HeadersHttpReaderService headersReaderService) {
    return new FeignAuditRequestLogger(
        auditDateTimeProvider, auditService, bodyReaderService, headersReaderService);
  }
}
