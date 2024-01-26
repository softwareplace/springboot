import java.util.*

rootProject.name = "spring-boot-builder-plugin"


includeBuild("spring-boot-build-configuration")
includeBuild("source/spring-boot-source-java")
includeBuild("source/spring-boot-source-kotlin")
includeBuild("openapi/spring-boot-openapi-java")
includeBuild("openapi/spring-boot-openapi-kotlin")

include(":plugin-build-configuration")
include(":plugin-source-java")
include(":plugin-source-kotlin")
include(":plugin-openapi-java")
include(":plugin-openapi-kotlin")

project(":plugin-build-configuration").projectDir = file("spring-boot-build-configuration")
project(":plugin-source-java").projectDir = file("source/spring-boot-source-java")
project(":plugin-source-kotlin").projectDir = file("source/spring-boot-source-kotlin")
project(":plugin-openapi-java").projectDir = file("openapi/spring-boot-openapi-java")
project(":plugin-openapi-kotlin").projectDir = file("openapi/spring-boot-openapi-kotlin")

val properties = Properties()
val inputStream = rootDir.resolve("spring-boot-build-configuration/src/main/resources/version.properties").inputStream()
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
