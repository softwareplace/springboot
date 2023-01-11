
plugins {
    `maven-publish`
    `kotlin-dsl`
    kotlin("jvm") version "1.7.22"
    id("org.openapi.generator") version "5.3.0"
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
}

java {
    withJavadocJar()
    withSourcesJar()
}

val sourceGroup = "com.gradle.kts.build.spring.openapi"
group = sourceGroup

version = "1.0.0"

gradlePlugin {
    plugins {
        register("build-spring-openapi-plugin") {
            id = "build-spring-openapi-plugin"
            implementationClass = "$sourceGroup.OpenApiPlugin"
        }
    }
}

dependencies {
    implementation("com.gradle.kts.build.configuration:build-configuration:1.0.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:6.2.0")
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    groupId.set("$group")
    packageName.set("$group")
    inputSpec.set("${projectDir.path}/src/main/resources/openapi/api.yaml")
    generateApiDocumentation.set(false)
    outputDir.set("${buildDir.path}/generated")
    apiPackage.set("$group.controller")
    invokerPackage.set("$group.invoker")
    modelPackage.set("$group.model")
    configOptions.set(
        mapOf(
            Pair("interfaceOnly", "true"),
            Pair("delegatePattern", "false"),
            Pair("useTags", "true"),
            Pair("generateApis", "true"),
        )
    )
}

