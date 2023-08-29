import java.util.Properties

val properties = Properties()
val inputStream = rootDir.resolve("build-configuration/gradle.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}
