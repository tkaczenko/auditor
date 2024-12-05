package io.github.tkaczenko.auditor.inbound.config;

import io.github.tkaczenko.auditor.inbound.api.AuditInboundRequestFilter;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the Auditor, including reading HTTP inbound request data.
 *
 * <p>This class is responsible for setting up the necessary beans for the Auditor to
 * function properly.
 */
@Configuration
@ComponentScan(basePackages = "io.github.tkaczenko.auditor.inbound")
public class AuditorConfig {

  /**
   * Setting up the {@link FilterRegistrationBean}s for the auditor.
   *
   * @param auditInboundRequestFilters List of {@link AuditInboundRequestFilter}s to register
   * @return List of {@link FilterRegistrationBean}s
   */
  @Bean
  public List<FilterRegistrationBean<AuditInboundRequestFilter>>
      auditedInboundRequestFilterRegistration(
          List<? extends AuditInboundRequestFilter> auditInboundRequestFilters) {
    return auditInboundRequestFilters.stream()
        .map(this::filterRegistration)
        .toList();
  }

  private FilterRegistrationBean<AuditInboundRequestFilter> filterRegistration(
      AuditInboundRequestFilter auditInboundRequestFilter) {
    var registration = new FilterRegistrationBean<>(auditInboundRequestFilter);
    registration.addUrlPatterns(auditInboundRequestFilter.getUrlPatterns());
    registration.setName(auditInboundRequestFilter.getClass().getSimpleName());
    registration.setOrder(1);
    return registration;
  }
}
