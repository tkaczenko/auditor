package com.github.tkaczenko.auditor.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Represents an audit request and response entity. This class is used to store information about
 * requests and responses made to or from external services.
 */
@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuditRequestResponse {

  /** A unique identifier for the request, generated automatically. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "request_id", updatable = false)
  private Long requestId;

  /** The name of the provider or service that the request was made to. */
  @Column(name = "provider", updatable = false)
  private String provider;

  /** The type of request made (e.g., GET, POST, PUT, DELETE). */
  @Column(name = "request_type", updatable = false)
  private String requestType;

  /** The time taken to process the request, in milliseconds. */
  @ToString.Exclude
  @Column(name = "spend_time_in_ms", updatable = false)
  private long spendTimeInMs;

  /** The headers included in the request. */
  @ToString.Exclude
  @Column(name = "request_headers", updatable = false)
  private String requestHeaders;

  /** The body of the request. */
  @ToString.Exclude
  @Column(name = "request_body", updatable = false)
  private String requestBody;

  /** The body of the response. */
  @ToString.Exclude
  @Column(name = "response_body", updatable = false)
  private String responseBody;

  /** The headers included in the response. */
  @ToString.Exclude
  @Column(name = "response_headers", updatable = false)
  private String responseHeaders;

  /** Any error that occurred during the request. */
  @ToString.Exclude
  @Column(name = "error", updatable = false)
  private String error;

  /** The URL of the request. */
  @ToString.Exclude
  @Column(name = "url", updatable = false)
  private String url;

  /** The HTTP status code of the response. */
  @ToString.Exclude
  @Column(name = "http_status", updatable = false)
  private Integer httpStatus;

  /** The HTTP method used for the request. */
  @ToString.Exclude
  @Column(name = "request_method", updatable = false)
  private String requestMethod;

  /** The IP address of the client making the request. */
  @ToString.Exclude
  @Column(name = "remote_address", updatable = false)
  private String remoteAddress;

  /** The date and time when the request was made. */
  @Column(name = "create_date_time", updatable = false)
  private LocalDateTime createDateTime;
}
