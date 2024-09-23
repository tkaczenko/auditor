package io.github.tkaczenko.auditor.cleanup;

import io.github.tkaczenko.auditor.cleanup.config.AuditorConfig;
import io.github.tkaczenko.auditor.core.EnableAuditor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Enables scheduled cleanup functionality for the Auditor. This annotation should be applied to the
 * main application class or a configuration class. It imports the necessary configuration for the
 * scheduled cleanup feature.
 *
 * <p>The scheduled cleanup feature is responsible for periodically removing old audit records from
 * the database to keep the database size manageable. This annotation configures the necessary
 * scheduling and cleanup logic.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableAuditor
@Import(AuditorConfig.class)
public @interface EnableScheduledCleanup {}
