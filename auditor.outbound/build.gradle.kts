plugins {
    id("auditor.library-conventions")
}

dependencies {
    api(project(":auditor.core"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.cloud.starter.openfeign)
}
