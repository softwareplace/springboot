package com.gradle.kts.build.source

import org.gradle.api.Action
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo


inline fun <T> uncheckedCast(obj: Any?): T =
    obj as T

fun mapOfNonNullValuesOf(vararg entries: Pair<String, String?>): Map<String, String> =
    mutableMapOf<String, String>().apply {
        for ((k, v) in entries) {
            if (v != null) {
                put(k, v)
            }
        }
    }

fun excludeMapFor(group: String?, module: String?): Map<String, String> =
    mapOfNonNullValuesOf(
        "group" to group,
        "module" to module
    )

fun <T : ModuleDependency> T.exclude(group: String? = null, module: String? = null): T =
    uncheckedCast(exclude(excludeMapFor(group, module)))


fun DependencyHandlerScope.implementation(target: String) {
    add("implementation", target)
}

fun DependencyHandler.implementation(
    dependencyNotation: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "implementation", dependencyNotation, dependencyConfiguration
)

fun DependencyHandlerScope.testImplementation(target: String) {
    add("testImplementation", target)
}

fun DependencyHandlerScope.runtimeOnly(target: String) {
    add("runtimeOnly", target)
}

fun DependencyHandlerScope.annotationProcessor(target: String) {
    add("annotationProcessor", target)
}

