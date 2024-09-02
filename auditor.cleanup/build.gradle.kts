plugins {
    id("auditor.library-conventions")
}

description = """
Provides scheduled cleanup of audit data from the database based on the configured cron expression or fixed delay. 
It leverages the ShedLock library to ensure that only one instance of the cleanup job runs at a time, preventing data corruption or race conditions in a clustered environment. 
The cleanup module is responsible for deleting audit records older than a specified retention period, freeing up disk space and maintaining optimal database performance.
""".trimIndent()

dependencies {
    api(project(":auditor.core"))

    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.shedlock.spring)
    implementation(libs.shedlock.provider.jdbc)
}
