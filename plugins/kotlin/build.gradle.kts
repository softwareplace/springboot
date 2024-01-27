import com.github.softwareplace.springboot.plugin.buildconfiguration.Dependencies
import com.github.softwareplace.springboot.plugin.buildconfiguration.getTag
import com.github.softwareplace.springboot.plugin.buildconfiguration.implementation
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    `kotlin-dsl`
    `maven-publish`
    id("com.github.softwareplace.springboot.plugin.build-configuration")
    id("org.jetbrains.kotlin.plugin.jpa") version System.getProperty("kotlinVersion")
    id("org.jetbrains.kotlin.plugin.spring") version System.getProperty("kotlinVersion")
    id("org.springframework.boot") version System.getProperty("springBootVersion")
    id("io.spring.dependency-management") version System.getProperty("springDependencyManagementVersion")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.github.softwareplace.springboot.plugin"

group = sourceGroup

tasks.named<Jar>("bootJar").configure {
    enabled = false
}

tasks.named<BootRun>("bootRun").configure {
    enabled = false
}

gradlePlugin {
    plugins {
        register("kotlin") {
            id = "com.github.softwareplace.springboot.plugin.kotlin"
            implementationClass = "$sourceGroup.kotlin.BuildSourcePlugin"
            version = project.getTag()
        }

        publishing {
            publications {
                create<MavenPublication>("release") {
                    groupId = sourceGroup
                    artifactId = "kotlin"
                    java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                    java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                    version = project.getTag()

                    from(components["java"])
                }
            }
        }
    }

    plugins {
        register("kotlin-submodule") {
            id = "com.github.softwareplace.springboot.plugin.kotlin-submodule"
            implementationClass = "$sourceGroup.kotlin.BuildSubmoduleSourcePlugin"
            version = project.getTag()
        }

        publishing {
            publications {
                create<MavenPublication>("kotlinSubmoduleRelease") {
                    groupId = sourceGroup
                    artifactId = "kotlin"
                    java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                    java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                    version = project.getTag()
                    from(components["java"])
                }
            }
        }
    }

    plugins {
        register("kotlin-openapi") {
            id = "com.github.softwareplace.springboot.plugin.kotlin-openapi"
            implementationClass = "$sourceGroup.kotlin.openapi.OpenApiPlugin"
            version = project.getTag()
        }

        publishing {
            publications {
                create<MavenPublication>("kotlinOpenAiRelease") {
                    groupId = sourceGroup
                    artifactId = "kotlin-openapi"
                    java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                    java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                    version = project.getTag()
                    from(components["java"])
                }
            }
        }
    }
}

dependencies {
    System.setProperty("kotlin-spring", "${projectDir}/src/main/resources/kotlin-spring")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}

