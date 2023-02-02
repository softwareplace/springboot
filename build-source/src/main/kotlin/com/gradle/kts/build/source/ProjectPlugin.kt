package com.gradle.kts.build.source

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

class ProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            allprojects {
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
