package com.github.softwareplace.plugin.kotlinbuildsource

import com.github.softwareplace.plugin.buildconfiguration.BasePluginConfiguration
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

open class BuildSourcePlugin : BasePluginConfiguration() {

    override fun customApply(target: Project) {
        with(target) {
            allprojects {
                apply(plugin = "org.jetbrains.kotlin.kapt")
            }
        }
    }
}
