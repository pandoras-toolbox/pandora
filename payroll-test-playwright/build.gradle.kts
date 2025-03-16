ext {
    set("scopeJUnit", "testImplementation")
}

dependencies {
    apply(from = rootProject.file("buildSrc/junit.gradle.kts"))
    testImplementation(testFixtures(project(":functional-test")))
    testImplementation("com.microsoft.playwright:playwright:1.50.0")
}
