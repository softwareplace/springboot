package com.gradle.kts.build.configuration

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
            apply(plugin = "build-configuration-plugin")
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

    open fun customApply(target: Project) {}
}
