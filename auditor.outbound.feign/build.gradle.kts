plugins {
    id("auditor.library-conventions")
}

dependencies {
    api(project(":auditor.core"))
    api(project(":auditor.outbound"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.cloud.starter.openfeign)
}
