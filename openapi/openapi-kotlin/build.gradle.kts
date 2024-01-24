import com.gradle.kts.build.configuration.Dependencies


plugins {
    `maven-publish`
    `kotlin-dsl`
    id("source-plugin")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.gradle.kts.kotlin.openapi"
val currentVersion = "1.0.0"

group = sourceGroup
version = currentVersion


gradlePlugin {
    plugins {
        register("openapi-plugin") {
            id = "openapi-plugin"
            implementationClass = "$sourceGroup.OpenApiPlugin"
            version = currentVersion
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
    sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
    targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(System.getProperty("jdkVersion")))
    }
}

dependencies {
    System.setProperty("kotlin-spring", "${projectDir}/src/main/resources/kotlin-spring")
    implementation("com.gradle.kts.build.configuration:build-configuration:1.0.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


