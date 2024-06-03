# Auditor

This is a **Spring Boot** library project that provides comprehensive auditing capabilities for **HTTP** requests and responses in a **Spring Boot** application. It automatically audits inbound requests and responses to application controllers, as well as outbound requests and responses sent via `RestTemplate` or `Feign` clients. The audit data, including request/response payloads, headers, and metadata, is persisted in an **SQL** database.

## Purpose

The primary purpose of this project is to enable auditing and logging of interactions with third-party systems, ensuring transparency and traceability of external communications. By integrating this library into a Spring Boot application, developers can easily audit and monitor all incoming and outgoing HTTP traffic, facilitating debugging, compliance, and security analysis.

## Features

- Automatic auditing of inbound HTTP requests and responses.
- Automatic auditing of outbound HTTP requests and responses via `RestTemplate` or `Feign` clients.
- Persistence of audit data, including payloads, headers, and metadata, in an SQL database.
- Seamless integration with the Spring Boot ecosystem.
- Facilitates debugging, compliance, and security analysis in distributed applications.

## Installation

To install the **Auditor** library in your **Spring Boot** application, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.yourorganization</groupId>
    <artifactId>auditor</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Usage
Once the dependency is added, the library will automatically start auditing HTTP requests and responses. Configuration options are available to customize the auditing behavior, such as specifying which endpoints to audit, excluding certain headers, and more.

### Configuration
Configuration properties for the Auditor library can be added to your application.properties or application.yml file:

```properties
auditor.enabled=true
auditor.database.url=jdbc:mysql://localhost:3306/auditdb
auditor.database.username=root
auditor.database.password=secret
```

```yaml
auditor:
  enabled: true
  database:
    url: jdbc:mysql://localhost:3306/auditdb
    username: root
    password: secret
```

## License
This project is licensed under the MIT License. See the LICENSE file for details.

## Contributing
Contributions are welcome! Please see the CONTRIBUTING file for guidelines on how to contribute to this project.

## Contact
For any inquiries or support, please contact andrii.tkaczenko@gmail.com.

