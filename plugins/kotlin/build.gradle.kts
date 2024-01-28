import com.github.softwareplace.springboot.buildconfiguration.Dependencies
import com.github.softwareplace.springboot.buildconfiguration.getTag
import com.github.softwareplace.springboot.buildconfiguration.implementation
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    `kotlin-dsl`
    `maven-publish`
    id("com.github.softwareplace.springboot.build-configuration")
    id("org.jetbrains.kotlin.plugin.jpa") version System.getProperty("kotlinVersion")
    id("org.jetbrains.kotlin.plugin.spring") version System.getProperty("kotlinVersion")
    id("org.springframework.boot") version System.getProperty("springBootVersion")
    id("io.spring.dependency-management") version System.getProperty("springDependencyManagementVersion")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.github.softwareplace.springboot"
val tagVersion = project.getTag()

group = sourceGroup
version = tagVersion

tasks.named<Jar>("bootJar").configure {
    enabled = false
}

tasks.named<BootRun>("bootRun").configure {
    enabled = false
}

gradlePlugin {

    plugins {
        register("kotlin") {
            id = "$sourceGroup.springboot.kotlin"
            implementationClass = "$sourceGroup.kotlin.BuildSourcePlugin"
        }

        register("kotlin-submodule") {
            id = "$sourceGroup.kotlin-submodule"
            implementationClass = "$sourceGroup.kotlin.BuildSubmoduleSourcePlugin"
        }

        register("kotlin-openapi") {
            id = "$sourceGroup.kotlin-openapi"
            implementationClass = "$sourceGroup.kotlin.openapi.OpenapiPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = "kotlin"
        }

        create<MavenPublication>("kotlinSubmoduleRelease") {
            artifactId = "kotlin-submodule"
        }

        create<MavenPublication>("kotlinOpenaiRelease") {
            artifactId = "kotlin-openapi"
        }
    }
}

dependencies {
    System.setProperty("kotlin-spring", "${projectDir}/src/main/resources/kotlin-spring")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}

