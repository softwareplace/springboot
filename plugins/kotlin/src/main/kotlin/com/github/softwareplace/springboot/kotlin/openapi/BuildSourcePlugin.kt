package com.github.softwareplace.springboot.kotlin.openapi

import com.github.softwareplace.springboot.buildconfiguration.BasePluginConfiguration
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
