package com.github.softwareplace.springboot.buildconfiguration

import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

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
            apply(plugin = "org.springframework.boot")
            apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
            apply(plugin = "io.spring.dependency-management")
            apply(plugin = "org.jetbrains.kotlin.plugin.spring")
            apply(plugin = "org.gradle.maven-publish")
            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply(plugin = "${Dependencies.Group.pluginsGroup}.build-configuration")
            apply(plugin = "${Dependencies.Group.pluginsGroup}.versions")
        }
    }

    private fun Project.applyApplicationDependencies() {
        allprojects {
            fasterXmlJackson()
            kotlinDeps()
        }
    }

    open fun customApply(target: Project) {
        // Override if needed
    }
}
