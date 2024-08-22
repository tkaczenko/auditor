import org.gradle.accessors.dm.LibrariesForLibs

val libs = project.extensions.getByName("libs") as LibrariesForLibs

plugins {
    `java-library`
    id("auditor.java-conventions")
}

dependencies {
    implementation(platform(libs.spring.boot))
    implementation(platform(libs.spring.cloud))
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-implicit:class")
}