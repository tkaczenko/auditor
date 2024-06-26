package com.github.tkaczenko.auditor.core.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

/**
 * Represents an audit request and response entity. This class is used to store information about
 * requests and responses made to or from external services.
 */
@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    AuditRequestResponse that = (AuditRequestResponse) o;
    return getRequestId() != null && Objects.equals(getRequestId(), that.getRequestId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
