import com.gradle.kts.build.configuration.Dependencies
import com.gradle.kts.build.configuration.kotlinDeps


plugins {
    `maven-publish`
    `kotlin-dsl`
    kotlin("jvm") version System.getProperty("kotlinVersion")
    id("source-plugin")
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

val sourceGroup = "com.gradle.kts.kotlin.openapi"
group = sourceGroup

version = "1.0.0"

gradlePlugin {
    plugins {
        register("openapi-plugin") {
            id = "openapi-plugin"
            implementationClass = "$sourceGroup.OpenApiPlugin"
        }
        register("submodule-java-openapi-plugin") {
            id = "submodule-java-openapi-plugin"
            implementationClass = "$sourceGroup.SubmoduleOpenApiPlugin"
        }
    }
}

dependencies {
    kotlinDeps()
//    jakarta()
    implementation("com.gradle.kts.build.configuration:build-configuration:1.0.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openapiTools}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


