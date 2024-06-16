/**
 * This module provides the functionality for the Auditor to store inbound request audited
 * information. It exports the main package containing the core classes and services, and requires
 * the necessary dependencies.
 */
module auditor.inbound {
  exports com.github.tkaczenko.auditor.inbound;
  exports com.github.tkaczenko.auditor.inbound.config;

  requires auditor.core;
  requires static lombok;
  requires org.slf4j;
  requires org.apache.commons.lang3;
  requires org.apache.tomcat.embed.core;
  requires spring.boot;
  requires spring.context;
  requires spring.core;
  requires spring.web;
}
