package com.gradle.kts.kotlin.buildsource

import com.gradle.kts.build.configuration.Dependencies
import com.gradle.kts.build.configuration.implementation
import com.gradle.kts.build.configuration.kotlinDeps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

open class BuildSourcePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyRepositories()
            applyApplicationDependencies()
            applyPlugins()
            applyTask()
            applyCustomTasks()
        }
    }

    private fun Project.applyApplicationDependencies() {
        allprojects {
            afterEvaluate {
                dependencies {
                    fasterXmlJackson()
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
            apply(plugin = "org.springframework.boot")
//            apply(plugin = "org.graalvm.buildtools.native")
            apply(plugin = "io.spring.dependency-management")
            apply(plugin = "org.jetbrains.kotlin.plugin.spring")
            apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply(plugin = "org.gradle.maven-publish")
            apply(plugin = "org.jetbrains.kotlin.kapt")
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
                    jvmTarget = Dependencies.Version.jdk
                    freeCompilerArgs += "-Xjsr305=strict"
                }
            }

            tasks.withType<Jar> {
                dependsOn(tasks.withType<KotlinCompile>())
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            }

            tasks.named<JavaCompile>("compileJava") {
                inputs.files(tasks.named("processResources"))
            }
        }
    }

    open fun Project.applyCustomTasks() {
        // Override this method to apply custom tasks
    }

    open fun DependencyHandlerScope.applyDependencies() {
        implementation("com.gradle.kts.build.configuration:build-configuration:1.0.0")
    }
}
