import box.pandora.Version

plugins {
    id("org.springframework.boot") version "3.4.3"
    // https://github.com/springdoc/springdoc-openapi-gradle-plugin
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
    id("io.spring.dependency-management") version "1.1.7"
}

configurations.all {
    // In order to replace SLF4J with log4j2:
    // https://logging.apache.org/log4j/2.x/manual/installation.html#impl-core-bridge-jboss-logging
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

ext {
    set("scopeJUnit", "testImplementation")
    set("scopeJUnitPlatformLauncher", "testRuntimeOnly")
}

dependencies {
    val springVersion = "3.4.3"
    implementation("org.springframework.boot:spring-boot-starter:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-log4j2:$springVersion") {
        // https://logging.apache.org/log4j/2.x/manual/installation.html#impl-core-bridge-jboss-logging
        because("replace SLF4J with log4j2")
    }
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-hateoas:$springVersion")
    val springdocVersion = "2.8.5"
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:$springdocVersion") {
        because("https://www.baeldung.com/spring-rest-openapi-documentation#settingupwithspringwebflux")
    }

    runtimeOnly("com.h2database:h2:2.3.232")

    apply(from = rootProject.file("buildSrc/junit.gradle.kts"))
    testImplementation("com.tngtech.archunit:archunit:${Version.ARCH_UNIT}")
}

// https://github.com/springdoc/springdoc-openapi-gradle-plugin?tab=readme-ov-file#customization
openApi {
    outputDir.set(file("${layout.buildDirectory.get().asFile}/docs"))
    outputFileName.set("swagger.json")
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs(
        // https://stackoverflow.com/a/77496400
        "-Xshare:off"
    )
}
