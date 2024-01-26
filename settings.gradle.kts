import java.util.*

rootProject.name = "spring-boot-builder-plugin"


include(":spring-boot-build-configuration")
include(":spring-boot-source-kotlin")
include(":spring-boot-source-java")
include(":spring-boot-openapi-java")
include(":spring-boot-openapi-kotlin")
include(":spring-boot-submodule-source-java")
include(":spring-boot-submodule-source-kotlin")

project(":spring-boot-build-configuration").projectDir = file("spring-boot-build-configuration")
project(":spring-boot-source-kotlin").projectDir = file("spring-boot-source-kotlin")
project(":spring-boot-source-java").projectDir = file("spring-boot-source-java")
project(":spring-boot-openapi-java").projectDir = file("spring-boot-openapi-java")
project(":spring-boot-openapi-kotlin").projectDir = file("spring-boot-openapi-kotlin")
project(":spring-boot-submodule-source-java").projectDir = file("spring-boot-submodule-source-java")
project(":spring-boot-submodule-source-kotlin").projectDir = file("spring-boot-submodule-source-kotlin")

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
