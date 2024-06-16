plugins {
    id("auditor.library-conventions")
}

description = """
Provides persistence of audit data, including payloads, headers, and metadata, in an SQL database. 
It defines the core entities and repositories for storing and retrieving audit records.
""".trimIndent()

dependencies {
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.web)
}
