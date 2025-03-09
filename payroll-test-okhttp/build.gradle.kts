import box.pandora.Version

dependencies {
    testImplementation(testFixtures(project(":functional-test")))
    testImplementation("com.google.guava:guava:${Version.GUAVA}")
    testImplementation("org.apache.commons:commons-lang3:${Version.COMMONS_LANG_3}")
}
