plugins {
    java
}

group = "box.pandora"
version = "0.0.1-SNAPSHOT"
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.12.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    testImplementation("com.squareup.okhttp3:okhttp")
    testImplementation("org.apache.commons:commons-lang3:3.17.0")
    testImplementation("com.google.guava:guava:33.4.0-jre")
    val immutablesVersion = "2.10.1"
    testImplementation("org.immutables:value:$immutablesVersion")
    testImplementation("org.immutables:builder:$immutablesVersion")
    testImplementation("org.assertj:assertj-core:3.27.3")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testAnnotationProcessor("org.immutables:value:$immutablesVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
