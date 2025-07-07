import box.pandora.ParallelExecutionConfig

val scopeJUnit: String = project.extra["scopeJUnit"] as String
val scopeJUnitPlatformLauncher: String = project.extra["scopeJUnitPlatformLauncher"] as String

dependencies {
    add(scopeJUnit, platform("org.junit:junit-bom:5.13.3"))
    add(scopeJUnit, "org.junit.jupiter:junit-jupiter")
    add(scopeJUnitPlatformLauncher, "org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    testLogging {
        showStandardStreams = true
    }

    useJUnitPlatform {
        val includeTags = project.findProperty("includedTags")?.toString()?.split(",") ?: emptyList()
        val excludeTags = project.findProperty("excludedTags")?.toString()?.split(",") ?: emptyList()
        if (includeTags.isNotEmpty()) {
            includeTags(*includeTags.toTypedArray())
            project.findProperty("includedTags")?.let { systemProperty("includedTags", it) }
        }
        if (excludeTags.isNotEmpty()) {
            excludeTags(*excludeTags.toTypedArray())
            project.findProperty("excludedTags")?.let { systemProperty("excludedTags", it) }
        }
    }

    testLogging {
        events("passed", "skipped", "failed")
    }

    val cfg = ParallelExecutionConfig.INSTANCE
    // https://junit.org/junit5/docs/current/user-guide/#writing-tests-parallel-execution
    systemProperty("junit.jupiter.execution.parallel.enabled", cfg.enabled())
    systemProperty("junit.jupiter.execution.parallel.mode.classes.default", cfg.modeClassesDefault())
    systemProperty("junit.jupiter.execution.parallel.mode.default", cfg.modeDefault())
    systemProperty("junit.jupiter.execution.parallel.config.strategy", cfg.configStrategy())
    systemProperty("junit.jupiter.execution.parallel.config.fixed.parallelism", cfg.configFixedParallelism())

    // https://logging.apache.org/log4j/2.x/manual/installation.html#impl-core-bridge-jul
    systemProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager")
}
