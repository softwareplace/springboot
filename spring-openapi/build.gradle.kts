import com.gradle.kts.build.configuration.Dependencies
import com.gradle.kts.build.configuration.kotlinDeps
import com.gradle.kts.build.source.jakarta

plugins {
    `maven-publish`
    `kotlin-dsl`
    kotlin("jvm") version System.getProperty("kotlinVersion")
    id("build-source-plugin")
    id("org.openapi.generator") version System.getProperty("openapitoolsVersion")
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://jitpack.io")
}

java {
    withJavadocJar()
    withSourcesJar()
}

val sourceGroup = "com.gradle.kts.build.spring.openapi"
group = sourceGroup

version = "1.0.0"

gradlePlugin {
    plugins {
        // Java
        register("build-spring-openapi-plugin") {
            id = "build-spring-openapi-plugin"
            implementationClass = "$sourceGroup.JavaOpenApiPlugin"
        }
        register("build-submodule-spring-openapi-plugin") {
            id = "build-submodule-spring-openapi-plugin"
            implementationClass = "$sourceGroup.JavaSubmoduleOpenApiPlugin"
        }

        // Kotlin
        register("build-kotlin-spring-openapi-plugin") {
            id = "build-kotlin-spring-openapi-plugin"
            implementationClass = "$sourceGroup.KotlinOpenApiPlugin"
        }
        register("build-kotlin-submodule-spring-openapi-plugin") {
            id = "build-kotlin-submodule-spring-openapi-plugin"
            implementationClass = "$sourceGroup.KotlinSubmoduleOpenApiPlugin"
        }
    }
}

dependencies {
    kotlinDeps()
    jakarta()
    implementation("com.gradle.kts.build.configuration:build-configuration:1.0.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openapiTools}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


