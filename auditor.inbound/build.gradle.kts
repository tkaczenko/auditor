plugins {
    id("auditor.library-conventions")
}

dependencies {
    testImplementation(libs.spring.test)

    api(project(":auditor.core"))

    implementation(libs.spring.boot.starter.web)
}
