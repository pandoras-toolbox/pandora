import box.pandora.Version

plugins {
    `java-library`
    `java-test-fixtures`
}

extra["scopeImmutablesValueImplementation"] = "testFixturesImplementation"
extra["scopeImmutablesValueAnnotationProcessor"] = "testFixturesAnnotationProcessor"
dependencies {
    apply(from = rootProject.file("buildSrc/immutables.gradle.kts"))

    testFixturesApi(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    testFixturesApi("com.squareup.okhttp3:okhttp")
    testFixturesApi("com.squareup.okhttp3:logging-interceptor")
    testFixturesApi(testFixtures(project(":common")))

    testFixturesImplementation("io.qameta.allure:allure-java-commons:${Version.ALLURE}")
    testFixturesImplementation("io.qameta.allure:allure-assertj:${Version.ALLURE}")
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
    testFixturesImplementation("org.aspectj:aspectjweaver:$aspectjVersion")
}
