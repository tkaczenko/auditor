import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
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
    mavenCentral()
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    implementation(libs.slf4j.api)

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

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
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
    threads = 4
    ruleSets = listOf("rulesets/java/quickstart.xml")
    toolVersion = "7.2.0"
}

jacoco {
    toolVersion = "0.8.12"
}

tasks {
    withType<JavaCompile>().configureEach {
        options.isFork = true
    }

    test {
        useJUnitPlatform()

        finalizedBy(jacocoTestReport)

        systemProperties(
            "junit.jupiter.execution.parallel.enabled" to "true",
            "junit.jupiter.execution.parallel.mode.default" to "same_thread",
            "junit.jupiter.execution.parallel.mode.classes.default" to "concurrent",
        )
    }

    jacocoTestReport {
        dependsOn(test)
        reports {
            xml.required = true
            html.required = true
        }
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = "0.80".toBigDecimal()
                }
            }
        }
    }

    withType<Checkstyle>().configureEach {
        reports {
            html.required = true
        }
    }

    withType<SpotBugsTask>().configureEach {
        effort = Effort.MAX
        reportLevel = Confidence.HIGH

        reports {
            create("html") {
                enabled = true
            }
        }
    }
}