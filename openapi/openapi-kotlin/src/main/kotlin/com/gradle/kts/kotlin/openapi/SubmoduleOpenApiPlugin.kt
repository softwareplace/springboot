package com.gradle.kts.kotlin.openapi

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class SubmoduleOpenApiPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.openapi.generator")
            openApiGenerateConfig(generator = "kotlin-spring")
            applyKotlinSourceSets()
            applyTasks()
        }
    }
}
