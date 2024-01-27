import com.github.softwareplace.springboot.plugin.buildconfiguration.Dependencies
import com.github.softwareplace.springboot.plugin.buildconfiguration.ORG_SPRINGFRAMEWORK_BOOT
import com.github.softwareplace.springboot.plugin.buildconfiguration.implementation
import com.github.softwareplace.springboot.plugin.buildconfiguration.kotlinDeps
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    `maven-publish`
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.github.softwareplace.springboot.plugin.build-configuration")
    id("org.jetbrains.kotlin.plugin.spring") version System.getProperty("kotlinVersion")
    id("org.springframework.boot") version System.getProperty("springBootVersion")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.github.softwareplace.springboot.plugin"

group = sourceGroup

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = sourceGroup
            artifactId = "java-openapi"
            java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
            java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))

            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
    }
}

tasks.named<Jar>("bootJar").configure {
    enabled = false
}

tasks.named<BootRun>("bootRun").configure {
    enabled = false
}

gradlePlugin {
    plugins {
        register("java-openapi") {
            id = "com.github.softwareplace.springboot.plugin.java-openapi"
            implementationClass = "$sourceGroup.javaopenapi.OpenApiPlugin"
        }
    }
}

dependencies {
    kotlinDeps()
    implementation("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-gradle-plugin:${Dependencies.Version.springBootVersion}")
    implementation("com.github.softwareplace.springboot.plugin:build-configuration:${System.getProperty("pluginsVersion")}")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


