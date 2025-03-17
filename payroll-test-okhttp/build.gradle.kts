plugins {
    id("io.qameta.allure") version "2.12.0"
}

ext {
    set("scopeJUnit", "testImplementation")
}

repositories {
    mavenCentral()
}

dependencies {
    apply(from = rootProject.file("buildSrc/junit.gradle.kts"))
    apply(from = rootProject.file("buildSrc/allure.gradle.kts"))
    apply(plugin = "io.qameta.allure")
    testImplementation(testFixtures(project(":functional-test")))
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
