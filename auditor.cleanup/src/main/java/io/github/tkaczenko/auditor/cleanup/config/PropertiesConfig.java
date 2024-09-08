package io.github.tkaczenko.auditor.cleanup.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Provides a configuration class for the Auditor's properties.
 *
 * <p>This class is responsible for managing the configuration properties for the Auditor
 * application, including scheduling-related properties.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesConfig {

  /** Provides configuration properties for the auditor scheduling functionality. */
  @Data
  @Configuration("schedulingProperties")
  @ConfigurationProperties(prefix = "auditor.scheduling")
  public static class SchedulingProperties {
    private String cron;
  }
}
