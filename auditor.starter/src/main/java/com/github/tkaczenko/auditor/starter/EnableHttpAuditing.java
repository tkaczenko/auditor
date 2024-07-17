package com.github.tkaczenko.auditor.starter;

import com.github.tkaczenko.auditor.starter.config.AuditorConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Enables HTTP auditing for the application. This annotation should be placed on the main
 * application class or a configuration class. It will enable the necessary components to capture
 * and store HTTP request and response data.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AuditorConfig.class)
public @interface EnableHttpAuditing {}
