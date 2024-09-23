package io.github.tkaczenko.auditor.inbound;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.tkaczenko.auditor.inbound.IpAddressSupplier.HttpRequestHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

@SuppressWarnings("PMD.AvoidUsingHardCodedIP")
class IpAddressSupplierTest {

  private MockHttpServletRequest request;
  private IpAddressSupplier subject;

  @BeforeEach
  void setUp() {
    request = new MockHttpServletRequest();
    subject = new IpAddressSupplier();
  }

  @Test
  void whenOnlyForwardedForHeaderIsPresentShouldReturnTheClientIpAddress() {
    request.addHeader(HttpRequestHeader.X_FORWARDED_FOR.getKey(), "192.168.1.1, 10.10.1.1");

    String actual = subject.getIpAddress(request);

    assertEquals("192.168.1.1", actual);
  }

  @Test
  void whenOnlyProxyClientIpHeaderIsPresentShouldReturnTheIpAddress() {
    request.addHeader(HttpRequestHeader.PROXY_CLIENT_IP.getKey(), "192.168.2.2");

    String actual = subject.getIpAddress(request);

    assertEquals("192.168.2.2", actual);
  }

  @Test
  void whenOnlyWlProxyClientIpHeaderIsPresentShouldReturnTheIpAddress() {
    request.addHeader(HttpRequestHeader.WL_PROXY_CLIENT_IP.getKey(), "192.168.3.3");

    String actual = subject.getIpAddress(request);

    assertEquals("192.168.3.3", actual);
  }

  @Test
  void whenOnlyHttpClientIpHeaderIsPresentShouldReturnTheIpAddress() {
    request.addHeader(HttpRequestHeader.HTTP_CLIENT_IP.getKey(), "192.168.4.4");

    String actual = subject.getIpAddress(request);

    assertEquals("192.168.4.4", actual);
  }

  @Test
  void whenOnlyHttpXforwardedForHeaderIsPresentShouldReturnTheIpAddress() {
    request.addHeader(HttpRequestHeader.HTTP_X_FORWARDED_FOR.getKey(), "192.168.5.5, 10.10.5.5");

    String actual = subject.getIpAddress(request);

    assertEquals("192.168.5.5", actual);
  }

  @Test
  void whenNoHeadersExistsShouldReturnTheRequestRemoteAddress() {
    request.setRemoteAddr("192.168.6.6");

    String actual = subject.getIpAddress(request);

    assertEquals("192.168.6.6", actual);
  }

  @Test
  void whenForwardedForHeaderIsPresentShouldTakePriorityOverLessImportantHeaders() {
    request.addHeader(HttpRequestHeader.X_FORWARDED_FOR.getKey(), "192.168.2.2");
    request.addHeader(HttpRequestHeader.PROXY_CLIENT_IP.getKey(), "192.168.1.1");

    String actual = subject.getIpAddress(request);

    assertEquals("192.168.2.2", actual);
  }

  @Test
  void whenProxyClientIpHeaderIsPresentShouldTakePriorityOverLessImportantHeaders() {
    request.addHeader(HttpRequestHeader.PROXY_CLIENT_IP.getKey(), "192.168.3.3");
    request.addHeader(HttpRequestHeader.WL_PROXY_CLIENT_IP.getKey(), "192.168.2.2");

    String actual = subject.getIpAddress(request);

    assertEquals("192.168.3.3", actual);
  }

  @Test
  void whenWlProxyClientIpHeaderIsPresentShouldTakePriorityOverLessImportantHeaders() {
    request.addHeader(HttpRequestHeader.WL_PROXY_CLIENT_IP.getKey(), "192.168.4.4");
    request.addHeader(HttpRequestHeader.HTTP_CLIENT_IP.getKey(), "192.168.3.3");

    String actual = subject.getIpAddress(request);

    assertEquals("192.168.4.4", actual);
  }

  @Test
  void whenHttpClientIpHeaderIsPresentShouldTakePriorityOverLessImportantHeaders() {
    request.addHeader(HttpRequestHeader.HTTP_CLIENT_IP.getKey(), "192.168.5.5");
    request.addHeader(HttpRequestHeader.HTTP_X_FORWARDED_FOR.getKey(), "192.168.4.4");

    String actual = subject.getIpAddress(request);

    assertEquals("192.168.5.5", actual);
  }
}
