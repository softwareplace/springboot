import java.util.*

rootProject.name = "example"

include(":security")
include(":java-example")
include(":kotlin-example")
include(":webflux-example")

project(":security").projectDir = file("shared-modules/security")

//val properties = Properties()
//val inputStream = rootDir.resolve("../plugins/build-configuration/src/main/resources/gradle.properties").inputStream()
//properties.load(inputStream)
//
//properties.forEach { (key, value) ->
//    System.setProperty(key.toString(), value.toString())
//}

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
//        maven(url = "https://jitpack.io")  {
//            content {
//                includeModule("com.github.softwareplace.springboot", "java")
//                includeModule("com.github.softwareplace.springboot", "build-configuration")
////                includeModuleByRegex("com.github.softwareplace.springboot", "\\.*")
//            }
//        }
    }
}


