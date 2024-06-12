plugins {
    base
    id("test-report-aggregation")
    id("jacoco-report-aggregation")
}

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
        val testAggregateTestReport by creating(AggregateTestReport::class) { // <.>
            testType = TestSuiteType.UNIT_TEST
        }
        val testCodeCoverageReport by creating(JacocoCoverageReport::class) { // <.>
            testType = TestSuiteType.UNIT_TEST
        }
    }
}

tasks.check {
    dependsOn(tasks.named<TestReport>("testAggregateTestReport"))
    dependsOn(tasks.named<JacocoReport>("testCodeCoverageReport"))
}

val exportedProjects = listOf(
    ":core", ":inbound", ":outbound", ":outbound-feign", ":cleanup", ":starter"
)

tasks.register("aggregateJavadoc", Javadoc::class) {
    group = "documentation"
    description = "Generates Javadoc for all exported subprojects"

    val javaSources =
        exportedProjects.flatMap { project(it).sourceSets["main"].allJava.srcDirs }
    val classpathFiles =
        exportedProjects.flatMap { project(it).sourceSets["main"].compileClasspath }

    source = files(javaSources).asFileTree
    classpath = files(classpathFiles)
    setDestinationDir(layout.buildDirectory.dir("/docs/javadoc").get().asFile)
}
