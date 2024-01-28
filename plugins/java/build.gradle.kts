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

val sourceGroup = "com.github.softwareplace.springboot"

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
            id = "com.github.softwareplace.springboot.java"
            implementationClass = "$sourceGroup.java.BuildSourcePlugin"
            version = project.getTag()
        }

        publishing {
            publications {
                create<MavenPublication>("release") {
                    groupId = sourceGroup
                    artifactId = "java"
                    java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                    java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                    version = project.getTag()

                    from(components["java"])
                }
            }
        }
    }


    plugins {
        register("java-submodule") {
            id = "com.github.softwareplace.springboot.java-submodule"
            implementationClass = "$sourceGroup.java.SubmoduleSourcePlugin"
            version = project.getTag()
        }

        publishing {
            publications {
                create<MavenPublication>("javaSubmoduleRelease") {
                    groupId = sourceGroup
                    artifactId = "java-submodule"
                    java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                    java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                    version = project.getTag()

                    from(components["java"])
                }
            }
        }
    }

    plugins {
        register("java-openapi") {
            id = "com.github.softwareplace.springboot.java-openapi"
            implementationClass = "$sourceGroup.openapi.OpenapiPlugin"
            version = project.getTag()
        }

        publishing {
            publications {
                create<MavenPublication>("javaOpenapiRelease") {
                    groupId = sourceGroup
                    artifactId = "java-openapi"
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
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


