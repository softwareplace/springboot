import org.gradle.api.JavaVersion.toVersion

plugins {
    `maven-publish`
    `kotlin-dsl`
    kotlin("jvm") version System.getProperty("kotlinVersion")
}

val sourceGroup = "com.gradle.kts.build.configuration"
group = sourceGroup
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://jitpack.io")
    maven("https://repo.spring.io/milestone")
}

beforeEvaluate {
    java {
        withJavadocJar()
        withSourcesJar()
        sourceCompatibility = toVersion(System.getProperty("jdkVersion"))
        targetCompatibility = toVersion(System.getProperty("jdkVersion"))
    }
}

afterEvaluate {
    allprojects {
        java {
            withJavadocJar()
            withSourcesJar()
            sourceCompatibility = toVersion(System.getProperty("jdkVersion"))
            targetCompatibility = toVersion(System.getProperty("jdkVersion"))
        }
    }
}


gradlePlugin {
    plugins {
        register("build-configuration-plugins") {
            id = "build-configuration-plugin"
            implementationClass = "$sourceGroup.BuildConfigurationPlugin"
        }
    }
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${System.getProperty("kotlinVersion")}")
}


