import org.gradle.accessors.dm.LibrariesForLibs

val libs = project.extensions.getByName("libs") as LibrariesForLibs

plugins {
    `java-library`
    `maven-publish`
    id("auditor.java-conventions")
}

dependencies {
    implementation(platform(libs.spring.boot))
    implementation(platform(libs.spring.cloud))
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    afterEvaluate {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])

                pom {
                    packaging = "jar"
                    name = project.name
                    description = project.description
                    url = "https://tkaczenko.github.io/auditor"

                    licenses {
                        license {
                            name = "MIT"
                            url = "https://github.com/tkaczenko/auditor/blob/main/LICENSE.md"
                            distribution = "repo"
                        }
                    }

                    developers {
                        developer {
                            id = "tkaczenko"
                            name = "Andrii Tkachenko"
                            email = "andrii.tkaczenko@gmail.com"
                        }
                    }

                    scm {
                        url = "https://github.com/tkaczenko/auditor"
                        connection = "scm:git:https://github.com/tkaczenko/auditor.git"
                        developerConnection = "scm:git:https://github.com/tkaczenko/auditor.git"
                    }

                    issueManagement {
                        system = "GitHub"
                        url = "https://github.com/tkaczenko/auditor/issues"
                    }

                    ciManagement {
                        system = "GitHub Actions"
                        url = "https://github.com/tkaczenko/auditor/actions"
                    }

                    distributionManagement {
                        downloadUrl = "https://github.com/tkaczenko/auditor/releases"
                    }
                }
            }
        }
    }

    repositories {
        mavenLocal {
            url = uri(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        options.compilerArgs.add("-implicit:class")
    }
}