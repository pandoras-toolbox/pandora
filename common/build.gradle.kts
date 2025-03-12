import box.pandora.Version

plugins {
    `java-library`
    `java-test-fixtures`
}

extra["scopeLog4jCore"] = "testFixturesApi"
extra["scopeLog4jBridge"] = "testFixturesRuntimeOnly"
extra["scopeJUnit"] = "testFixturesApi"
dependencies {
    apply(from = rootProject.file("buildSrc/log4j.gradle.kts"))
    apply(from = rootProject.file("buildSrc/junit.gradle.kts"))

    testFixturesImplementation("com.tngtech.archunit:archunit:${Version.ARCH_UNIT}")
    testFixturesImplementation("org.apache.commons:commons-configuration2:${Version.COMMON_CONFIGURATION_2}")
    testFixturesImplementation("com.fasterxml.jackson.core:jackson-core:${Version.JACKSON}")
    testFixturesImplementation("com.fasterxml.jackson.core:jackson-annotations:${Version.JACKSON}")
    testFixturesImplementation("com.fasterxml.jackson.core:jackson-databind:${Version.JACKSON}")
    testFixturesImplementation("org.apache.commons:commons-jexl3:3.4.0")
    testFixturesImplementation("commons-beanutils:commons-beanutils:1.10.1")
}
