package io.github.tkaczenko.auditor.outbound.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a method as an audited outbound call. It provides metadata about
 * the call, such as the provider and request type. The auditing system can use this information to
 * track and log the outbound calls made by the application.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuditedOutboundCall {

  /**
   * Returns the provider of the audited outbound call.
   *
   * @return the provider of the audited outbound call
   */
  String provider();

  /**
   * Returns the request type of the audited outbound call.
   *
   * @return the request type of the audited outbound call
   */
  String requestType();
}
