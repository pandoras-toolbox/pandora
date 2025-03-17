import box.pandora.Version

plugins {
    `java-library`
    `java-test-fixtures`
}

ext {
    set("scopeLog4jCore", "testFixturesApi")
    set("scopeLog4jBridge", "testFixturesRuntimeOnly")
    set("scopeJUnit", "testImplementation")
}

dependencies {
    apply(from = rootProject.file("buildSrc/log4j.gradle.kts"))
    apply(from = rootProject.file("buildSrc/junit.gradle.kts"))

    testFixturesImplementation("com.tngtech.archunit:archunit:${Version.ARCH_UNIT}")
    testFixturesImplementation("org.apache.commons:commons-configuration2:2.11.0")
    testFixturesImplementation("com.fasterxml.jackson.core:jackson-databind:${Version.JACKSON}")
    testFixturesImplementation("org.apache.commons:commons-jexl3:3.4.0")
}

tasks.withType<JavaExec> {
    // https://logging.apache.org/log4j/2.x/manual/installation.html#impl-core-bridge-jul
    systemProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager")
}
