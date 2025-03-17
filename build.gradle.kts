plugins {
    java
    id("com.github.ben-manes.versions") version "0.52.0"
}

tasks.wrapper {
    gradleVersion = "8.13"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "project-report")
    apply(plugin = "com.github.ben-manes.versions")

    group = "box.pandora"
    version = "0.0.1-SNAPSHOT"

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    repositories {
        mavenCentral()
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}
