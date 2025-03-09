import box.pandora.ParallelExecutionConfig
import box.pandora.Version

plugins {
    java
    id("io.qameta.allure") version "2.12.0"
    id("com.github.ben-manes.versions") version "0.52.0"
}

// Needed to define a repository for being able to create the Allure report:
repositories {
    mavenCentral()
}

subprojects {
    if (name != "payroll" && !name.contains("load")) {
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
            testLogging {
                showStandardStreams = true
            }

            useJUnitPlatform {
                val includeTags = project.findProperty("includedTags")?.toString()?.split(",") ?: emptyList()
                val excludeTags = project.findProperty("excludedTags")?.toString()?.split(",") ?: emptyList()
                if (includeTags.isNotEmpty()) {
                    includeTags(*includeTags.toTypedArray())
                    project.findProperty("includedTags")?.let { systemProperty("includedTags", it) }
                }
                if (excludeTags.isNotEmpty()) {
                    excludeTags(*excludeTags.toTypedArray())
                    project.findProperty("excludedTags")?.let { systemProperty("excludedTags", it) }
                }
            }

            testLogging {
                events("passed", "skipped", "failed")
            }


            val cfg = ParallelExecutionConfig.INSTANCE
            // https://junit.org/junit5/docs/current/user-guide/#writing-tests-parallel-execution
            systemProperty("junit.jupiter.execution.parallel.enabled", cfg.enabled())
            systemProperty("junit.jupiter.execution.parallel.mode.classes.default", cfg.modeClassesDefault())
            systemProperty("junit.jupiter.execution.parallel.mode.default", cfg.modeDefault())
            systemProperty("junit.jupiter.execution.parallel.config.strategy", cfg.configStrategy())
            systemProperty("junit.jupiter.execution.parallel.config.fixed.parallelism", cfg.configFixedParallelism())
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
