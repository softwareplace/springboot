rootProject.name = "springboot-security-example"

include(":app")
include(":security")

//project(":security").projectDir = file("../security")

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
    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.github.softwareplace.springboot:plugins:1.0.0")
    }
}
