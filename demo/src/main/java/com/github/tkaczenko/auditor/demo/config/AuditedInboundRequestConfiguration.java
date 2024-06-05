package com.github.tkaczenko.auditor.demo.config;

import com.github.tkaczenko.auditor.core.service.AuditDateTimeProvider;
import com.github.tkaczenko.auditor.core.service.AuditService;
import com.github.tkaczenko.auditor.core.service.reader.BodyHttpReaderService;
import com.github.tkaczenko.auditor.core.service.reader.HeadersHttpReaderService;
import com.github.tkaczenko.auditor.demo.service.filter.DemoControllerAuditRequestFilter;
import com.github.tkaczenko.auditor.inbound.IpAddressSupplier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditedInboundRequestConfiguration {

  @Bean
  public FilterRegistrationBean auditedInboundRequestFilterRegistration(
      AuditDateTimeProvider auditDateTimeProvider,
      AuditService auditService,
      BodyHttpReaderService bodyReaderService,
      HeadersHttpReaderService headersReaderService,
      IpAddressSupplier ipAddressSupplier) {
    var loggingRequestFilter =
        new DemoControllerAuditRequestFilter(
            auditDateTimeProvider,
            auditService,
            bodyReaderService,
            headersReaderService,
            ipAddressSupplier);
    var registration = new FilterRegistrationBean<>(loggingRequestFilter);
    registration.addUrlPatterns("/test/inbound");
    registration.setOrder(1);
    return registration;
  }

}
