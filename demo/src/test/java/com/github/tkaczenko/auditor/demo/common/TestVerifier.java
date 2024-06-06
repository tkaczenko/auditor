package com.github.tkaczenko.auditor.demo.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tkaczenko.auditor.demo.model.AuditRecord;
import com.github.tkaczenko.auditor.demo.repository.AuditRecordRepository;
import com.github.tkaczenko.auditor.demo.util.CompareResultService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuppressWarnings("checkstyle:VisibilityModifierCheck")
public class TestVerifier {

  private final CompareResultService compareResultService = new CompareResultService();

  @Autowired private AuditRecordRepository auditRequestRepository;

  protected void verifyAuditRecords(String expectedTransactionId, String actualTransactionId) {
    log.info(
        "Verifying AuditRequest table of workflow by transactionId: expected={}, actual={}",
        expectedTransactionId,
        actualTransactionId);

    var matcher =
        ExampleMatcher.matching()
            .withIgnorePaths("requestId", "spendTimeInMs")
            .withIgnoreNullValues();
    Function<FluentQuery.FetchableFluentQuery<AuditRecord>, List<AuditRecord>>
        orderByCreateDateTimeDescending =
            q -> q.sortBy(Sort.by("createDateTime").descending()).all();

    var expected = AuditRecord.builder().transactionId(expectedTransactionId).build();
    var actual = AuditRecord.builder().transactionId(actualTransactionId).build();

    String auditRequestsResult =
        compareResultService.compareListsWithExclusions(
            () ->
                auditRequestRepository.findBy(
                    Example.of(actual, matcher), orderByCreateDateTimeDescending),
            () ->
                getOrEmpty(
                    expectedTransactionId,
                    () ->
                        auditRequestRepository.findBy(
                            Example.of(expected, matcher), orderByCreateDateTimeDescending)));
    assertThat(auditRequestsResult).isEmpty();
  }

  private <T> List<T> getOrEmpty(Object expected, Supplier<List<T>> supplier) {
    return Optional.ofNullable(expected).map(i -> supplier.get()).orElse(Collections.emptyList());
  }
}
