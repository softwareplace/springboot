import java.util.*

include(":security")
include(":java-example")
include(":kotlin-example")
include(":webflux-example")

project(":security").projectDir = file("shared-modules/security")

includeBuild("../plugins/build-configuration")
includeBuild("../plugins/java")
includeBuild("../plugins/kotlin")
includeBuild("../plugins/java-openapi")
includeBuild("../plugins/kotlin-openapi")
includeBuild("../plugins/java-submodule")
includeBuild("../plugins/kotlin-submodule")

val properties = Properties()
val inputStream = rootDir.resolve("../plugins/build-configuration/src/main/resources/version.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}
