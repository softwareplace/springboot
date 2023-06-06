package com.gradle.kts.build.java.openapi

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class OpenApiPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.openapi.generator")
            applyJavaSourceSets()
            openApiGenerateConfig()
            applyTasks()
        }
    }


}
