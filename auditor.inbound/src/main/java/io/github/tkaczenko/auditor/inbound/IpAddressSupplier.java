package io.github.tkaczenko.auditor.inbound;

import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/** Retriever of the IP address of the client making the request. */
@Component
public class IpAddressSupplier {

  private static final String UNKNOWN = "unknown";

  /**
   * Provides an implementation to retrieve the IP address of the client making the request. The IP
   * address is determined by checking various HTTP headers that may contain the client's IP
   * address. If the IP address cannot be determined from the headers, the remote address of the
   * request is used.
   *
   * @param request the HTTP request
   * @return the IP address of the client making the request
   */
  public String getIpAddress(HttpServletRequest request) {
    return getClientIp(
        Stream.of(HttpRequestHeader.values())
            .map(HttpRequestHeader::getKey)
            .map(request::getHeader)
            .filter(this::isIpFound)
            .findFirst()
            .orElseGet(request::getRemoteAddr));
  }

  private String getClientIp(String ip) {
    if (StringUtils.isNotBlank(ip)) {
      String[] ips = StringUtils.split(ip, ',');
      return ips[0].trim();
    }

    return ip;
  }

  private boolean isIpFound(String ip) {
    return ip != null && !ip.isEmpty() && !UNKNOWN.equalsIgnoreCase(ip);
  }

  /** Defines the various HTTP headers that may contain the client's IP address. */
  @Getter
  @RequiredArgsConstructor
  public enum HttpRequestHeader {
    /**
     * The X-Forwarded-For HTTP header, which may contain the client's IP address when the request
     * has passed through a proxy or load balancer.
     */
    X_FORWARDED_FOR("X-Forwarded-For"),

    /**
     * The Proxy-Client-IP HTTP header, which may contain the client's IP address when the request
     * has passed through a proxy.
     */
    PROXY_CLIENT_IP("Proxy-Client-IP"),

    /**
     * The WL-Proxy-Client-IP HTTP header, which may contain the client's IP address when the
     * request has passed through a WebLogic proxy.
     */
    WL_PROXY_CLIENT_IP("WL-Proxy-Client-IP"),

    /** The HTTP_CLIENT_IP HTTP header, which may contain the client's IP address. */
    HTTP_CLIENT_IP("HTTP_CLIENT_IP"),

    /**
     * The HTTP_X_FORWARDED_FOR HTTP header, which may contain the client's IP address when the
     * request has passed through a proxy or load balancer.
     */
    HTTP_X_FORWARDED_FOR("HTTP_X_FORWARDED_FOR");

    private final String key;
  }
}
