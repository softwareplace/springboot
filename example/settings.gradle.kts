import java.util.*

rootProject.name = "example"

include(":security")
include(":java-example")
include(":kotlin-example")
include(":webflux-example")

project(":security").projectDir = file("shared-modules/security")


includeBuild("../plugins/build-configuration")
includeBuild("../plugins/java")
includeBuild("../plugins/kotlin")

val properties = Properties()
val inputStream = rootDir.resolve("../plugins/build-configuration/src/main/resources/gradle.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
//        maven(url = "https://jitpack.io")  {
//            content {
//                includeModule("com.github.softwareplace.springboot", "java")
//                includeModule("com.github.softwareplace.springboot", "kotlin")
//                includeModule("com.github.softwareplace.springboot", "java-submodule")
//                includeModule("com.github.softwareplace.springboot", "kotlin-submodule")
//                includeModule("com.github.softwareplace.springboot", "build-configuration")
//            }
//        }
    }
}


