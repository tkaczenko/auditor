plugins {
    id("auditor.application-conventions")
}

dependencies {
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.java.diff)
    testImplementation(libs.h2)
    testImplementation(libs.wiremock)
    testImplementation(libs.rest.assured)
}