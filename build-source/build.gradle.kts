//import com.gradle.kts.build.configuration.kotlinDeps
import com.gradle.kts.build.configuration.Dependencies
import com.gradle.kts.build.configuration.addSpringframeworkBoot
import com.gradle.kts.build.configuration.implementation
import com.gradle.kts.build.configuration.kotlinDeps

plugins {
    `maven-publish`
    `kotlin-dsl`
    kotlin("jvm") version "1.7.22"
    id("build-configuration-plugin")
    id("org.jetbrains.kotlin.plugin.spring") version "1.7.22"
    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
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

val sourceGroup = "com.gradle.kts.build.source"
group = sourceGroup

version = "1.0.0"

gradlePlugin {
    plugins {
        register("build-source-plugin") {
            id = "build-source-plugin"
            implementationClass = "$sourceGroup.BuildSourcePlugin"
        }
        register("build-source-application-plugin") {
            id = "build-source-application-plugin"
            implementationClass = "$sourceGroup.ApplicationPlugin"
        }
        register("build-source-project-plugin") {
            id = "build-source-project-plugin"
            implementationClass = "$sourceGroup.ProjectPlugin"
        }
    }
}

dependencies {
    kotlinDeps()
    addSpringframeworkBoot("spring-boot-gradle-plugin")
    implementation(
        Dependencies.buildDependency(
            Dependencies.LibDomain.comGradleKtsConfiguration,
            Dependencies.TargetLib.buildConfiguration,
            Dependencies.Version.comGradleKtsConfiguration,
        )
    )
}

