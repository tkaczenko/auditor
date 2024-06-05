package com.github.tkaczenko.auditor.demo.service.filter;

import com.github.tkaczenko.auditor.core.service.AuditDateTimeProvider;
import com.github.tkaczenko.auditor.core.service.AuditService;
import com.github.tkaczenko.auditor.core.service.reader.BodyHttpReaderService;
import com.github.tkaczenko.auditor.core.service.reader.HeadersHttpReaderService;
import com.github.tkaczenko.auditor.inbound.AuditInboundRequestFilter;
import com.github.tkaczenko.auditor.inbound.IpAddressSupplier;

public class DemoControllerAuditRequestFilter extends AuditInboundRequestFilter {

  public DemoControllerAuditRequestFilter(
      AuditDateTimeProvider auditDateTimeProvider,
      AuditService auditService,
      BodyHttpReaderService bodyReaderService,
      HeadersHttpReaderService headersReaderService,
      IpAddressSupplier ipAddressSupplier) {
    super(
        auditDateTimeProvider,
        auditService,
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
}
