package io.github.tkaczenko.auditor.demo.service.filter;

import io.github.tkaczenko.auditor.core.internal.service.AuditDateTimeProvider;
import io.github.tkaczenko.auditor.core.internal.AuditFacade;
import io.github.tkaczenko.auditor.core.internal.factory.body.BodyHttpReaderService;
import io.github.tkaczenko.auditor.core.internal.factory.headers.HeadersHttpReaderService;
import io.github.tkaczenko.auditor.inbound.api.AuditInboundRequestFilter;
import io.github.tkaczenko.auditor.inbound.internal.service.IpAddressSupplier;
import org.springframework.stereotype.Component;

@Component
public class DemoControllerAuditRequestFilter extends AuditInboundRequestFilter {

  public DemoControllerAuditRequestFilter(
      AuditDateTimeProvider auditDateTimeProvider,
      AuditFacade auditFacade,
      BodyHttpReaderService bodyReaderService,
      HeadersHttpReaderService headersReaderService,
      IpAddressSupplier ipAddressSupplier) {
    super(
        auditDateTimeProvider,
        auditFacade,
        bodyReaderService,
        headersReaderService,
        ipAddressSupplier);
  }

  @Override
  public String getProvider() {
    return "inbound-provider";
  }

  @Override
  public String getRequestType() {
    return "inbound-requestType";
  }

  @Override
  public String[] getUrlPatterns() {
    return new String[] {"/test/inbound"};
  }
}
