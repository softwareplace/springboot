import com.gradle.kts.build.configuration.Dependencies
import com.gradle.kts.build.configuration.ORG_SPRINGFRAMEWORK_BOOT
import com.gradle.kts.build.configuration.implementation
import com.gradle.kts.build.configuration.kotlinDeps

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

val sourceGroup = "com.gradle.kts.kotlin.buildsource"
group = sourceGroup

version = "1.0.0"

gradlePlugin {
    plugins {
        register("source-plugin") {
            id = "source-plugin"
            implementationClass = "$sourceGroup.BuildSourcePlugin"
        }

        register("submodule-source-plugin") {
            id = "submodule-source-plugin"
            implementationClass = "$sourceGroup.BuildSubmoduleSourcePlugin"
        }
    }
}

dependencies {
    kotlinDeps()
    implementation("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-gradle-plugin:${Dependencies.Version.springBootVersion}")
//    implementation("org.graalvm.buildtools:native-gradle-plugin:${Dependencies.Version.graalvmBuildToolsNativeVersion}")
    implementation("com.gradle.kts.build.configuration:build-configuration:1.0.0")
}

