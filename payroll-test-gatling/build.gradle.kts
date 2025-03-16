import box.pandora.Version

plugins {
    id("java")
    id("io.gatling.gradle") version "3.13.5"
}

group = "box.pandora"
version = Version.PANDORA

repositories {
    mavenCentral()
}

configurations.all {
    exclude(group = "ch.qos.logback", module = "logback-classic")
}

ext {
    set("scopeJUnit", "gatlingImplementation")
}

dependencies {
    apply(from = rootProject.file("buildSrc/junit.gradle.kts"))

    gatlingImplementation("com.tngtech.archunit:archunit:${Version.ARCH_UNIT}")

    gatlingRuntimeOnly("org.apache.logging.log4j:log4j-slf4j2-impl:${Version.LOG4J}")
}

gatling {
    jvmArgs =
        listOf(
            "--add-opens", "java.base/java.lang=ALL-UNNAMED",
            "-Dlog4j.configurationFile=src/gatling/resources/log4j2-test.xml"
        )
}

tasks.withType<JavaExec> {
    //  Otherwise this error would occur when starting Gatling from a Java class:
    //  java.lang.IllegalAccessException: module java.base does not open java.lang to unnamed module @4d3167f4
    jvmArgs = listOf("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}
