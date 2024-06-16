/**
 * This module provides the functionality for the Auditor to clean up audited data. It exports the
 * main package containing the core classes and services, and requires the necessary dependencies
 * such as JDBC, Lombok, ShedLock, and Spring.
 */
module auditor.cleanup {
  exports com.github.tkaczenko.auditor.cleanup;
  exports com.github.tkaczenko.auditor.cleanup.config;

  requires auditor.core;
  requires java.sql;
  requires static lombok;
  requires org.slf4j;
  requires net.javacrumbs.shedlock.core;
  requires net.javacrumbs.shedlock.provider.jdbctemplate;
  requires net.javacrumbs.shedlock.spring;
  requires spring.boot;
  requires spring.context;
}
