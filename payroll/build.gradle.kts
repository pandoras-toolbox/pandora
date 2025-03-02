plugins {
    java
    id("org.springframework.boot") version libs.versions.spring.get()
    // https://github.com/springdoc/springdoc-openapi-gradle-plugin
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
    id("io.spring.dependency-management") version "1.1.7"
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

// https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#0.3
val mockitoAgent = configurations.create("mockitoAgent")
val springVersion = libs.versions.spring.get()
val springdocVersion = libs.versions.springdoc.get();
dependencies {
    testImplementation(libs.mockito)
    mockitoAgent(libs.mockito) {
        isTransitive = false
    }
    implementation("org.springframework.boot:spring-boot-starter:$springVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-hateoas:$springVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:$springdocVersion") {
        because("https://www.baeldung.com/spring-rest-openapi-documentation#settingupwithspringwebflux")
    }
    runtimeOnly("com.h2database:h2:2.3.232")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.12.0")
}

// https://github.com/springdoc/springdoc-openapi-gradle-plugin?tab=readme-ov-file#customization
openApi {
    outputDir.set(file("${layout.buildDirectory.get().asFile}/docs"))
    outputFileName.set("swagger.json")
 }

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-javaagent:${mockitoAgent.asPath}",
        // https://stackoverflow.com/a/77496400
        "-Xshare:off")
}
