package com.github.tkaczenko.auditor.demo;

import com.github.tkaczenko.auditor.core.EnableAuditor;
import com.github.tkaczenko.auditor.inbound.EnableInboundAuditing;
import com.github.tkaczenko.auditor.outbound.EnableOutboundAuditing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAuditor
@EnableInboundAuditing
@EnableOutboundAuditing
@SuppressWarnings("PMD.UseUtilityClass")
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
