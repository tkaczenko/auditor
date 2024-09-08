package io.github.tkaczenko.auditor.core;

import io.github.tkaczenko.auditor.core.config.AuditorConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * This annotation is used to enable the Auditor functionality in a Spring application. When applied
 * to the main application class or a configuration class, it will import the necessary {@link
 * AuditorConfig} to set up the Auditor functionality.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AuditorConfig.class)
public @interface EnableAuditor {}
