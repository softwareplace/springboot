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
val currentVersion = "1.0.0"

group = sourceGroup
version = currentVersion

gradlePlugin {
    plugins {
        register("source-plugin") {
            id = "source-plugin"
            implementationClass = "$sourceGroup.BuildSourcePlugin"
            version = currentVersion
        }

        register("submodule-source-plugin") {
            id = "submodule-source-plugin"
            implementationClass = "$sourceGroup.BuildSubmoduleSourcePlugin"
            version = currentVersion
        }
    }
}

java {
    sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
    targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(System.getProperty("jdkVersion")))
    }
}

dependencies {
    kotlinDeps()
    implementation("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-gradle-plugin:${Dependencies.Version.springBootVersion}")
//    implementation("org.graalvm.buildtools:native-gradle-plugin:${Dependencies.Version.graalvmBuildToolsNativeVersion}")
    implementation("com.gradle.kts.build.configuration:build-configuration:1.0.0")
}

