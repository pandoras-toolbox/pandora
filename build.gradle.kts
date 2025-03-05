import box.pandora.Version

plugins {
    java
    id("io.qameta.allure") version "2.12.0"
}

// Needed to define a repository for being able to create the Allure report:
repositories {
    mavenCentral()
}

subprojects {
    if (name != "payroll") {
        apply(plugin = "java")
        apply(plugin = "io.qameta.allure")

        java {
            toolchain {
                languageVersion = JavaLanguageVersion.of(21)
            }
        }

        repositories {
            mavenCentral()
        }

        group = "box.pandora"
        version = Version.PANDORA

        dependencies {
            testImplementation("org.apache.logging.log4j:log4j-core:${Version.LOG4J}")
            testImplementation("org.slf4j:slf4j-simple:2.0.17") {
                because("prevent: SLF4J(W): No SLF4J providers were found")
            }
            testImplementation(platform("org.junit:junit-bom:${Version.JUNIT}"))
            testImplementation("org.junit.jupiter:junit-jupiter")
            testImplementation("io.qameta.allure:allure-java-commons:${Version.ALLURE}")
            testImplementation("io.qameta.allure:allure-assertj:${Version.ALLURE}")
            testImplementation("org.assertj:assertj-core:${Version.ASSERTJ}")
            testImplementation("org.immutables:value:${Version.IMMUTABLES}")

            testRuntimeOnly("org.junit.platform:junit-platform-launcher")

            testAnnotationProcessor("org.immutables:value:${Version.IMMUTABLES}")
        }

        tasks.withType<Test> {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }
            // https://junit.org/junit5/docs/current/user-guide/#writing-tests-parallel-execution
            systemProperty("junit.jupiter.execution.parallel.enabled", "true")
            systemProperty("junit.jupiter.execution.parallel.mode.classes.default", "concurrent")
            systemProperty("junit.jupiter.execution.parallel.mode.default", "concurrent")
            systemProperty("junit.jupiter.execution.parallel.config.strategy", "fixed")
            systemProperty("junit.jupiter.execution.parallel.config.fixed.parallelism", "8")
        }

        allure {
            adapter {
                frameworks {
                    junit5 {
                        enabled.set(true)
                    }
                }
            }
        }
    }
}
