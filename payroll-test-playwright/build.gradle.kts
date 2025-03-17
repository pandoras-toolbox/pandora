plugins {
    id("io.qameta.allure") version "2.12.0"
}

ext {
    set("scopeJUnit", "testImplementation")
    set("scopeJUnitPlatformLauncher", "testRuntimeOnly")
}

dependencies {
    apply(from = rootProject.file("buildSrc/junit.gradle.kts"))
    apply(from = rootProject.file("buildSrc/allure.gradle.kts"))
    testImplementation(testFixtures(project(":functional-test")))
    testImplementation("com.microsoft.playwright:playwright:1.50.0")
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
