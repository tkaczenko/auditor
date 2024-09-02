plugins {
    id("auditor.library-conventions")
}

description = """
Provides automatic auditing of outbound HTTP requests and responses within the RestTemplate client. 
It intercepts outgoing requests and responses using Spring's `ClientHttpRequestInterceptor`, 
and persists the audit data.
""".trimIndent()

dependencies {
    api(project(":auditor.core"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.cloud.starter.openfeign)
}
