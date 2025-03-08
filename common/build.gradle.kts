import box.pandora.Version

plugins {
    `java-library`
    `java-test-fixtures`
}

dependencies {
    testFixturesImplementation("org.apache.logging.log4j:log4j-core:${Version.LOG4J}")
    testFixturesImplementation("org.apache.commons:commons-configuration2:${Version.COMMON_CONFIGURATION_2}")
    testFixturesImplementation("com.fasterxml.jackson.core:jackson-core:${Version.JACKSON}")
    testFixturesImplementation("com.fasterxml.jackson.core:jackson-annotations:${Version.JACKSON}")
    testFixturesImplementation("com.fasterxml.jackson.core:jackson-databind:${Version.JACKSON}")
    testFixturesImplementation("org.apache.commons:commons-jexl3:3.4.0")
    testFixturesImplementation("commons-beanutils:commons-beanutils:1.10.1")
}
