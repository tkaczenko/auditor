import org.jreleaser.gradle.plugin.tasks.JReleaserFullReleaseTask
import org.jreleaser.model.Active
import org.jreleaser.model.Stereotype
import org.sonarqube.gradle.SonarTask

plugins {
    base
    signing
    id("test-report-aggregation")
    id("jacoco-report-aggregation")
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.release)
    alias(libs.plugins.jreleaser)
    `maven-publish`
}

description = """
A Spring Boot library providing comprehensive auditing for HTTP requests and responses. 
This library automatically audits inbound and outbound HTTP traffic and persists audit data in an SQL database. 
It facilitates debugging, compliance, and security analysis by offering detailed records of HTTP interactions.
""".trimIndent()

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testReportAggregation(project(":demo"))
    jacocoAggregation(project(":demo"))
}

reporting {
    reports {
        val testAggregateTestReport by creating(AggregateTestReport::class) {
            testType = TestSuiteType.UNIT_TEST
        }
        val testCodeCoverageReport by creating(JacocoCoverageReport::class) {
            testType = TestSuiteType.UNIT_TEST
        }
    }
}

sonar {
    properties {
        property("sonar.projectKey", "tkaczenko_auditor")
        property("sonar.organization", "tkaczenko")
        property("sonar.host.url", "https://sonarcloud.io")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            layout.buildDirectory.file("reports/jacoco/testCodeCoverageReport/testCodeCoverageReport.xml")
        )
    }
}

val exportedProjects = listOf(
    ":auditor.core", ":auditor.inbound", ":auditor.outbound", ":auditor.outbound.feign", ":auditor.cleanup", ":auditor.starter"
)
val aggregatedJavadocDir = layout.buildDirectory.dir("docs/javadoc").get()
val stagingRepository = layout.buildDirectory.dir("staging-deploy").get()

tasks {
    check {
        dependsOn(named<TestReport>("testAggregateTestReport"))
        dependsOn(named<JacocoReport>("testCodeCoverageReport"))
    }

    withType<SonarTask>() {
        dependsOn("testCodeCoverageReport")
    }

    register<Javadoc>("aggregateJavadoc") {
        group = "documentation"
        description = "Generates Javadoc for all exported subprojects"

        val javadocTasks = exportedProjects.map { project(it).tasks.withType<Javadoc>().getByName("javadoc") }
        source = files(javadocTasks.map { it.source }).asFileTree
        classpath = files(javadocTasks.map { it.classpath })

        setDestinationDir(aggregatedJavadocDir.asFile)

        options {
            this as StandardJavadocDocletOptions
            addStringOption("-module-source-path", "./*/src/main/java/")
        }
    }

    register<Copy>("aggregateJars") {
        from(exportedProjects.map { project(it) }.map { it.layout.buildDirectory.dir("staging-deploy").get() })
        into(stagingRepository)
    }

    named<JReleaserFullReleaseTask>("jreleaserFullRelease") {
        dependsOn("aggregateJars")
    }
}

jreleaser {
    project {
        name = rootProject.name
        version = rootProject.version.toString()
        stereotype = Stereotype.WEB
        description = rootProject.description

        license = "MIT"
        inceptionYear = "2024"

        authors = listOf("Andrii Tkachenko")
        tags = listOf("library", "auditing", "spring", "controller", "resttemplate", "feign", "request-response-log", "auditor")

        links {
            homepage = "https://tkaczenko.github.io/auditor"
            documentation = "https://tkaczenko.github.io/auditor/docs"
            license = "https://github.com/tkaczenko/auditor/blob/main/LICENSE.md"
            bugTracker = "https://github.com/tkaczenko/auditor/issues"
        }

        snapshot {
            fullChangelog = true
        }
    }

    distributions {
        exportedProjects.forEach {
            create(it.drop(1)) {
                java {
                    artifacts {
                        artifact {
                            path = file("{{distributionName}}/build/libs/{{distributionName}}-{{projectVersion}}.jar")
                        }
                    }
                }
            }
        }
    }

    signing {
        passphrase = System.getenv("JRELEASER_GPG_PASSPHRASE")
        publicKey = System.getenv("JRELEASER_GPG_PUBLIC_KEY")
        secretKey = System.getenv("JRELEASER_GPG_SECRET_KEY")
        active = Active.ALWAYS
        armored = true
    }

    release {
        github {
            token = System.getenv("TOKEN")

            changelog {
                formatted = Active.ALWAYS
                preset = "conventional-commits"

                hide {
                    contributors = listOf("[bot]", "GitHub")
                }

                contributors {
                    enabled = true
                    format = "- {{contributorName}} ({{contributorUsernameAsLink}})"
                }

                append {
                    enabled = true
                }
            }
        }
    }

    deploy {
        maven {
            github {
                create("GitHubPackages") {
                    active = Active.ALWAYS

                    url = "https://maven.pkg.github.com/tkaczenko/auditor"
                    username = System.getenv("USERNAME")
                    password = System.getenv("TOKEN")
                    snapshotSupported = true

                    stagingRepository(stagingRepository.asFile.path)
                }
            }

            mavenCentral {
                create("MavenCentral") {
                    active = Active.ALWAYS

                    url = "https://central.sonatype.com/api/v1/publisher"
                    username = System.getenv("JRELEASER_MAVENCENTRAL_USERNAME")
                    password = System.getenv("JRELEASER_MAVENCENTRAL_TOKEN")
                    snapshotSupported = true

                    stagingRepository(stagingRepository.asFile.path)
                }
            }
        }
    }
}