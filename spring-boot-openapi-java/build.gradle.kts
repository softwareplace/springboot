import com.github.softwareplace.plugin.buildconfiguration.Dependencies
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    `maven-publish`
    `kotlin-dsl`
    id("com.github.softwareplace.plugin.spring-boot-java-source-plugin")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.github.softwareplace.plugin"

group = sourceGroup

publishing {
    publications {
        create<MavenPublication>("springBootJavaOpenapiPlugin") {
            groupId = sourceGroup
            artifactId = "spring-boot-java-openapi-plugin"
            java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
            java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))

            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
    }
}

gradlePlugin {
    plugins {
        register("spring-boot-java-openapi-plugin") {
            id = "com.github.softwareplace.plugin.spring-boot-java-openapi-plugin"
            implementationClass = "$sourceGroup.javaopenapi.OpenApiPlugin"
        }
    }
}

tasks.named<Jar>("bootJar").configure {
    enabled = false
}

tasks.named<BootRun>("bootRun").configure {
    enabled = false
}

dependencies {
    implementation("com.github.softwareplace.plugin:spring-boot-build-configuration:${System.getProperty("pluginsVersion")}")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


