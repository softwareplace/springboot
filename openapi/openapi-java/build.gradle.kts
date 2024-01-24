import com.gradle.kts.build.configuration.Dependencies

plugins {
    `maven-publish`
    `kotlin-dsl`
    id("java-source-plugin")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.gradle.kts.java.openapi"
val currentVersion = "1.0.0"

group = sourceGroup
version = currentVersion

gradlePlugin {
    plugins {
        register("java-openapi-plugin") {
            id = "java-openapi-plugin"
            implementationClass = "$sourceGroup.OpenApiPlugin"
            version = currentVersion
        }
    }
}

dependencies {
    implementation("com.gradle.kts.build.configuration:build-configuration:1.0.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


