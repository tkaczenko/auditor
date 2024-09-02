plugins {
    id("auditor.library-conventions")
}

description = """
Provides auditing of outbound HTTP requests and responses within the Feign client. 
It leverages Feign's logger to capture outgoing requests and responses, 
and persists the audit data.
""".trimIndent()

dependencies {
    api(project(":auditor.core"))
    api(project(":auditor.outbound"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.cloud.starter.openfeign)
}
