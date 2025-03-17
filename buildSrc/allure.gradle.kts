import box.pandora.Version

dependencies {
    add("testRuntimeOnly", "io.qameta.allure:allure-assertj:${Version.ALLURE}")
    add("testRuntimeOnly", "io.qameta.allure:allure-java-commons:${Version.ALLURE}")
    add("testRuntimeOnly", "org.aspectj:aspectjrt:${Version.ASPECTJ}")
    add("testRuntimeOnly", "org.aspectj:aspectjweaver:${Version.ASPECTJ}")
    add("testRuntimeOnly", "commons-beanutils:commons-beanutils:1.10.1")
}
