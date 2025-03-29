import box.pandora.Version

plugins {
    `java-library`
    `java-test-fixtures`
}

ext {
    set("scopeImmutablesValueImplementation", "testFixturesCompileOnly")
    set("scopeImmutablesValueAnnotationProcessor", "testFixturesAnnotationProcessor")
    set("scopeJUnit", "testFixturesApi")
    set("scopeJUnitPlatformLauncher", "testFixturesRuntimeOnly")
}

dependencies {
    apply(from = rootProject.file("buildSrc/immutables.gradle.kts"))
    apply(from = rootProject.file("buildSrc/junit.gradle.kts"))

    testFixturesApi(testFixtures(project(":common")))
    testFixturesApi("org.assertj:assertj-core:3.27.3")
    testFixturesApi(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    testFixturesApi("com.squareup.okhttp3:okhttp")
    testFixturesApi("com.google.guava:guava:33.4.6-jre")

    testFixturesImplementation("io.qameta.allure:allure-assertj:${Version.ALLURE}")
    testFixturesImplementation("org.aspectj:aspectjrt:${Version.ASPECTJ}")
    testFixturesImplementation("com.squareup.okhttp3:logging-interceptor")
    testFixturesImplementation("org.apache.commons:commons-lang3:3.17.0")
    testFixturesImplementation("commons-io:commons-io:2.18.0")
    testFixturesImplementation("com.jayway.jsonpath:json-path:2.9.0")
    testFixturesImplementation("net.minidev:json-smart:2.5.2")
    testFixturesImplementation("com.fasterxml.jackson.core:jackson-core:${Version.JACKSON}")
    testFixturesImplementation("com.fasterxml.jackson.core:jackson-databind:${Version.JACKSON}")
}
