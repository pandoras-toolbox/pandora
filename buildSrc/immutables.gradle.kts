import box.pandora.Version

val scopeImmutablesValueImplementation: String = project.extra["scopeImmutablesValueImplementation"] as String
val scopeImmutablesValueAnnotationProcessor: String = project.extra["scopeImmutablesValueAnnotationProcessor"] as String

dependencies {
    add(scopeImmutablesValueImplementation, "org.immutables:value:${Version.IMMUTABLES}")
    add(scopeImmutablesValueImplementation, "org.immutables:builder:${Version.IMMUTABLES}")
    add(scopeImmutablesValueAnnotationProcessor, "org.immutables:value:${Version.IMMUTABLES}")
}
