plugins {
    id("java")
    id("pmd")
    id("jacoco")
    id("checkstyle")
    id("idea")
    id("eclipse")
    id("java-library")
    id("org.springframework.boot") version "3.1.9"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.liquibase.gradle") version "2.0.3"
    id("com.github.spotbugs") version "5.2.1"
}

val springCloudVersion by extra("2022.0.4")
val lombokVersion by extra("1.18.22")
val checkstyleVersion by extra("10.12.5")
val sevntuChecksVersion by extra("1.44.1")

group = "com.github.tkaczenko.auditor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.apache.commons:commons-lang3")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.mockito:mockito-core")

    checkstyle("com.puppycrawl.tools:checkstyle:${checkstyleVersion}")
    checkstyle("com.github.sevntu-checkstyle:sevntu-checks:${sevntuChecksVersion}")
}

tasks.test {
    useJUnitPlatform()
}
