package io.github.tkaczenko.auditor.demo.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

public interface AssertJson {

  default void assertEquals(
      String expectedJson, String actualJson, CustomComparator customComparator)
      throws JSONException {
    JSONAssert.assertEquals(expectedJson, actualJson, customComparator);
  }

  default boolean isJson(String json) {
    if (StringUtils.isBlank(json)) {
      return false;
    }
    try {
      return new ObjectMapper()
          .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
          .readTree(json)
          .isObject();
    } catch (Exception e) {
      return false;
    }
  }
}
