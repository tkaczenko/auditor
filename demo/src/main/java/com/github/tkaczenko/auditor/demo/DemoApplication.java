package com.github.tkaczenko.auditor.demo;

import com.github.tkaczenko.auditor.cleanup.EnableScheduledCleanup;
import com.github.tkaczenko.auditor.inbound.EnableInboundAuditing;
import com.github.tkaczenko.auditor.outbound.EnableOutboundAuditing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableInboundAuditing
@EnableOutboundAuditing
@EnableScheduledCleanup
@SuppressWarnings("PMD.UseUtilityClass")
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
