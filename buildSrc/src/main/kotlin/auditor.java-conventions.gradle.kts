import com.github.spotbugs.snom.SpotBugsTask
import org.gradle.accessors.dm.LibrariesForLibs

val libs = project.extensions.getByName("libs") as LibrariesForLibs

plugins {
    java
    id("pmd")
    id("jacoco")
    id("checkstyle")
    id("com.github.spotbugs")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.junit.core)
    testImplementation(libs.mockito.junit.jupiter)
    testRuntimeOnly(libs.junit.launcher)

    implementation(libs.spotbugs.annotations)
    implementation(libs.commons.lang3)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

checkstyle {
    configProperties = mapOf(
        "org.checkstyle.google.suppressionfilter.config" to "${rootDir}/config/checkstyle/suppressions.xml"
    )
    val archive = configurations.checkstyle.get().resolve().filter {
        it.name.startsWith("checkstyle")
    }
    config = resources.text.fromArchiveEntry(archive, "google_checks.xml")

    toolVersion = "10.12.5"
    maxWarnings = 0
}

pmd {
    ruleSets = listOf("rulesets/java/quickstart.xml")
    toolVersion = "7.2.0"
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        html.required = true
    }
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        html.required = true
    }
}

tasks.withType<SpotBugsTask>().configureEach {
    reports {
        create("html") {
            enabled = true
        }
    }
}


