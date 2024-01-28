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

java {
    withJavadocJar()
    withSourcesJar()
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
        create("java") {
            id = "$sourceGroup.java"
            implementationClass = "$sourceGroup.java.BuildSourcePlugin"
        }

        create("java-submodule") {
            id = "$sourceGroup.java-submodule"
            implementationClass = "$sourceGroup.java.SubmoduleSourcePlugin"
        }

        create("java-openapi") {
            id = "$sourceGroup.java-openapi"
            implementationClass = "$sourceGroup.java.openapi.OpenapiPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = "java"
            groupId = sourceGroup
            from(components["java"])
        }

        create<MavenPublication>("javaSubmoduleRelease") {
            artifactId = "java-submodule"
            groupId = sourceGroup
            from(components["java"])
        }

        create<MavenPublication>("javaOpenapiRelease") {
            artifactId = "java-openapi"
            groupId = sourceGroup
            from(components["java"])
        }
    }
}

dependencies {
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


