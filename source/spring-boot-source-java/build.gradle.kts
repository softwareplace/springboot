import com.github.softwareplace.plugin.buildconfiguration.Dependencies
import com.github.softwareplace.plugin.buildconfiguration.ORG_SPRINGFRAMEWORK_BOOT
import com.github.softwareplace.plugin.buildconfiguration.implementation
import com.github.softwareplace.plugin.buildconfiguration.kotlinDeps

plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
    id("spring-boot-build-configuration")
    id("org.jetbrains.kotlin.plugin.jpa") version System.getProperty("kotlinVersion")
//    id("org.graalvm.buildtools.native") version System.getProperty("graalvmBuildToolsNativeVersion")
    id("org.jetbrains.kotlin.plugin.spring") version System.getProperty("kotlinVersion")
    id("org.springframework.boot") version System.getProperty("springBootVersion")
    id("io.spring.dependency-management") version System.getProperty("springDependencyManagementVersion")
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

val sourceGroup = "com.github.softwareplace.plugin"
val currentVersion: String = System.getProperty("pluginsVersion")

group = sourceGroup
version = currentVersion

gradlePlugin {
    plugins {
        register("spring-boot-java-source-plugin") {
            id = "spring-boot-java-source-plugin"
            implementationClass = "$sourceGroup.javabuildsource.BuildSourcePlugin"
            version = currentVersion

            publishing {
                publications {
                    create<MavenPublication>("springBootJavaSourcePlugin") {
                        groupId = sourceGroup
                        artifactId = "spring-boot-java-source-plugin"
                        version = currentVersion
                        java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                        java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))

                        from(components["java"])
                    }
                }
            }
        }

        register("spring-boot-java-submodule-source-plugin") {
            id = "spring-boot-java-submodule-source-plugin"
            implementationClass = "$sourceGroup.javabuildsource.BuildSubmoduleSourcePlugin"
            version = currentVersion

            publishing {
                publications {
                    create<MavenPublication>("springBootJavaSubmoduleSourcePlugin") {
                        groupId = sourceGroup
                        artifactId = "spring-boot-java-submodule-source-plugin"
                        version = currentVersion
                        java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                        java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))

                        from(components["java"])
                    }
                }
            }
        }
    }
}

dependencies {
    kotlinDeps()
    implementation("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-gradle-plugin:${Dependencies.Version.springBootVersion}")
//    implementation("org.graalvm.buildtools:native-gradle-plugin:${Dependencies.Version.graalvmBuildToolsNativeVersion}")
    implementation("com.github.softwareplace.plugin:spring-boot-build-configuration:${System.getProperty("pluginsVersion")}")
}

