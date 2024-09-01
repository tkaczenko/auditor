plugins {
    id("auditor.library-conventions")
}

description = """
Provides auditing of inbound HTTP requests and responses to the application's controllers. 
It leverages Spring's request interceptor mechanism to capture incoming requests and responses, 
and persists the audit data.
""".trimIndent()

dependencies {
    testImplementation(libs.spring.test)

    api(project(":auditor.core"))

    implementation(libs.spring.boot.starter.web)
}
