/**
 * This module provides the core functionality for the Auditor. It exports the main package
 * containing the core classes and services, and requires the necessary dependencies.
 */
module auditor.core {
  exports com.github.tkaczenko.auditor.core;
  exports com.github.tkaczenko.auditor.core.config;
  exports com.github.tkaczenko.auditor.core.model;
  exports com.github.tkaczenko.auditor.core.repository;
  exports com.github.tkaczenko.auditor.core.service;
  exports com.github.tkaczenko.auditor.core.service.reader;

  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.databind;
  requires jakarta.persistence;
  requires org.hibernate.orm.core;
  requires static lombok;
  requires org.slf4j;
  requires spring.beans;
  requires spring.boot;
  requires spring.context;
  requires spring.core;
  requires spring.data.commons;
  requires spring.data.jpa;
  requires spring.tx;
  requires spring.web;
}
