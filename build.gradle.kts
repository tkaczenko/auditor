plugins {
    base
    id("test-report-aggregation")
    id("jacoco-report-aggregation")
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
    ":auditor.core",
    ":auditor.cleanup",
    ":auditor.inbound",
    ":auditor.outbound",
    ":auditor.outbound.feign",
    ":auditor.starter"
)

tasks.register("aggregateJavadoc", Javadoc::class) {
    group = "documentation"
    description = "Generates Javadoc for all exported subprojects"

    val javadocTasks =
        exportedProjects.map { project(it).tasks.withType<Javadoc>().getByName("javadoc") }

    source = files(javadocTasks.map { it.source }).asFileTree
    classpath = files(javadocTasks.map { it.classpath })

    options {
        this as StandardJavadocDocletOptions
        addStringOption("-module-source-path", "./*/src/main/java/")
    }

    setDestinationDir(layout.buildDirectory.dir("docs/javadoc").get().asFile)
}
