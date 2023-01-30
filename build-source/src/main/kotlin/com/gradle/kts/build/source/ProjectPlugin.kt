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
