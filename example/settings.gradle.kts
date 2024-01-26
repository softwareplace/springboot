import java.util.*

include(":security")
include(":java-example")
include(":kotlin-example")
include(":webflux-example")

project(":security").projectDir = file("shared-modules/security")

includeBuild("../spring-boot-build-configuration")
includeBuild("../spring-boot-source-java")
includeBuild("../spring-boot-source-kotlin")
includeBuild("../spring-boot-openapi-java")
includeBuild("../spring-boot-openapi-kotlin")
includeBuild("../spring-boot-submodule-source-java")
includeBuild("../spring-boot-submodule-source-kotlin")

val properties = Properties()
val inputStream = rootDir.resolve("../spring-boot-build-configuration/src/main/resources/version.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}