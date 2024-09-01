package com.github.tkaczenko.auditor.inbound;

import com.github.tkaczenko.auditor.core.EnableAuditor;
import com.github.tkaczenko.auditor.inbound.config.AuditorConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Enables inbound auditing for the application. This annotation should be placed on the main
 * application class or a configuration class. It will enable the inbound auditing functionality,
 * which includes capturing and storing information about incoming requests and their responses.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableAuditor
@Import(AuditorConfig.class)
public @interface EnableInboundAuditing {}
