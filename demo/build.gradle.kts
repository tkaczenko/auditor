plugins {
    id("auditor.application-conventions")
}

dependencies {
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.java.diff)
    testImplementation(libs.h2)
    testImplementation(libs.wiremock)
    testImplementation(libs.rest.assured)

    implementation(project(":core"))
    implementation(project(":inbound"))
    implementation(project(":outbound"))
    implementation(project(":outbound-feign"))

    implementation(platform(libs.spring.boot))
    implementation(platform(libs.spring.cloud))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.cloud.starter.openfeign)
}
