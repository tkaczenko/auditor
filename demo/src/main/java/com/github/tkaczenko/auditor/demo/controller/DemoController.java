package com.github.tkaczenko.auditor.demo.controller;

import com.github.tkaczenko.auditor.demo.config.PropertiesConfig.FeignClientProperties;
import com.github.tkaczenko.auditor.demo.model.dto.ErrorResponse;
import com.github.tkaczenko.auditor.demo.model.dto.Request;
import com.github.tkaczenko.auditor.demo.model.dto.Response;
import com.github.tkaczenko.auditor.demo.service.client.feign.OutboundFeignClient;
import com.github.tkaczenko.auditor.demo.service.client.feign.dto.FeignRequest;
import com.github.tkaczenko.auditor.demo.service.client.feign.dto.FeignResponse;
import com.github.tkaczenko.auditor.demo.service.client.resttemplate.OutboundRestTemplateClient;
import com.github.tkaczenko.auditor.demo.service.client.resttemplate.dto.RestTemplateResponse;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class DemoController {

  private final OutboundRestTemplateClient outboundRestTemplateClient;
  private final OutboundFeignClient outboundFeignClient;
  private final FeignClientProperties feignClientProperties;
  private final Tracer tracer;

  @PostMapping(path = "test/inbound", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response> testInbound(@RequestBody Request request) {
    MDC.put("transactionId", request.getRequestTransactionId());
    if (log.isInfoEnabled()) {
      log.info("Request: {}", request);
    }
    return ResponseEntity.ok(
        Response.builder().responseTransactionId(request.getRequestTransactionId()).build());
  }

  @PostMapping(path = "test/outbound/restTemplate", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response> testOutboundRestTemplate(@RequestBody Request request) {
    MDC.put("transactionId", request.getRequestTransactionId());
    if (log.isInfoEnabled()) {
      log.info("Request: {}", request);
    }
    RestTemplateResponse response =
            outboundRestTemplateClient.get();
    MDC.clear();
    return ResponseEntity.ok(
        Response.builder()
            .responseTransactionId(response.getRestTemplateResponseTransactionId())
            .build());
  }

  @PostMapping(path = "test/outbound/feign", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response> testOutboundFeign(@RequestBody Request request) {
    MDC.put("transactionId", request.getRequestTransactionId());
    if (log.isInfoEnabled()) {
      log.info("Request: {}", request);
    }
    FeignResponse response =
        outboundFeignClient.post(
            feignClientProperties.getClientReference(),
            FeignRequest.builder()
                .feignRequestTransactionId(request.getRequestTransactionId())
                .build());
    MDC.clear();
    return ResponseEntity.ok(
            Response.builder()
                    .responseTransactionId(response.getFeignResponseTransactionId())
                    .build());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> clientError(RuntimeException exception) {
    if (log.isErrorEnabled()) {
      log.error(exception.getMessage(), exception);
    }
    return new ResponseEntity<>(
        new ErrorResponse(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
