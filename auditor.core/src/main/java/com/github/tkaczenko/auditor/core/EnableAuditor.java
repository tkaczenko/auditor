package com.github.tkaczenko.auditor.core;

import com.github.tkaczenko.auditor.core.config.AuditorConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * This annotation is used to enable the Auditor functionality in a Spring application. When applied
 * to the main application class or a configuration class, it will import the necessary {@link
 * AuditorConfiguration} to set up the Auditor functionality.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AuditorConfiguration.class)
public @interface EnableAuditor {}
