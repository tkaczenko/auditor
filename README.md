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
Once the dependency is added, the library could automatically start auditing HTTP requests and responses. Configuration options are available to customize the auditing behavior, such as specifying which endpoints to audit, excluding certain headers, and more.

`@EnableInboundAuditing`
This annotation enables auditing of inbound HTTP requests and responses to your application's controllers.

`@EnableOutboundAuditing`
This annotation enables auditing of outbound HTTP requests and responses sent via RestTemplate or Feign clients.

`@EnableScheduledCleanup`
This annotation enables scheduled cleanup of audit data from the database based on the configured cron expression or fixed delay.

You can use one or more of these annotations depending on your requirements. For example, to enable all auditing features, you can add the following to your main application class:

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
Configuration properties for the Auditor library can be added to your `application.properties` or `application.yml` file:

```properties
auditor.extendable-from-mdc=true
auditor.scheduling.cron="0 0 * * * ?"
auditor.scheduling.fixed-delay=3600000
```

```yaml
  auditor:
    extendable-from-mdc: true
    scheduling:
      cron: "0 0 * * * ?"
      fixed-delay: 3600000
```

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE.md) file for details.

## Contributing
Contributions are welcome! Please see the [CONTRIBUTING](CONTRIBUTING.md) file for guidelines on how to contribute to this project.

## Contact
For any inquiries or support, please contact [andrii.tkaczenko@gmail.com](mailto:andrii.tkaczenko@gmail.com).
