plugins {
    `java-library`
}

allprojects {
    group = "software.smithycommunity.csharp"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }
}

// No JAR for root project, just subprojects
tasks["jar"].enabled = false

