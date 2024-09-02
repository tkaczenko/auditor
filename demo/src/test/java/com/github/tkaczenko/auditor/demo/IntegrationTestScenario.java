package com.github.tkaczenko.auditor.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tkaczenko.auditor.demo.model.dto.Request;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Value
@Builder
public class IntegrationTestScenario {

  private final Api api;
  private final Integration integration;
  private final String expectedTransactionId;

  public Provider getProvider() {
    return integration.getProviders().get(0);
  }

  public static class IntegrationTestScenarioBuilder {

    public IntegrationTestScenarioBuilder provider(Provider provider) {
      this.integration =
          Integration.builder().providers(Collections.singletonList(provider)).build();
      return this;
    }

    public IntegrationTestScenarioBuilder providers(List<Provider> providers) {
      this.integration = Integration.builder().providers(providers).build();
      return this;
    }
  }

  @Value
  @Builder
  @Slf4j
  public static class Api {

    private final String url;
    @Builder.Default private final HttpStatus status = HttpStatus.OK;
    private final String requestBody;

    private final String actualTransactionId;

    private final String responseBody;
    private final Class<?> responseBodyClass;
    private final Object responseBodyObject;

    public static class ApiBuilder {

      private final ObjectMapper objectMapper = new ObjectMapper();

      public ApiBuilder requestBody(String requestBody) {
        this.requestBody = requestBody;
        try {
          this.actualTransactionId =
              objectMapper.readValue(requestBody, Request.class).getRequestTransactionId();
        } catch (JsonProcessingException e) {
          log.warn("Invoice have NOT been parsed");
        }
        return this;
      }

      public ApiBuilder requestBody(Request request) throws JsonProcessingException {
        this.requestBody = toJson(request);
        this.actualTransactionId = request.getRequestTransactionId();
        return this;
      }

      public ApiBuilder responseBody(Class<?> responseBodyClass, String responseBody)
          throws JsonProcessingException {
        this.responseBodyObject = objectMapper.readValue(responseBody, responseBodyClass);
        this.responseBody = responseBody;
        return this;
      }

      private <T> String toJson(T object) throws JsonProcessingException {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(object);
      }
    }
  }

  @Value
  @Builder
  public static class Integration {

    private final String scenario;
    private final List<String> states;
    private final List<Provider> providers;

    public static class IntegrationBuilder {

      public IntegrationBuilder providers(List<Provider> providers) {
        List<Provider> list = new ArrayList<>();
        for (Provider provider : providers) {
          list.add(provider);
          for (int i = 1; i < provider.getCount(); i++) {
            list.add(provider);
          }
        }
        this.providers = list;
        this.scenario = getScenario(list);
        this.states = getStates(list);

        return this;
      }

      private String getScenario(List<Provider> providers) {
        return providers.stream()
            .map(Provider::getStatus)
            .map(HttpStatus::value)
            .map(String::valueOf)
            .collect(Collectors.joining("-"));
      }

      private List<String> getStates(List<Provider> providers) {
        List<String> states =
            IntStream.range(0, providers.size())
                .mapToObj(i -> String.format("%d-%s", i, providers.get(i).getStatus()))
                .collect(Collectors.toList());
        states.add(0, Scenario.STARTED);
        states.add(Scenario.STARTED);
        return states;
      }
    }
  }

  @Value
  @Builder
  public static class Provider {

    private final HttpStatus status;
    private final String requestBody;
    private final String responseBody;
    private final int count;
  }
}
