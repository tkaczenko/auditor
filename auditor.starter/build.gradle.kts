plugins {
    id("auditor.library-conventions")
}

dependencies {
    api(project(":auditor.core"))
    api(project(":auditor.inbound"))
    api(project(":auditor.outbound"))
    api(project(":auditor.outbound.feign"))
    api(project(":auditor.cleanup"))

    implementation(libs.spring.boot.starter.web)
}
