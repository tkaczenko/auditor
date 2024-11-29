package org.springframework.test.context.transaction;

import org.springframework.core.annotation.Order;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

// Workaround from https://github.com/spring-projects/spring-framework/issues/33383
@Order(3000) // TransactionalTestExecutionListener is order 4000
public class InheritedTransactionRemover implements TestExecutionListener {
  private final ThreadLocal<Boolean> transactionRemoved = ThreadLocal.withInitial(() -> false);

  @Override
  public void beforeTestMethod(TestContext testContext) {
    if (!transactionRemoved.get()) {
      TransactionContextHolder.removeCurrentTransactionContext();
      transactionRemoved.set(true);
    }
  }
}