package io.github.tkaczenko.auditor.cleanup.config;

import javax.sql.DataSource;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configures the Auditor, including scheduling and locking mechanisms.
 *
 * <p>This configuration class sets up the necessary beans and configurations for the Auditor
 * application. It enables scheduling and scheduler locking, and scans the base package for
 * component classes. The lock provider is configured to use a JDBC-based locking mechanism, which
 * ensures that scheduled tasks are properly synchronized across multiple instances of the
 * application.
 */
@Configuration
@ConditionalOnClass(name = "io.github.tkaczenko.auditor.cleanup.EnableScheduledCleanup")
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
@ComponentScan(basePackages = "io.github.tkaczenko.auditor.cleanup")
public class AuditorConfig {
  /**
   * Provides a lock provider for the scheduler, using a JDBC-based locking mechanism.
   *
   * @param dataSource the data source to be used for the lock provider
   * @return the lock provider instance
   */
  @Bean
  public LockProvider lockProvider(DataSource dataSource) {
    return new JdbcTemplateLockProvider(dataSource);
  }
}
