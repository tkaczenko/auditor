plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(libs.spotbugs.gradle.plugin)
    // https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
