import box.pandora.Version

val scopeCore: String = project.extra["scopeLog4jCore"] as String
val scopeLog4jBridge: String = project.extra["scopeLog4jBridge"] as String

dependencies {
    add(scopeCore, "org.apache.logging.log4j:log4j-core:${Version.LOG4J}")
    // Redirect other loggers through log4j2:
    // https://logging.apache.org/log4j/2.x/manual/installation.html
    add(scopeLog4jBridge, "org.apache.logging.log4j:log4j-slf4j2-impl:${Version.LOG4J}")
    add(scopeLog4jBridge, "org.apache.logging.log4j:log4j-jcl:${Version.LOG4J}")
    add(scopeLog4jBridge, "org.apache.logging.log4j:log4j-jul:${Version.LOG4J}")
    add(scopeLog4jBridge, "org.apache.logging.log4j:log4j-jpl:${Version.LOG4J}")
}
