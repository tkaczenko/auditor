package io.github.tkaczenko.auditor.demo.service.client.feign;

import io.github.tkaczenko.auditor.demo.config.FeignConfig;
import io.github.tkaczenko.auditor.demo.service.client.feign.dto.FeignRequest;
import io.github.tkaczenko.auditor.demo.service.client.feign.dto.FeignResponse;
import io.github.tkaczenko.auditor.outbound.api.AuditedOutboundCall;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "outbound-feign-client",
    url = "${feign.url}",
    configuration = FeignConfig.class)
public interface OutboundFeignClient {

  @AuditedOutboundCall(
      provider = "outbound-feign-provider",
      requestType = "outbound-feign-request-type")
  @PostMapping
  FeignResponse post(
      @RequestParam(name = "clientReference") String clientReference,
      @RequestBody FeignRequest request);
}
