package com.github.softwareplace.springboot.plugin.buildconfiguration

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
            apply(plugin = "com.github.softwareplace.springboot.plugin.build-configuration")

        }
    }

    private fun Project.applyApplicationDependencies() {
        allprojects {
            afterEvaluate {
                dependencies {
                    implementation("org.springframework.boot")
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
