package com.github.tkaczenko.auditor.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Provides the configuration for the Auditor. This class is responsible for setting up
 * the necessary beans for the Auditor to function properly.
 */
@Configuration
@ComponentScan(basePackages = "com.github.tkaczenko.auditor.core")
public class AuditorConfiguration {}
