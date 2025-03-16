import box.pandora.Version

plugins {
    java
    id("io.qameta.allure") version "2.12.0"
    id("com.github.ben-manes.versions") version "0.52.0"
}

tasks.wrapper {
    gradleVersion = "8.13"
}

// Needed to define a repository for being able to create the Allure report:
repositories {
    mavenCentral()
}

allprojects {
    apply(plugin = "project-report")
    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}

subprojects {
    if (name != "payroll-backend" && name != "payroll-test-gatling") {
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

        extra["scopeLog4jCore"] = "testImplementation"
        extra["scopeLog4jBridge"] = "runtimeOnly"
        extra["scopeImmutablesValueImplementation"] = "testImplementation"
        extra["scopeImmutablesValueAnnotationProcessor"] = "testAnnotationProcessor"
        dependencies {
            apply(from = rootProject.file("buildSrc/log4j.gradle.kts"))
            apply(from = rootProject.file("buildSrc/immutables.gradle.kts"))

            testImplementation("io.qameta.allure:allure-java-commons:${Version.ALLURE}")
            testImplementation("io.qameta.allure:allure-assertj:${Version.ALLURE}")
            testImplementation("org.assertj:assertj-core:${Version.ASSERTJ}")
        }

        tasks.withType<JavaExec> {
            // https://logging.apache.org/log4j/2.x/manual/installation.html#impl-core-bridge-jul
            systemProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager")
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
