plugins {
    id("auditor.library-conventions")
}

description = """
Automatically configures and integrates Spring Boot auditing capabilities for HTTP requests and responses.
The library includes: 
auditor.core: Automatically audits inbound HTTP requests and responses to the application's controllers;
auditor.inbound: Handles auditing of inbound HTTP requests and responses;
auditor.outbound: Manages auditing of outbound HTTP requests and responses via the RestTemplate client.
Audit data, including payloads, headers, and metadata, is persisted in an SQL database.
""".trimIndent()

dependencies {
    api(project(":auditor.core"))
    api(project(":auditor.inbound"))
    api(project(":auditor.outbound"))

    implementation(libs.spring.boot.starter.web)
}