package com.gradle.kts.kotlin.buildsource

import com.gradle.kts.build.configuration.BasePluginConfiguration
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

open class BuildSourcePlugin : BasePluginConfiguration() {

    override fun customApply(target: Project) {
        with(target) {
            allprojects {
                apply(plugin = "org.jetbrains.kotlin.jvm")
            }
        }
    }
}
