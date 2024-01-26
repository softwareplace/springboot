import com.github.softwareplace.plugin.buildconfiguration.Dependencies
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    `maven-publish`
    `kotlin-dsl`
    id("com.github.softwareplace.plugin.spring-boot-java-source-plugin") version System.getProperty("pluginsVersion")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.github.softwareplace.plugin"
val currentVersion: String = System.getProperty("pluginsVersion")

group = sourceGroup
version = currentVersion

publishing {
    publications {
        create<MavenPublication>("springBootJavaOpenapiPlugin") {
            groupId = sourceGroup
            artifactId = "spring-boot-java-openapi-plugin"
            version = currentVersion
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
            version = currentVersion
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


