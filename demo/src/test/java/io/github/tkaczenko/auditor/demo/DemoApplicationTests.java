package io.github.tkaczenko.auditor.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@SuppressWarnings("PMD.UncommentedEmptyMethodBody")
class DemoApplicationTests {

  @Test
  void contextLoads() {
    // app is able to load Spring context
  }
}
