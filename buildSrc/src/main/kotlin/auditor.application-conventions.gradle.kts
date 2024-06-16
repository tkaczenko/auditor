import gradle.kotlin.dsl.accessors._b57f3335cbc6d1ccbac183e6469782cf.implementation
import org.gradle.accessors.dm.LibrariesForLibs

val libs = project.extensions.getByName("libs") as LibrariesForLibs

plugins {
    application
    id("auditor.java-conventions")
}

dependencies {
    implementation(project(":auditor.starter"))

    implementation(platform(libs.spring.boot))
    implementation(platform(libs.spring.cloud))

    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.cloud.starter.openfeign)
    implementation(libs.micrometer.brave)

    implementation(libs.h2)
}
