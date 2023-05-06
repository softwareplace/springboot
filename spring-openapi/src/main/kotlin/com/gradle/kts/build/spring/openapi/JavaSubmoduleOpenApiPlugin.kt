package com.gradle.kts.build.spring.openapi

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class JavaSubmoduleOpenApiPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.openapi.generator")
            applyJavaSourceSets()
            openApiGenerateConfig()
            applyTasks()
        }
    }


}
