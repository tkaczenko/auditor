package com.github.tkaczenko.auditor.demo.service.client.resttemplate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestTemplateRequest {
  private String restTemplateRequestTransactionId;
}
