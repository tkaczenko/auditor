plugins {
    id("auditor.library-conventions")
}

dependencies {
    api(project(":auditor.core"))
    api(project(":auditor.inbound"))
    api(project(":auditor.outbound"))

    implementation(libs.spring.boot.starter.web)
}
