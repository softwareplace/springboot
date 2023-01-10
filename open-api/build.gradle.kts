plugins {
    `maven-publish`
    `kotlin-dsl`
    kotlin("jvm") version "1.7.22"
    id("build-configuration-plugin")
    id("org.openapi.generator") version "5.3.0"
    id("info.solidsoft.pitest") version "1.7.0"
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
}

java {
    withJavadocJar()
    withSourcesJar()
}

val sourceGroup = "com.gradle.kts.build.openapi"
group = sourceGroup

version = "1.0.0"

gradlePlugin {
    plugins {
        register("build-open-api-plugin") {
            id = "build-open-api-plugin"
            implementationClass = "$sourceGroup.OpenApiPlugin"
        }
    }
}

dependencies {

}

