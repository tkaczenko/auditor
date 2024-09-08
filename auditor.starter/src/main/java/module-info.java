/**
 * This module provides the functionality for the Auditor to autoconfigure. It exports the main
 * package containing the core classes and services, and requires the necessary dependencies.
 */
module auditor.starter {
  exports io.github.tkaczenko.auditor.starter;

  requires auditor.core;
  requires auditor.inbound;
  requires auditor.outbound;
  requires auditor.cleanup;
  requires spring.context;
  requires org.slf4j;
}
