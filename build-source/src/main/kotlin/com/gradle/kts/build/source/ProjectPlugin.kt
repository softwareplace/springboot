package com.gradle.kts.build.source

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.*

class ProjectPlugin : Plugin<Project> {
//    private val Project.java: org.gradle.api.plugins.JavaPluginExtension get() =
//        (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("java") as org.gradle.api.plugins.JavaPluginExtension

    override fun apply(target: Project) {
        with(target) {
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
    }
}
