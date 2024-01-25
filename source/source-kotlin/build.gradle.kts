import com.github.softwareplace.plugin.buildconfiguration.Dependencies
import com.github.softwareplace.plugin.buildconfiguration.ORG_SPRINGFRAMEWORK_BOOT
import com.github.softwareplace.plugin.buildconfiguration.kotlinDeps

plugins {
    `kotlin-dsl`
    `maven-publish`
    id("build-configuration-plugin")
    id("org.jetbrains.kotlin.plugin.jpa") version System.getProperty("kotlinVersion")
//    id("org.graalvm.buildtools.native") version System.getProperty("graalvmBuildToolsNativeVersion")
    id("org.jetbrains.kotlin.plugin.spring") version System.getProperty("kotlinVersion")
    id("org.springframework.boot") version System.getProperty("springBootVersion")
    id("io.spring.dependency-management") version System.getProperty("springDependencyManagementVersion")
}

val sourceGroup = "com.github.softwareplace.plugin.kotlinbuildsource"
val currentVersion: String = System.getProperty("pluginsVersion")

group = sourceGroup
version = currentVersion

gradlePlugin {
    plugins {
        register("source-plugin") {
            id = "source-plugin"
            implementationClass = "$sourceGroup.BuildSourcePlugin"
            version = currentVersion
            publishing {
                publications {
                    create<MavenPublication>("sourcePlugin") {
                        groupId = sourceGroup
                        artifactId = "source-plugin"
                        version = currentVersion
                        java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
                        java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))

                        from(components["java"])
                    }
                }
            }
        }

        register("submodule-source-plugin") {
            id = "submodule-source-plugin"
            implementationClass = "$sourceGroup.BuildSubmoduleSourcePlugin"
            version = currentVersion

            publishing {
                publications {
                    create<MavenPublication>("submoduleSourcePlugin") {
                        groupId = sourceGroup
                        artifactId = "submodule-source-plugin"
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
    implementation("com.github.softwareplace.plugin.buildconfiguration:build-configuration:${System.getProperty("pluginsVersion")}")
//    implementation("org.graalvm.buildtools:native-gradle-plugin:${Dependencies.Version.graalvmBuildToolsNativeVersion}")
}

