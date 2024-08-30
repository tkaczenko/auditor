package com.github.tkaczenko.auditor.core;

/** Provides methods to determine the execution of a specific request. */
public interface Auditable {

  /**
   * Returns the provider of the request.
   *
   * @return the provider of the request
   */
  String getProvider();

  /**
   * Returns the type of the request.
   *
   * @return the type of the request
   */
  String getRequestType();
}
