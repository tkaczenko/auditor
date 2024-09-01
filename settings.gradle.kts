plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "auditor"

include("auditor.core")
include("auditor.inbound")