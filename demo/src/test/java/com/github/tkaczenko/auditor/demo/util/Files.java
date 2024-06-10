package com.github.tkaczenko.auditor.demo.util;

import lombok.experimental.UtilityClass;

@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
@UtilityClass
public final class Files {

  @UtilityClass
  public static class Inbound {

    public static final String INBOUND_SUCCESS_REQUEST =
        "/__files/inbound/inbound-success-request.json";
    public static final String INBOUND_SUCCESS_REQUEST_TO_REST_TEMPLATE =
        "/__files/inbound/inbound-success-request-to-rest-template.json";
    public static final String INBOUND_SUCCESS_RESPONSE =
        "/__files/inbound/inbound-success-response.json";
    public static final String INBOUND_SUCCESS_RESPONSE_TO_REST_TEMPLATE =
            "/__files/inbound/inbound-success-response-to-rest-template.json";
    public static final String INBOUND_FAILURE_REQUEST =
        "/__files/inbound/inbound-failure-request.json";
    public static final String INBOUND_FAILURE_RESPONSE =
        "/__files/inbound/inbound-failure-response.json";
  }

  @UtilityClass
  public static class Outbound {

    @UtilityClass
    public static class RestTemplate {

      public static final String REST_TEMPLATE_SUCCESS_REQUEST =
          "/__files/outbound/rest-template/rest-template-success-request.json";
      public static final String REST_TEMPLATE_SUCCESS_RESPONSE =
          "/__files/outbound/rest-template/rest-template-success-response.json";
    }

    @UtilityClass
    public static class Feign {

      public static final String FEIGN_SUCCESS_REQUEST =
          "/__files/outbound/feign/feign-success-request.json";
      public static final String FEIGN_SUCCESS_RESPONSE =
          "/__files/outbound/feign/feign-success-response.json";
    }
  }
}
