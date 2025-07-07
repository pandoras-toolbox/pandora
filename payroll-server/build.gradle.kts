import box.pandora.Version

repositories {
    mavenCentral()
}

plugins {
    id("org.springframework.boot") version box.pandora.Version.SPRING
    // https://github.com/springdoc/springdoc-openapi-gradle-plugin
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.14.0"
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
    implementation("org.springframework.boot:spring-boot-starter:${Version.SPRING}")
    implementation("org.springframework.boot:spring-boot-starter-log4j2:${Version.SPRING}") {
        // https://logging.apache.org/log4j/2.x/manual/installation.html#impl-core-bridge-jboss-logging
        because("replace SLF4J with log4j2")
    }
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${Version.SPRING}")
    implementation("org.springframework.boot:spring-boot-starter-validation:${Version.SPRING}")
    implementation("org.springframework.boot:spring-boot-starter-hateoas:${Version.SPRING}")
    val springdocVersion = "2.8.9"
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
    // URL to fetch the OpenAPI spec:
    apiDocsUrl.set("http://localhost:8080/v3/api-docs.yaml")
    outputDir.set(file("${layout.buildDirectory.get().asFile}/docs"))
    outputFileName.set("openapi.yaml")
    java {
        toolchain {
            // Target Java 17 to prevent a compile error for some reason:
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs(
        // https://stackoverflow.com/a/77496400
        "-Xshare:off"
    )
}

fun registerOpenApiTask(
    taskName: String,
    generatorName: String,
    groupName: String,
    descriptionName: String,
    outputDirName: String,
    packageName: String,
) {
    tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>(taskName) {
        group = groupName
        description = descriptionName
        this.generatorName.set(generatorName)
        inputSpec.set("$projectDir/src/main/resources/spec/api-docs.yaml")
        outputDir.set(outputDirName)
        apiPackage.set("${packageName}.api")
        invokerPackage.set("${packageName}.invoker")
        modelPackage.set("${packageName}.model")
        configOptions.set(
            mapOf(
                "library" to "retrofit2"
            )
        )
    }
}

registerOpenApiTask(
    taskName = "generateClient",
    generatorName = "java",
    groupName = "openapi",
    descriptionName = "Generates the OpenAPI Java client for this project.",
    outputDirName = "$rootDir/payroll-client",
    packageName = "box.pandora.payroll_client"
)

registerOpenApiTask(
    taskName = "generateDocs",
    generatorName = "html2",
    groupName = "openapi",
    descriptionName = "Generates the OpenAPI HTML2 docs for this project.",
    outputDirName = "$rootDir/payroll-server/openapi-docs",
    packageName = "box.pandora.payroll_client"
)

tasks.register("generateAll") {
    group = "openapi"
    description = "Generates the OpenAPI client and documentation for this project."
    dependsOn(
        tasks.named("generateClient"),
        tasks.named("generateDocs")
    )
}
