package com.gradle.kts.build.source

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class BuildSourcePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyTask()
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
        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }

        tasks.withType<Test> {
            useJUnitPlatform()
        }
    }

}
