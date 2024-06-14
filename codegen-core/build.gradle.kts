plugins {
    `java-library`
}

description = "Community smithy code generation for C#"

val smithyVersion: String by project

dependencies {
    api("software.amazon.smithy:smithy-codegen-core:$smithyVersion")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("com.google.guava:guava:32.1.2-jre")
}