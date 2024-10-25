import java.util.*

rootProject.name = "plugins"

// Libs
includeBuild("libs/starter")
includeBuild("libs/security/security")
includeBuild("libs/data-commons")

val properties = Properties()
val inputStream = rootDir.resolve("gradle.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://repo.spring.io/milestone")
    }
}
