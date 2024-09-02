package com.github.tkaczenko.auditor.outbound;

import com.github.tkaczenko.auditor.core.EnableAuditor;
import com.github.tkaczenko.auditor.outbound.config.AuditorConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Enables outbound auditing for the application. This annotation should be placed on the main
 * application class or a configuration class. It will enable the outbound auditing functionality,
 * which includes capturing and storing information about outbound requests and their responses.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableAuditor
@Import(AuditorConfig.class)
public @interface EnableOutboundAuditing {

}
