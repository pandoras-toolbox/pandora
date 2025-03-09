import box.pandora.Version

plugins {
    id("java")
    id("io.gatling.gradle") version "3.13.4.1"
}

group = "box.pandora"
version = Version.PANDORA

repositories {
    mavenCentral()
}

tasks.withType<JavaExec> {
    //  Otherwise this error would occur:
    //  java.lang.IllegalAccessException: module java.base does not open java.lang to unnamed module @4d3167f4
    jvmArgs = listOf("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}
