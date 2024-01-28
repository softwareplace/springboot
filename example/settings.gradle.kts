import java.util.*

include(":security")
//include(":java-example")
include(":kotlin-example")
//include(":webflux-example")

project(":security").projectDir = file("shared-modules/security")

includeBuild("../plugins/build-configuration")
includeBuild("../plugins/java")
includeBuild("../plugins/kotlin")

val properties = Properties()
val inputStream = rootDir.resolve("../gradle.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}
