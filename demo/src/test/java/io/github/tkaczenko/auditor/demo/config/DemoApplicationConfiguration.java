package io.github.tkaczenko.auditor.demo.config;

import io.github.tkaczenko.auditor.demo.DemoApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile({"test"})
@Import(DemoApplication.class)
@TestConfiguration
public class DemoApplicationConfiguration {
  @Bean
  public FixedCurrentTimeProvider currentTimeProvider() {
    return new FixedCurrentTimeProvider();
  }
}
