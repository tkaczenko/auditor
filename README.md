# Auditor

This is a **Spring Boot** library project that provides comprehensive auditing capabilities for **HTTP** requests and responses in a **Spring Boot** application. It automatically audits inbound requests and responses to application controllers, as well as outbound requests and responses sent via clients. The audit data, including request/response payloads, headers, and metadata, is persisted in an **SQL** database.

## Purpose

The primary purpose of this project is to enable auditing and logging of interactions with third-party systems, ensuring transparency and traceability of external communications. By integrating this library into a Spring Boot application, developers can easily audit and monitor all incoming and outgoing HTTP traffic, facilitating debugging, compliance, and security analysis.

## Features

- Automatic auditing of inbound HTTP requests and responses.
- Automatic auditing of outbound HTTP requests and responses via `RestTemplate` or `Feign` clients.
- Persistence of audit data, including payloads, headers, and metadata, in an SQL database.
- Seamless integration with the Spring Boot ecosystem.
- Facilitates debugging, compliance, and security analysis in distributed applications.

## Installation and Getting Started

To install the **Auditor** library in your **Spring Boot** application, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.github.tkaczenko</groupId>
    <artifactId>auditor</artifactId>
    <version>0.0.1</version>
</dependency>
```

Or in `build.gradle`:

```groovy
implementation 'com.github.tkaczenko:auditor:0.0.1'
```

Or in `build.gradle.kts`:

```kotlin
implementation("com.github.tkaczenko:auditor:0.0.1")
```

### Usage

Once the dependency is added, the library could automatically start auditing HTTP requests and responses. Configuration options are available to customize the auditing behavior, such as specifying which endpoints to audit, and more.

```java
@EnableHttpAuditing
```
This annotation enables all available configurations.

```java
@EnableInboundAuditing
```
This annotation enables auditing of inbound HTTP requests and responses to your application's controllers.

```java
@EnableOutboundAuditing
```
This annotation enables auditing of outbound HTTP requests and responses sent via RestTemplate or Feign clients.

```java
@EnableScheduledCleanup
```
This annotation enables scheduled cleanup of audit data from the database based on the configured cron expression or fixed delay.

You can use one or more of these annotations depending on your requirements. For example, to enable all auditing features, you can add the following to your main application class:

```java
@EnableHttpAuditing
public class YourApplication {
    // ...
}
```

or

```java
@EnableInboundAuditing
@EnableOutboundAuditing
@EnableScheduledCleanup
@SpringBootApplication
public class YourApplication {
    // ...
}
```

### Configuration

Configuration properties for the Auditor library can be added to your `application.properties` file:

```properties
auditor.extendable-from-mdc=true
auditor.scheduling.cron="0 0 * * * ?"
auditor.scheduling.fixed-delay=3600000
```

or `application.yml`:

```yaml
  auditor:
    extendable-from-mdc: true
    scheduling:
      cron: "0 0 * * * ?"
      fixed-delay: 3600000
```

## Modules

There are several modules in Auditor. Here is a quick overview:

### demo

The `demo` module is a Spring Boot application that demonstrates the usage of the Auditor library. It includes integration tests to verify the functionality of the auditing features for both inbound and outbound HTTP requests and responses. The demo application serves as a reference implementation and can be used as a starting point for integrating the Auditor library into your own Spring Boot projects.

### auditor.core

The `auditor.core` module is the main library providing persistence of audit data, including payloads, headers, and metadata, in an SQL database. It defines the core entities and repositories for storing and retrieving audit records.

### auditor.inbound

The `inbound` module provides automatic auditing of inbound HTTP requests and responses to the application's controllers. It leverages Spring's request interceptor mechanism to capture incoming requests and responses, and persists the audit data using the `core` module.

### auditor.outbound

The `auditor.outbound` module provides automatic auditing of outbound HTTP requests and responses within the `RestTemplate` client. It intercepts outgoing requests and responses using Spring's `ClientHttpRequestInterceptor`, and persists the audit data using the `core` module.

### auditor.outbound.feign

The `auditor.outbound.feign` module provides automatic auditing of outbound HTTP requests and responses within the Feign client. It leverages Feign's logger to capture outgoing requests and responses, and persists the audit data using the `core` module.

### auditor.cleanup

The `auditor.cleanup` module provides scheduled cleanup of audit data from the database based on the configured cron expression or fixed delay. It leverages the ShedLock library to ensure that only one instance of the cleanup job runs at a time, preventing data corruption or race conditions in a clustered environment. The cleanup module is responsible for deleting audit records older than a specified retention period, freeing up disk space and maintaining optimal database performance.

## Built with
- Java
- Kotlin
- Gradle
- Spring Boot
- Spring MVC
- Spring Data JPA
- H2 Database
- ShedLock

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE.md) file for details.

## Contributing

Contributions are welcome! Please see the [CONTRIBUTING](CONTRIBUTING.md) file for guidelines on how to contribute to this project.

## Contact

For any inquiries or support, please contact [andrii.tkaczenko@gmail.com](mailto:andrii.tkaczenko@gmail.com).
