import com.gradle.kts.build.configuration.Dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    `kotlin-dsl`
    kotlin("jvm") version "1.7.22"
    id("build-configuration-plugin")
    id("org.jetbrains.kotlin.plugin.spring") version "1.7.22"

}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
}

java {
    withJavadocJar()
    withSourcesJar()
}

val sourceGroup = "com.gradle.kts.build.source"
group = sourceGroup

version = "1.0.0"

gradlePlugin {
    plugins {
        register("build-source-plugin") {
            id = "build-source-plugin"
            implementationClass = "$sourceGroup.BuildSourcePlugin"
        }
        register("build-source-application-plugin") {
            id = "build-source-application-plugin"
            implementationClass = "$sourceGroup.ApplicationPlugin"
        }
        register("build-source-project-plugin") {
            id = "build-source-project-plugin"
            implementationClass = "$sourceGroup.ProjectPlugin"
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    implementation(
        Dependencies.buildDependency(
            Dependencies.LibDomain.comGradleKtsConfiguration,
            Dependencies.TargetLib.buildConfiguration,
            Dependencies.Version.comGradleKtsConfiguration,
        )
    )

    implementation(
        Dependencies.buildDependency(
            Dependencies.LibDomain.orgJetbrainsKotlin,
            Dependencies.TargetLib.kotlinReflect,
            Dependencies.Version.kotlin,
        )
    )
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
