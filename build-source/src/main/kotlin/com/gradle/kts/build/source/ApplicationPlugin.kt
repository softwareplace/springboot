package com.gradle.kts.build.source

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import com.gradle.kts.build.configuration.*

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyApplicationDependencies()
            applyPlugins()
        }
    }

    private fun Project.applyPlugins() {
        plugins.apply("org.jetbrains.kotlin.jvm")
    }

    private fun Project.applyApplicationDependencies() {
        dependencies {
            kotlin()
            springSecurity()
            fasterXmlJackson()
        }
    }
}
