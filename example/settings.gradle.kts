import java.util.*

rootProject.name = "example"
include(":security")
include(":java-example")
include(":kotlin-example")
include(":webflux-example")

project(":security").projectDir = file("shared-modules/security")

includeBuild("../spring-boot-build-configuration")
includeBuild("../source/spring-boot-source-java")
includeBuild("../source/spring-boot-source-kotlin")

includeBuild("../openapi/spring-boot-openapi-java")
includeBuild("../openapi/spring-boot-openapi-kotlin")

val properties = Properties()

val inputStream = rootDir.resolve("../spring-boot-build-configuration/src/main/resources/version.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}


pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}
