package com.github.tkaczenko.auditor.starter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configures the Auditor. This class imports the necessary configurations from the Auditor core,
 * inbound, outbound, and cleanup modules.
 */
@Configuration
@Import({
  com.github.tkaczenko.auditor.core.config.AuditorConfig.class,
  com.github.tkaczenko.auditor.inbound.config.AuditorConfig.class,
  com.github.tkaczenko.auditor.outbound.config.AuditorConfig.class,
  com.github.tkaczenko.auditor.cleanup.config.AuditorConfig.class
})
public class AuditorConfig {}