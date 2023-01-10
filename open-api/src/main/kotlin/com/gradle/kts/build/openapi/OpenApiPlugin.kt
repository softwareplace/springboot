package com.gradle.kts.build.openapi

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.withType

class OpenApiPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyTask()
        }
    }

}
