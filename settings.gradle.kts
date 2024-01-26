import java.util.*

rootProject.name = "spring-boot-builder-plugin"

includeBuild("spring-boot-build-configuration")
includeBuild("source/spring-boot-source-java")
includeBuild("source/spring-boot-source-kotlin")
includeBuild("openapi/spring-boot-openapi-java")
includeBuild("openapi/spring-boot-openapi-kotlin")

apply("example/settings.gradle.kts")

val properties = Properties()
val inputStream = rootDir.resolve("spring-boot-build-configuration/src/main/resources/version.properties").inputStream()
properties.load(inputStream)

properties.forEach { (key, value) ->
    System.setProperty(key.toString(), value.toString())
}
