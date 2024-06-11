package com.github.tkaczenko.auditor.demo;

import com.github.tkaczenko.auditor.starter.EnableHttpAuditing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableHttpAuditing
@SuppressWarnings("PMD.UseUtilityClass")
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
