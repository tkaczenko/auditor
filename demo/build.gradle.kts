plugins {
    id("auditor.application-conventions")
}

dependencies {
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.java.diff)
    testImplementation(libs.wiremock)
    testImplementation(libs.rest.assured)
    testImplementation(libs.assertj)
    testImplementation(libs.awaitility)
}
