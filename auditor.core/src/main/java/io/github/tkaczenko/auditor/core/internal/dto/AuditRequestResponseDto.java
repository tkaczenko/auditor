package io.github.tkaczenko.auditor.core.internal.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Represents a data transfer object (DTO) for an audit request and response. This DTO contains
 * information about the request, such as the provider, request type, headers, and body, as well as
 * the response, including the status, headers, and body. It also includes details like the time
 * spent processing the request, the URL, and the remote address. The DTO is used to capture and
 * store audit information for later persisting.
 */
@Data
@Builder
public class AuditRequestResponseDto {
  private String provider;

  private String requestType;

  @ToString.Exclude private long spendTimeInMs;

  @ToString.Exclude private String requestHeaders;

  @ToString.Exclude private String requestBody;

  @ToString.Exclude private String responseBody;

  @ToString.Exclude private String responseHeaders;

  private String error;

  private String url;

  private Integer httpStatus;

  private String requestMethod;

  @ToString.Exclude private String remoteAddress;

  private LocalDateTime createDateTime;

  /**
   * Builder class for the AuditRequestResponseDto class.
   */
  public static class AuditRequestResponseDtoBuilder {
    @Override
    public String toString() {
      return "AuditRequestResponseDtoBuilder{"
          + "provider='"
          + provider
          + '\''
          + ", requestType='"
          + requestType
          + '\''
          + ", url='"
          + url
          + '\''
          + ", httpStatus="
          + httpStatus
          + ", requestMethod='"
          + requestMethod
          + '\''
          + ", error='"
          + error
          + '\''
          + '}';
    }
  }
}
