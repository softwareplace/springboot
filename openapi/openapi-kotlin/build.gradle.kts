import com.gradle.kts.build.configuration.Dependencies


plugins {
    `maven-publish`
    `kotlin-dsl`
    id("source-plugin")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.gradle.kts.kotlin.openapi"
group = sourceGroup

version = "1.0.0"


gradlePlugin {
    plugins {
        register("openapi-plugin") {
            id = "openapi-plugin"
            implementationClass = "$sourceGroup.OpenApiPlugin"
        }
    }
}

dependencies {
    System.setProperty("kotlin-spring", "${projectDir}/src/main/resources/kotlin-spring")
    implementation("com.gradle.kts.build.configuration:build-configuration:1.0.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


