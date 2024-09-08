package io.github.tkaczenko.auditor.demo.service.filter;

import io.github.tkaczenko.auditor.core.service.AuditDateTimeProvider;
import io.github.tkaczenko.auditor.core.service.AuditFacade;
import io.github.tkaczenko.auditor.core.service.reader.BodyHttpReaderService;
import io.github.tkaczenko.auditor.core.service.reader.HeadersHttpReaderService;
import io.github.tkaczenko.auditor.inbound.AuditInboundRequestFilter;
import io.github.tkaczenko.auditor.inbound.IpAddressSupplier;
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
