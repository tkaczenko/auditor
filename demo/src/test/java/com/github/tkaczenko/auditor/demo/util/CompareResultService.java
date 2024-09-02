package com.github.tkaczenko.auditor.demo.util;

import static org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE;

import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

@Slf4j
@SuppressWarnings("PMD.GuardLogStatement")
public class CompareResultService implements AssertJson {

  private static final Pattern RTRIM = Pattern.compile("\\s+$");
  private static final String S_EXPECTED_VALUE_S_PARSED_VALUE_S =
      "    %s -> expectedValue [%s] parsedValue [%s] ";
  private static final String S_ERROR_MESS_S_EXPECTED_VALUE_S_PARSED_VALUE_S =
      "    %s -> assertionError as %s %n%s %nExpected: %s %n     got: %s";
  private static final int INITIAL_SIZE = 22;
  private static final int LIST_SIZE_TO_COMPARE_THRESHOLD = 10;

  private final ObjectDiffer objectComparator;
  private final CustomComparator jsonComparator;

  public CompareResultService() {
    objectComparator = getObjectComparator();
    jsonComparator = getJsonComparator();
  }

  private ObjectDiffer getObjectComparator() {
    return createObjectDifferBuilder()
        .inclusion()
        .exclude()
        .propertyName("requestId")
        .propertyName("transactionId")
        .propertyName("traceId")
        .propertyName("url")
        .propertyName("spendTimeInMs")
        .propertyName("createDateTime")
        .and()
        .build();
  }

  private CustomComparator getJsonComparator() {
    return new CustomComparator(
        NON_EXTENSIBLE,
        new Customization("Matched-Stub-Id", (o1, o2) -> true),
        new Customization("matched-stub-id", (o1, o2) -> true),
        new Customization("Transfer-Encoding", (o1, o2) -> true),
        new Customization("accept", (o1, o2) -> true),
        new Customization("accept-encoding", (o1, o2) -> true),
        new Customization("connection", (o1, o2) -> true),
        new Customization("content-length", (o1, o2) -> true),
        new Customization("content-type", (o1, o2) -> true),
        new Customization("host", (o1, o2) -> true),
        new Customization("user-agent", (o1, o2) -> true));
  }

  private ObjectDifferBuilder createObjectDifferBuilder() {
    ObjectDifferBuilder builder = ObjectDifferBuilder.startBuilding();
    builder
        .comparison()
        .ofType(String.class)
        .toUse(
            (node, type, working, base) -> {
              if (StringUtils.compare(
                      StringUtils.trim((String) working), StringUtils.trim((String) base))
                  == 0) {
                node.setState(DiffNode.State.UNTOUCHED);
              } else {
                node.setState(DiffNode.State.CHANGED);
              }
            });
    return builder;
  }

  public <T> String compareWithExclusions(
      Supplier<Optional<? extends T>> actualSupplier,
      Supplier<Optional<? extends T>> expectedSupplier) {
    return compareObjects(actualSupplier, expectedSupplier, objectComparator);
  }

  public <T> String compareListsWithExclusions(
      Supplier<List<? extends T>> actualSupplier, Supplier<List<? extends T>> expectedSupplier) {
    return compareLists(actualSupplier, expectedSupplier, objectComparator);
  }

  private <T> String compareLists(
      Supplier<List<? extends T>> actualSupplier,
      Supplier<List<? extends T>> expectedSupplier,
      ObjectDiffer objectDiffer) {
    List<? extends T> actualList = actualSupplier.get();
    List<? extends T> expectedList = expectedSupplier.get();

    log.info(
        "Comparing lists of {} with element: expectedSize={}, actualSize={}",
        expectedList.isEmpty() ? null : expectedList.get(0).getClass().getSimpleName(),
        expectedList.size(),
        actualList.size());

    StringBuilder output = new StringBuilder(INITIAL_SIZE);
    if (actualList.isEmpty()) {
      if (expectedList.isEmpty()) {
        return output.toString();
      }
      output.append("Can not find record");
    } else {
      int actualListSize = actualList.size();
      int expectedListSize = expectedList.size();
      if (actualListSize == expectedListSize) {
        if (expectedListSize > LIST_SIZE_TO_COMPARE_THRESHOLD) {
          output.append(
              String.format(
                  "count of expected records is too big: actualList=%s, expectedList=%s",
                  actualListSize, expectedListSize));
        }
        if (actualListSize > LIST_SIZE_TO_COMPARE_THRESHOLD) {
          output.append(
              String.format(
                  "count of actual records is too big: actualList=%s, expectedList=%s",
                  actualListSize, expectedListSize));
        }
        compareOnyByOne(actualList, expectedList, objectDiffer, output);
      } else {
        output.append(
            String.format(
                "count of records is not the same: actualList=%s, expectedList=%s",
                actualListSize, expectedListSize));
      }
    }
    return output.toString();
  }

