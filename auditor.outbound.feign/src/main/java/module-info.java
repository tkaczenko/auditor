/**
 * This module provides the functionality for the Auditor to store outbound Feign request audited
 * information. It exports the main package containing the core classes and services, and requires
 * the necessary dependencies.
 */
module auditor.outbound.feign {
  exports io.github.tkaczenko.auditor.outbound.feign;

  requires auditor.core;
  requires feign.core;
  requires static lombok;
  requires org.slf4j;
  requires spring.context;
  requires spring.core;
}
