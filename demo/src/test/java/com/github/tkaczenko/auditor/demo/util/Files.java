package com.github.tkaczenko.auditor.demo.util;

import lombok.experimental.UtilityClass;

@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
@UtilityClass
public final class Files {

  @UtilityClass
  public static class Inbound {

    public static final String INBOUND_SUCCESS_REQUEST =
        "/__files/inbound/inbound-success-request.json";
    public static final String INBOUND_SUCCESS_RESPONSE =
        "/__files/inbound/inbound-success-response.json";
    public static final String INBOUND_MULTI_1_SUCCESS_REQUEST =
        "/__files/inbound/inbound-multi-1-success-request.json";
    public static final String INBOUND_MULTI_1_SUCCESS_RESPONSE =
        "/__files/inbound/inbound-multi-1-success-response.json";
    public static final String INBOUND_MULTI_2_SUCCESS_REQUEST =
        "/__files/inbound/inbound-multi-2-success-request.json";
    public static final String INBOUND_MULTI_2_SUCCESS_RESPONSE =
        "/__files/inbound/inbound-multi-2-success-response.json";
    public static final String INBOUND_SUCCESS_REQUEST_TO_REST_TEMPLATE =
        "/__files/inbound/inbound-success-request-to-rest-template.json";
    public static final String INBOUND_SUCCESS_RESPONSE_TO_REST_TEMPLATE =
        "/__files/inbound/inbound-success-response-to-rest-template.json";
    public static final String INBOUND_SUCCESS_REQUEST_TO_FEIGN =
        "/__files/inbound/inbound-success-request-to-feign.json";
    public static final String INBOUND_SUCCESS_RESPONSE_TO_FEIGN =
        "/__files/inbound/inbound-success-response-to-feign.json";
  }

  @UtilityClass
  public static class Outbound {

    @UtilityClass
    public static class RestTemplate {

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
