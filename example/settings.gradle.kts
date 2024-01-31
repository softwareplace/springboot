rootProject.name = "example"

pluginManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://repo.spring.io/milestone")
    }
}

buildscript {
    dependencies {
        classpath("com.github.softwareplace.springboot:plugins:0.0.017-SNAPSHOT")
    }
}

include(":security")
include(":java-example")
include(":kotlin-example")
include(":webflux-example")

project(":security").projectDir = file("shared-modules/security")


