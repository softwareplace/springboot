import com.github.softwareplace.springboot.buildconfiguration.Dependencies
import com.github.softwareplace.springboot.buildconfiguration.getTag
import com.github.softwareplace.springboot.buildconfiguration.implementation
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
    id("com.github.softwareplace.springboot.build-configuration")
    id("org.jetbrains.kotlin.plugin.jpa") version System.getProperty("kotlinVersion")
    id("org.jetbrains.kotlin.plugin.spring") version System.getProperty("kotlinVersion")
    id("org.springframework.boot") version System.getProperty("springBootVersion")
    id("io.spring.dependency-management") version System.getProperty("springDependencyManagementVersion")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

private val basePluginPath = "com.github.softwareplace.springboot"

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

allprojects {
    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
        gradlePluginPortal()

        mavenLocal {
            content {
                includeModule(basePluginPath, "java")
                includeModule(basePluginPath, "kotlin")
                includeModule(basePluginPath, "java-submodule")
                includeModule(basePluginPath, "kotlin-submodule")
            }
        }
        maven("https://jitpack.io") {
            content {
                includeModule(basePluginPath, "java")
                includeModule(basePluginPath, "kotlin")
                includeModule(basePluginPath, "java-submodule")
                includeModule(basePluginPath, "kotlin-submodule")
            }
        }
    }
}

gradlePlugin {
    plugins {
        create("kotlin") {
            id = "$sourceGroup.kotlin"
            implementationClass = "$sourceGroup.kotlin.BuildSourcePlugin"
        }

        create("kotlin-submodule") {
            id = "$sourceGroup.kotlin-submodule"
            implementationClass = "$sourceGroup.kotlin.BuildSubmoduleSourcePlugin"
        }

        create("kotlin-openapi") {
            id = "$sourceGroup.kotlin-openapi"
            implementationClass = "$sourceGroup.kotlin.openapi.OpenapiPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = "kotlin"
            groupId = sourceGroup
            from(components["java"])
        }

        create<MavenPublication>("kotlinSubmoduleRelease") {
            artifactId = "kotlin-submodule"
            groupId = sourceGroup
            from(components["java"])
        }

        create<MavenPublication>("kotlinOpenaiRelease") {
            artifactId = "kotlin-openapi"
            groupId = sourceGroup
            from(components["java"])
        }
    }
}

dependencies {
    System.setProperty("kotlin-spring", "${projectDir}/src/main/resources/kotlin-spring")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}

