import java.util.*

includeBuild("../plugins/versions")
includeBuild("../plugins/build-configuration")
includeBuild("../plugins/utils")
includeBuild("../plugins/java")
includeBuild("../plugins/kotlin")

val properties = Properties()
val inputStream =
    rootDir.resolve("../gradle.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}

