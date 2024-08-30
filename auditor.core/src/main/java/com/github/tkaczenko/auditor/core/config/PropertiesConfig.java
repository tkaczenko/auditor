package com.github.tkaczenko.auditor.core.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Provides a configuration class for the Auditor's properties.
 *
 * <p>This class is responsible for managing the configuration properties for the Auditor
 * application, including persisting-related properties.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesConfig {

  /** Provides configuration properties for the auditor persisting functionality. */
  @Data
  @Configuration
  @ConfigurationProperties(prefix = "auditor.persist")
  public static class PersistingProperties {

    /**
     * Determines whether the audited record can be extended from the MDC (Mapped Diagnostic
     * Context). When set to true, the audited record can be extended from the MDC, allowing for
     * more flexibility in saving the audited data.
     */
    private boolean extendableFromMdc;
  }
}
