package com.gradle.kts.build.spring.openapi

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class KotlinOpenApiPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.openapi.generator")
            applyKotlinSourceSets()
            openApiGenerateConfig(generator = "kotlin-spring")
            applyTasks()
        }
    }
}
