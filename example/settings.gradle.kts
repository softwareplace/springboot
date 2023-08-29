import java.util.*

include(":security")
rootProject.name = "example"
include(":java-example")
include(":kotlin-example")
include(":webflux-example")

project(":security").projectDir = file("shared-modules/security")

includeBuild("../build-configuration")
includeBuild("../source/source-java")
includeBuild("../source/source-kotlin")

includeBuild("../openapi/openapi-java")
includeBuild("../openapi/openapi-kotlin")

val properties = Properties()
val inputStream = rootDir.resolve("../build-configuration/gradle.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}
