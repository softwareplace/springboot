package com.gradle.kts.build.source

import com.gradle.kts.build.configuration.kotlinDeps
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class BuildSourcePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyRepositories()
            applyApplicationDependencies()
            applyPlugins()
            applyTask()
        }
    }

    private fun Project.applyApplicationDependencies() {
        allprojects {
            afterEvaluate {
                dependencies {
                    fasterXmlJackson()
                    jsonLogger()
                    kotlinDeps()
                }
            }
        }
    }

    private fun Project.applyRepositories() {
        allprojects {
            repositories {
                mavenCentral()
                mavenLocal()
                gradlePluginPortal()
                maven("https://jitpack.io")
            }
        }
    }

    private fun Project.applyPlugins() {
        allprojects {
            extensions.getByName<JavaPluginExtension>("java").sourceCompatibility = JavaVersion.VERSION_11
            apply(plugin = "org.springframework.boot")
            apply(plugin = "io.spring.dependency-management")
            apply(plugin = "org.jetbrains.kotlin.plugin.spring")
            apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply(plugin = "org.gradle.maven-publish")
            repositories {
                mavenCentral()
                mavenLocal()
                gradlePluginPortal()
                maven("https://jitpack.io")
                maven("https://repo.spring.io/milestone")
            }
        }
    }

    private fun Project.applyTask() {
        tasks.withType<Test> {
            description = "Runs unit tests"
            useJUnitPlatform()
            testLogging {
                showExceptions = true
                showStackTraces = true
                exceptionFormat = TestExceptionFormat.FULL
                events = mutableSetOf(
                    TestLogEvent.FAILED,
                    TestLogEvent.SKIPPED
                )
            }
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
    }

}
