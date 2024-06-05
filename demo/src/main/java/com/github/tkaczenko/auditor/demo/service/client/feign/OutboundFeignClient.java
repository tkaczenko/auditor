package com.github.tkaczenko.auditor.demo.service.client.feign;

import com.github.tkaczenko.auditor.demo.config.FeignConfiguration;
import com.github.tkaczenko.auditor.demo.service.client.feign.dto.FeignRequest;
import com.github.tkaczenko.auditor.demo.service.client.feign.dto.FeignResponse;
import com.github.tkaczenko.auditor.outbound.AuditedOutboundCall;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "outbound-feign-client",
    url = "${feign.url}",
    configuration = FeignConfiguration.class)
public interface OutboundFeignClient {

  @PostMapping
  @AuditedOutboundCall(
      provider = "outbound-feign-provider",
      requestType = "outbound-rest-template-request-type")
  FeignResponse getDigitalIdentityTrustUsInfoSearchVerification(
      @RequestParam String clientReference, @RequestBody FeignRequest request);
}
