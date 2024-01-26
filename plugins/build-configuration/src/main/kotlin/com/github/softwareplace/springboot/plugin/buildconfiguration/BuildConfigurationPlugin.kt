package com.github.softwareplace.springboot.plugin.buildconfiguration

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class BuildConfigurationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            applyTasks()
            applyRepositories()
        }
    }

    private fun Project.applyPlugins() {
        allprojects {
            apply(plugin = "org.springframework.boot")
            apply(plugin = "io.spring.dependency-management")
            apply(plugin = "org.jetbrains.kotlin.plugin.spring")
            apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
            apply(plugin = "org.gradle.maven-publish")
            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply(plugin = "com.github.softwareplace.springboot.plugin.build-configuration")
        }
    }

    private fun Project.applyTasks() {
        allprojects {
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


            tasks.withType<KotlinCompile>().configureEach {
                kotlinOptions {
                    freeCompilerArgs += "-Xjsr305=strict"
                }
            }

            tasks.withType<JavaCompile> {
                sourceCompatibility = System.getProperty("jdkVersion")
                targetCompatibility = System.getProperty("jdkVersion")
                inputs.files(tasks.named("processResources"))
            }

            tasks.withType<Jar> {
                dependsOn(tasks.withType<KotlinCompile>())
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            }
        }
    }

    private fun Project.applyRepositories() {
        allprojects {
            configurations.all {
                resolutionStrategy.eachDependency {
                    if (requested.group == "org.springframework.boot") {
                        useVersion(System.getProperty("springBootVersion"))
                    }
                    if (requested.group == "org.yaml") {
                        useVersion(System.getProperty("snakeYamlVersion"))
                    }
                }
            }

            repositories {
                mavenCentral()
                mavenLocal()
                gradlePluginPortal()
                maven("https://jitpack.io")
                maven("https://repo.spring.io/milestone")
            }
        }
    }
}
