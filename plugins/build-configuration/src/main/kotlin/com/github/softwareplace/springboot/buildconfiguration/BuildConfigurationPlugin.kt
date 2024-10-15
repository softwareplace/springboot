package com.github.softwareplace.springboot.buildconfiguration

import com.github.softwareplace.springboot.versions.jdkVersion
import com.github.softwareplace.springboot.versions.kotlinVersion
import com.github.softwareplace.springboot.versions.snakeYamlVersion
import com.github.softwareplace.springboot.versions.springBootVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class BuildConfigurationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyTasks()
            applyRepositories()
            kotlinDeps()
            dependencies {
                implementation("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
            }
        }
    }

    private fun Project.applyTasks() {
        allprojects {
            kotlinExtension.sourceSets["main"].kotlin {
                srcDir("$projectDir/build/generated/src/main/kotlin")
            }

            try {
                tasks.getByName<Jar>("jar") {
                    archiveClassifier.set("")
                }
            } catch (err: Throwable) {
                println(err)
            }

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
                compilerOptions {
                    jvmTarget.set(JvmTarget.valueOf("JVM_${jdkVersion}"))
                    this.freeCompilerArgs.set(
                        listOf(
                            "-Xjsr305=strict",
                        )
                    )
                }
            }

            tasks.withType<JavaCompile> {
                sourceCompatibility = jdkVersion
                targetCompatibility = jdkVersion
                inputs.files(tasks.named("processResources"))
            }

            tasks.withType<Jar> {
                duplicatesStrategy = DuplicatesStrategy.INCLUDE
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

                    if (requested.group == "com.github.softwareplace.springboot") {
                        useVersion(System.getProperty("pluginsVersion"))
                    }

                    if (requested.group == "org.yaml") {
                        useVersion(snakeYamlVersion)
                    }

                    if (requested.group == "org.jetbrains.kotlin") {
                        useVersion(kotlinVersion)
                    }
                }
            }

            repositories {
                mavenCentral()
                mavenLocal()
                gradlePluginPortal()
                maven("https://jitpack.io")
                maven("https://repo.spring.io/milestone")
                maven("https://plugins.gradle.org/m2/")  // For Gradle plugins
                maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
            }
        }
    }
}
