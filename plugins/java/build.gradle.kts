import com.github.softwareplace.springboot.buildconfiguration.Dependencies
import com.github.softwareplace.springboot.buildconfiguration.ORG_SPRINGFRAMEWORK_BOOT
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

java {
    withJavadocJar()
    withSourcesJar()
}

allprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == ORG_SPRINGFRAMEWORK_BOOT) {
                useVersion(System.getProperty("springBootVersion"))
            }
        }
    }
}

val tagVersion = getTag()
val sourceGroup = "com.github.softwareplace.springboot"

version = tagVersion
group = sourceGroup

tasks.named<Jar>("bootJar").configure {
    enabled = false
}

tasks.named<BootRun>("bootRun").configure {
    enabled = false
}

gradlePlugin {
    plugins {
        register("java") {
            id = "$sourceGroup.java"
            implementationClass = "$sourceGroup.java.BuildSourcePlugin"
        }

        register("java-submodule") {
            id = "$sourceGroup.java-submodule"
            implementationClass = "$sourceGroup.java.SubmoduleSourcePlugin"
        }

        register("java-openapi") {
            id = "$sourceGroup.java-openapi"
            implementationClass = "$sourceGroup.openapi.OpenapiPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = "java"
        }

        create<MavenPublication>("javaSubmoduleRelease") {
            artifactId = "java-submodule"
        }

        create<MavenPublication>("javaOpenapiRelease") {
            artifactId = "java-openapi"
        }
    }
}

dependencies {
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


