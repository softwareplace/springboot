import java.util.*

include(":security")
include(":java-example")
include(":kotlin-example")
include(":webflux-example")

project(":security").projectDir = file("shared-modules/security")

includeBuild("../build-configuration")
includeBuild("../java")
includeBuild("../kotlin")
includeBuild("../java-openapi")
includeBuild("../kotlin-openapi")
includeBuild("../java-submodule")
includeBuild("../kotlin-submodule")

val properties = Properties()
val inputStream = rootDir.resolve("../build-configuration/src/main/resources/version.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}