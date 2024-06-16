plugins {
    id("auditor.library-conventions")
}

dependencies {
    api(project(":auditor.core"))

    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.shedlock.spring)
    implementation(libs.shedlock.provider.jdbc)
}
