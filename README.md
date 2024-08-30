# Auditor

[//]: # ([![Maven Central]&#40;https://img.shields.io/maven-central/v/tkaczenko/auditor&#41;]&#40;https://central.sonatype.com/artifact/tkaczenko/auditor&#41;)
[//]: # ([![Javadoc]&#40;https://javadoc.io/badge2/tkaczenko/auditor/javadoc.svg&#41;]&#40;https://javadoc.io/doc/tkaczenko/auditor&#41;)
[![Build](https://github.com/tkaczenko/auditor/actions/workflows/build.yml/badge.svg)](https://github.com/tkaczenko/auditor/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=tkaczenko_auditor&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=tkaczenko_auditor)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=tkaczenko_auditor&metric=coverage)](https://sonarcloud.io/summary/new_code?id=tkaczenko_auditor)
[![Tests](https://img.shields.io/sonar/total_tests/tkaczenko_auditor?server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/project/overview?id=tkaczenko_auditor)

This is a **Spring Boot** library project that provides comprehensive auditing capabilities for **HTTP** requests and responses in a **Spring Boot** application. It automatically audits inbound requests and responses to application controllers, as well as outbound requests and responses sent via clients. The audit data, including request/response payloads, headers, and metadata, is persisted in an **SQL** database.

## Purpose

The primary purpose of this project is to enable auditing and logging of interactions with third-party systems, ensuring transparency and traceability of external communications. By integrating this library into a Spring Boot application, developers can easily audit and monitor all incoming and outgoing HTTP traffic, facilitating debugging, compliance, and security analysis.

## Features

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

### Configuration

Configuration properties for the Auditor library can be added to your `application.properties` file:

```properties
auditor.extendable-from-mdc=true
```

or `application.yml`:

```yaml
  auditor:
    extendable-from-mdc: true
```

## Modules

There are several modules in Auditor. Here is a quick overview:

### auditor.core

The `auditor.core` module is the main library providing persistence of audit data, including payloads, headers, and metadata, in an SQL database. It defines the core entities and repositories for storing and retrieving audit records.

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
