plugins {
    id("auditor.application-conventions")
}

description = """
 Spring Boot application that demonstrates the usage of the Auditor library. 
 It includes integration tests to verify the functionality of the auditing features for both inbound and outbound HTTP requests and responses. 
 The demo application serves as a reference implementation and can be used as a starting point for integrating the Auditor library into your own Spring Boot projects.
""".trimIndent()

dependencies {
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.java.diff)
    testImplementation(libs.wiremock)
    testImplementation(libs.rest.assured)
    testImplementation(libs.assertj)
    testImplementation(libs.awaitility)
}
