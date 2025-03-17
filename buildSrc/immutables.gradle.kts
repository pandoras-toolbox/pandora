val scopeImmutablesValueImplementation: String = project.extra["scopeImmutablesValueImplementation"] as String
val scopeImmutablesValueAnnotationProcessor: String = project.extra["scopeImmutablesValueAnnotationProcessor"] as String

dependencies {
    val immutablesVersion = "2.10.1"
    add(scopeImmutablesValueImplementation, "org.immutables:value:$immutablesVersion")
    add(scopeImmutablesValueImplementation, "org.immutables:builder:$immutablesVersion")
    add(scopeImmutablesValueAnnotationProcessor, "org.immutables:value:$immutablesVersion")
}
