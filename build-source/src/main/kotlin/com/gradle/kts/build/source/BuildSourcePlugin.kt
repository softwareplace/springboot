package com.gradle.kts.build.source

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.withType

class BuildSourcePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyTask()
            configurations.apply {
                all { exclude("org.springframework.boot", "spring-boot-starter-logging") }
            }
        }
    }

    private fun Project.applyTask() {
        tasks.withType<Test> {
            description = "Runs unit tests"
            useJUnitPlatform()
            testLogging {
                showExceptions = true
                showStackTraces = true
                exceptionFormat = TestExceptionFormat.FULL
                events = mutableSetOf(
                    TestLogEvent.FAILED,
                    TestLogEvent.SKIPPED
                )
            }
        }
    }

}
