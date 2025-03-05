import box.pandora.Version

plugins {
    `java-library`
    `java-test-fixtures`
}

dependencies {
    testFixturesApi(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    testFixturesApi("com.squareup.okhttp3:okhttp")
    testFixturesApi("com.squareup.okhttp3:logging-interceptor")

    testFixturesImplementation("org.apache.logging.log4j:log4j-core:${Version.LOG4J}")
    testFixturesImplementation(platform("org.junit:junit-bom:${Version.JUNIT}"))
    testFixturesImplementation("org.junit.jupiter:junit-jupiter")
    testFixturesImplementation("io.qameta.allure:allure-java-commons:${Version.ALLURE}")
    testFixturesImplementation("io.qameta.allure:allure-assertj:${Version.ALLURE}")
    testFixturesImplementation("org.apache.commons:commons-lang3:${Version.COMMONS_LANG_3}")
    testFixturesImplementation("com.google.guava:guava:33.4.0-jre")
    testFixturesImplementation("org.immutables:value:${Version.IMMUTABLES}")
    testFixturesImplementation("org.immutables:builder:${Version.IMMUTABLES}")
    testFixturesImplementation("org.assertj:assertj-core:${Version.ASSERTJ}")
    testFixturesImplementation("commons-io:commons-io:2.18.0")
    val aspectjVersion = "1.9.22.1"
    testFixturesImplementation("org.aspectj:aspectjrt:$aspectjVersion")
    testFixturesImplementation("org.aspectj:aspectjweaver:$aspectjVersion")

    testFixturesAnnotationProcessor("org.immutables:value:${Version.IMMUTABLES}")
}
