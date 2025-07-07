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

    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("javax.validation:validation-api:2.0.1.Final")

    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    implementation("javax.servlet:javax.servlet-api:4.0.1")

    apply(from = rootProject.file("buildSrc/junit.gradle.kts"))
    testImplementation("com.tngtech.archunit:archunit:${Version.ARCH_UNIT}")
}

// https://github.com/springdoc/springdoc-openapi-gradle-plugin?tab=readme-ov-file#customization
openApi {
    // URL to fetch the OpenAPI spec:
    apiDocsUrl.set("http://localhost:8080/v3/api-docs.yaml")
    outputDir.set(file("${layout.buildDirectory.get().asFile}/docs"))
    outputFileName.set("openapi.yaml")
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
    libraryName: String?,
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
        library.set(libraryName)
    }
}

registerOpenApiTask(
    // The generated code is used only as a starting point for implementation.
    taskName = "generateServer",
    generatorName = "spring",
    libraryName = "spring-boot",
    groupName = "openapi",
    descriptionName = "Generates the OpenAPI Java server for this project.",
    outputDirName = "${layout.buildDirectory.get()}/generated/server",
    packageName = "box.pandora.petstore_server"
)

registerOpenApiTask(
    taskName = "generateClient",
    generatorName = "java",
    libraryName = "retrofit2",
    groupName = "openapi",
    descriptionName = "Generates the OpenAPI Java client for this project.",
    outputDirName = "${layout.buildDirectory.get()}/generated/client",
    packageName = "box.pandora.petstore_client"
)

registerOpenApiTask(
    taskName = "generateDocs",
    generatorName = "html2",
    libraryName = null,
    groupName = "openapi",
    descriptionName = "Generates the OpenAPI HTML2 docs for this project.",
    outputDirName = "${layout.buildDirectory.get()}/generated/openapi-docs",
    packageName = "box.pandora.petstore_client"
)

tasks.register("generateAll") {
    group = "openapi"
    description = "Generates the OpenAPI server, client and documentation for this project."
    dependsOn(
        tasks.named("generateServer"),
        tasks.named("generateClient"),
        tasks.named("generateDocs")
    )
}

