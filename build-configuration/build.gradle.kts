plugins {
    `maven-publish`
    `kotlin-dsl`
    kotlin("jvm") version "1.7.22"
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://jitpack.io")
    maven("https://repo.spring.io/milestone")
}

java {
    withJavadocJar()
    withSourcesJar()
}

val sourceGroup = "com.gradle.kts.build.configuration"
group = sourceGroup
version = "1.0.0"


gradlePlugin {
    plugins {
        register("build-configuration-plugins") {
            id = "build-configuration-plugin"
            implementationClass = "$sourceGroup.BuildConfigurationPlugin"
        }
    }
}


