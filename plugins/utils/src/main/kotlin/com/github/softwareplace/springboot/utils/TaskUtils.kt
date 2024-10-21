package com.github.softwareplace.springboot.utils

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.run.BootRun


fun Project.submoduleConfig() {
    tasks.named<Jar>("bootJar").configure {
        enabled = false
    }

    tasks.named<BootRun>("bootRun").configure {
        enabled = false
    }

    extensions.getByName<SourceSetContainer>("sourceSets").apply {
        getByName("main").apply {
            java {
                exclude(
                    "**/SpringDocConfiguration.kt",
                )
            }
        }
    }

    kotlinExtension.sourceSets["main"].kotlin {
        exclude(
            "**/SpringDocConfiguration.kt",
        )
    }
}

fun Project.bootBuildImageConfig(action: Action<BootBuildImage>) {
    project.extensions.getByType(BootBuildImage::class.java).apply {
        action.execute(this)
    }
}

/**
 * Adds a map entry to the property value.
 *
 * @param key the key
 * @param value the value
 */
fun BootBuildImage.putEnv(key: String, value: String) {
    environment.put(key, value)
}

/**
 * Sets the value of the property to the given value, replacing whatever value the property already had.
 *
 * <p>
 * This method can also be used to discard the value of the property, by passing it {@code null}. When the
 * value is discarded (or has never been set in the first place), the convention (default value) for this
 * property, if specified, will be used to provide the value instead.
 * </p>
 *
 * @param value The value, can be null.
 */
fun BootBuildImage.setName(value: String) {
    imageName.set(value)
}

/**
 * Configures the native submodule tasks for the Gradle project by disabling specific tasks
 * related to native image building and execution.
 *
 * This method checks if the specified tasks exist in the project before disabling them.
 * If a task does not exist, it will simply skip the disabling operation without throwing an error.
 *
 * ### Disabled Tasks:
 * - `bootBuildImage`: Disables the boot build image task.
 * - `nativeCompile`: Disables the native compilation task.
 * - `nativeRun`: Disables the native execution task.
 * - `nativeCopy`: Disables the native copy task.
 * - `nativeTestCompile`: Disables the native test compilation task.
 *
 * ### Usage Example:
 *
 * ```kotlin
 * project.nativeSubmoduleConfig()
 * ```
 *
 * In this example, the `nativeSubmoduleConfig` method is called to disable the specified tasks
 * related to native image processing.
 */
fun Project.nativeSubmoduleConfig() {
    tasks.apply {
        afterEvaluate {
            // List of tasks to disable
            val tasksToDisable = listOf(
                "bootBuildImage",
                "nativeCompile",
                "nativeRun",
                "nativeCopy",
                "nativeTestCompile"
            )

            // Disable tasks if they exist
            tasksToDisable.forEach { taskName ->
                try {
                    named(taskName) {
                        enabled = false
                    }
                } catch (error: Exception) {
                    println("Task '$taskName' does not exist and cannot be disabled.")
                }
            }
        }
    }
}

