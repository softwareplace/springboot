package com.gradle.kts.build.source

import com.gradle.kts.build.configuration.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

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
            fasterXmlJackson()
            springSecurity()
            jsonWebToken()
            jsonLogger()
            passay()
            kotlin()
            test()
        }
    }
}

