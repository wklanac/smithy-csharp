plugins {
    `java-library`
}

description = "Common code generation components for languages designed to satisfy the .NET Common Langauage Specification (CLS)"

val smithyVersion: String by project

dependencies {
    api("software.amazon.smithy:smithy-codegen-core:$smithyVersion")
    implementation("com.google.guava:guava:32.1.2-jre")
}