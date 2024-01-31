rootProject.name = "example"

include(":security")
include(":java-example")
include(":kotlin-example")
include(":webflux-example")

project(":security").projectDir = file("shared-modules/security")

pluginManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://repo.spring.io/milestone")
    }
}

//buildscript {
//    dependencies {
//        classpath("com.github.softwareplace.springboot:plugins:1.0.0")
//    }
//}
