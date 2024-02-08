import java.util.*

rootProject.name = "example"

include(":security")
include(":java-example")
include(":kotlin-example")
include(":webflux-example")

project(":security").projectDir = file("shared-modules/security")

//includeBuild("../plugins/versions")
//includeBuild("../plugins/build-configuration")
//includeBuild("../plugins/java")
//includeBuild("../plugins/kotlin")
//
//val properties = Properties()
//val inputStream =
//    rootDir.resolve("../gradle.properties").inputStream()
//properties.load(inputStream)
//
//properties.forEach { (key, value) ->
//    System.setProperty(key.toString(), value.toString())
//}


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
        maven("https://repo.spring.io/milestone")
    }

    dependencies {
        classpath("com.github.softwareplace.springboot:plugins:1.0.0")
    }
}
