package com.github.softwareplace.springboot.buildconfiguration

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

abstract class BasePluginConfiguration : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            applyApplicationDependencies()
            customApply(target)
        }
    }

    private fun Project.applyPlugins() {
        allprojects {
            apply(plugin = "com.github.softwareplace.springboot.build-configuration")
            apply(plugin = "org.springframework.boot")
            apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
            apply(plugin = "io.spring.dependency-management")
            apply(plugin = "org.jetbrains.kotlin.plugin.spring")
            apply(plugin = "org.gradle.maven-publish")
            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply(plugin = "com.github.softwareplace.springboot.build-configuration")
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

    open fun customApply(target: Project) {
        // Override if needed
    }
}
