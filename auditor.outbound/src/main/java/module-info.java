/**
 * This module provides the functionality for the Auditor to store outbound request audited
 * information. It exports the main package containing the core classes and services, and requires
 * the necessary dependencies.
 */
module auditor.outbound {
  exports io.github.tkaczenko.auditor.outbound;
  exports io.github.tkaczenko.auditor.outbound.config;
    exports io.github.tkaczenko.auditor.outbound.api;

    requires auditor.core;
  requires static lombok;
  requires org.slf4j;
  requires java.xml;
  requires org.apache.commons.lang3;
  requires org.aspectj.weaver;
  requires spring.context;
  requires spring.core;
  requires spring.web;
}
