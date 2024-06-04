plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "auditor"
include("core")
include("inbound")
include("outbound")
include("outbound-feign")
