import box.pandora.Version

extra["scopeJUnit"] = "testImplementation"
dependencies {
    apply(from = rootProject.file("buildSrc/junit.gradle.kts"))
    testImplementation(testFixtures(project(":functional-test")))
    testImplementation("com.google.guava:guava:${Version.GUAVA}")
    testImplementation("org.apache.commons:commons-lang3:${Version.COMMONS_LANG_3}")
}
