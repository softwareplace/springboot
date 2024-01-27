import java.util.*

includeBuild("../plugins/build-configuration")

includeBuild("../plugins/java")
includeBuild("../plugins/java-openapi")

includeBuild("../plugins/kotlin")
includeBuild("../plugins/kotlin-openapi")

val properties = Properties()
val inputStream = rootDir.resolve("../plugins/build-configuration/src/main/resources/version.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}