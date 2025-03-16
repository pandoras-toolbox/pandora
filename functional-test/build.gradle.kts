import box.pandora.Version

plugins {
    `java-library`
    `java-test-fixtures`
}

ext {
    set("scopeImmutablesValueImplementation", "testFixturesCompileOnly")
    set("scopeImmutablesValueAnnotationProcessor", "testFixturesAnnotationProcessor")
    set("scopeJUnit", "testFixturesApi")
}

dependencies {
    apply(from = rootProject.file("buildSrc/immutables.gradle.kts"))
    apply(from = rootProject.file("buildSrc/junit.gradle.kts"))

    testFixturesApi(testFixtures(project(":common")))
    testFixturesApi(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    testFixturesApi("com.squareup.okhttp3:okhttp")

    testFixturesImplementation("com.squareup.okhttp3:logging-interceptor")
    testFixturesImplementation("org.apache.commons:commons-lang3:${Version.COMMONS_LANG_3}")
    testFixturesImplementation("com.google.guava:guava:${Version.GUAVA}")
    testFixturesImplementation("org.assertj:assertj-core:${Version.ASSERTJ}")
    testFixturesImplementation("commons-io:commons-io:2.18.0")
    testFixturesImplementation("com.jayway.jsonpath:json-path:2.9.0")
    testFixturesImplementation("net.minidev:json-smart:2.5.2")
    testFixturesImplementation("com.fasterxml.jackson.core:jackson-core:${Version.JACKSON}")
    testFixturesImplementation("com.fasterxml.jackson.core:jackson-databind:${Version.JACKSON}")
    val aspectjVersion = "1.9.23"
    testFixturesImplementation("org.aspectj:aspectjrt:$aspectjVersion")

    testFixturesRuntimeOnly("org.aspectj:aspectjweaver:$aspectjVersion")
}
