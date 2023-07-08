import com.gradle.kts.build.configuration.Dependencies

plugins {
    `maven-publish`
    `kotlin-dsl`
    id("java-source-plugin")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.gradle.kts.java.openapi"
group = sourceGroup

version = "1.0.0"

gradlePlugin {
    plugins {
        register("java-openapi-plugin") {
            id = "java-openapi-plugin"
            implementationClass = "$sourceGroup.OpenApiPlugin"
        }
    }
}

dependencies {
    implementation("com.gradle.kts.build.configuration:build-configuration:1.0.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


