/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 * For more detailed information on multi-project builds, please refer to https://docs.gradle.org/8.12.1/userguide/multi_project_builds.html in the Gradle documentation.
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
    // https://github.com/autonomousapps/dependency-analysis-gradle-plugin
    id("com.autonomousapps.build-health") version "2.12.0"
}

rootProject.name = "pandora"
include("common")
include("functional-test")
//include("payroll-client")
include("payroll-server")
include("payroll-test-gatling")
include("payroll-test-okhttp")
include("payroll-test-playwright")
