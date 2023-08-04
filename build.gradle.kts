plugins {
    `maven-publish`
    kotlin("jvm") version "1.9.0"
}

group = "me.omega"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io/")
}

dependencies {
    compileOnly("dev.hollowcube:minestom-ce:dev")
    compileOnly("net.kyori:adventure-text-minimessage:4.12.0")

    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    compileOnly("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")
    compileOnly("org.mongodb:bson-kotlinx:4.10.1")

    // Logging
    compileOnly("org.slf4j:slf4j-api:2.0.7")
    compileOnly("org.slf4j:slf4j-jdk14:2.0.7")

    // Serialization
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1")

    // Reflection
    compileOnly("org.reflections:reflections:0.10.2")
    compileOnly(kotlin("reflect"))
}

kotlin {
    jvmToolchain(17)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {

    build {
        dependsOn(publishToMavenLocal)
    }

}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}