  private <T> void compareOnyByOne(
      List<? extends T> actualList,
      List<? extends T> expectedList,
      ObjectDiffer objectDiffer,
      StringBuilder output) {
    for (int i = 0; i < actualList.size(); i++) {
      T actualObject = actualList.get(i);
      T expectedObject = expectedList.get(i);
      log.info(
          "Comparing expected {} with actual of {}", i, expectedObject.getClass().getSimpleName());
      String compared =
          compareObjects(
              () -> Optional.of(actualObject), () -> Optional.of(expectedObject), objectDiffer);
      if (!compared.isEmpty()) {
        log.error(
            "Objects of {} are NOT equal: expected={}, actual={}",
            expectedObject.getClass().getSimpleName(),
            i,
            i);
        output.append(compared).append('\n');
      }
    }
  }

  private <T> String compareObjects(
      Supplier<Optional<? extends T>> routerSupplier,
      Supplier<Optional<? extends T>> expectedSupplier,
      ObjectDiffer objectDiffer) {
    Optional<? extends T> actualObjectOptional = routerSupplier.get();
    Optional<? extends T> expectedObjectOptional = expectedSupplier.get();

    StringBuilder output = new StringBuilder(INITIAL_SIZE);
    if (actualObjectOptional.isPresent()) {
      T actualObject = actualObjectOptional.get();
      if (expectedObjectOptional.isPresent()) {
        T expectedObject = expectedObjectOptional.get();
        compareObjects(objectDiffer, expectedObject, actualObject, output);
      } else {
        output.append("Cannot find expected record");
      }
    } else if (expectedObjectOptional.isPresent()) {
      output.append("Cannot find actual record");
    }

    return output.toString();
  }

  private <T> void compareObjects(
      ObjectDiffer objectDiffer, T expectedObject, T actualObject, StringBuilder output) {
    DiffNode diffNode = objectDiffer.compare(expectedObject, actualObject);
    if (diffNode.hasChanges()) {
      diffNode.visit(
          (node, visit) -> {
            if (node.hasChanges() && !node.isRootNode()) {
              String expected = safeRetrieveValue(node, expectedObject);
              String actual = safeRetrieveValue(node, actualObject);
              String message =
                  compareFields(
                      expectedObject.getClass(), node.getPropertyName(), expected, actual);
              if (message != null) {
                output.append('\n').append(message);
              }
            }
          });
    }
  }

  private String compareFields(
      Class<?> clazz, String propertyName, String expected, String actual) {
    String expectedNormalized = normalizeLineSeparators(expected);
    String actualNormalized = normalizeLineSeparators(actual);
    if (isJson(expectedNormalized) && isJson(actualNormalized)) {
      // Compare JSONs
      try {
        log.info("compare property {} in {} as JSON", propertyName, clazz.getSimpleName());
        assertEquals(expectedNormalized, actualNormalized, jsonComparator);
      } catch (JSONException | AssertionError e) {
        return String.format(
            S_ERROR_MESS_S_EXPECTED_VALUE_S_PARSED_VALUE_S,
            propertyName,
            "JSON",
            e.getMessage(),
            expectedNormalized,
            actualNormalized);
      }
    } else if (!expectedNormalized.equals(actualNormalized)) {
      // Compare TXTs
      return String.format(
          S_EXPECTED_VALUE_S_PARSED_VALUE_S, propertyName, expectedNormalized, actualNormalized);
    }
    return null;
  }

  private <T> String safeRetrieveValue(DiffNode diffNode, T target) {
    Object value = diffNode.canonicalGet(target);
    return value == null
        ? StringUtils.EMPTY
        : RTRIM.matcher(value.toString()).replaceAll(StringUtils.EMPTY);
  }

  private String normalizeLineSeparators(String str) {
    return str.replaceAll("(\r\n|\n)", StringUtils.SPACE)
        .replaceAll(String.format("%s", System.lineSeparator()), StringUtils.SPACE);
  }
}
