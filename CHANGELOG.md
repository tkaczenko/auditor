# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased](https://github.com/tkaczenko/auditor/compare/v0.0.1...HEAD)

## [0.0.1](https://github.com/tkaczenko/auditor/releases/tag/v0.0.1) - 2024-07-01

### Added
- Initial release of the project.
- Feature: Persistence of audit data, including payloads, headers, and metadata, in an SQL database.
- Feature: Automatic auditing of inbound HTTP requests and responses to application's controllers.
- Feature: Automatic auditing of outbound HTTP requests and responses within
  - `RestTemplate` client
  - `Feign` client.
- Feature: Seamless integration with the Spring Boot ecosystem using of annotations, `@EnableHttpAuditing`.
- Feature: Seamless integration with the Spring Boot ecosystem using of annotations, `@EnableInboundAuditing`, `@EnableOutboundAuditing`, `@EnableScheduledCleanup`.
- Feature: Automatic scheduled cleaning up audited data based on `ShedLock`.
- API: Introduced properties to configure the auditor
  - ```yaml
    auditor:
      extendable-from-mdc: true # if true, AuditRequestResponse will be populated from MDC
    scheduling:
      cron: "0 0 * * * ?" # clean up audited data every day
      fixed-delay: 3600000 # clean up audited data every hour
    ```
