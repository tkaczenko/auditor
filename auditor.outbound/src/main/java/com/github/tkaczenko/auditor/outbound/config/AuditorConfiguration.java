package com.github.tkaczenko.auditor.outbound.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the Auditor, including reading HTTP outbound request data.
 *
 * <p>This class is responsible for setting up the necessary beans for the Auditor to
 * function properly.
 */
@Configuration
@ComponentScan(basePackages = "com.github.tkaczenko.auditor.outbound")
public class AuditorConfiguration {}
