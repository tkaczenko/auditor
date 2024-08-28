plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "auditor"

include("auditor.core")
include("auditor.cleanup")
include("auditor.inbound")
include("auditor.outbound")
include("auditor.outbound.feign")
include("auditor.starter")
include("demo")