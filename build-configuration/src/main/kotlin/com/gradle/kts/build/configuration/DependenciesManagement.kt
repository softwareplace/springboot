package com.gradle.kts.build.configuration

import org.gradle.api.Action
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.*
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo


const val ORG_SPRINGFRAMEWORK_BOOT = "org.springframework.boot"

inline fun <T> uncheckedCast(obj: Any?): T =
    obj as T

fun DependencyHandlerScope.kotlinDeps() {
    implementation(
        Dependencies.buildDependency(
            Dependencies.Group.orgJetbrainsKotlinSpring,
            Dependencies.Module.orgJetbrainsKotlinPluginSpring,
            Dependencies.Version.kotlin,
        )
    )
    implementation(
        Dependencies.buildDependency(
            Dependencies.Group.orgJetbrainsKotlinJpa,
            Dependencies.Module.orgJetbrainsKotlinPluginJpa,
            Dependencies.Version.kotlin,
        )
    )
    implementation(
        Dependencies.buildDependency(
            Dependencies.Group.orgJetbrainsKotlin,
            Dependencies.Module.kotlinReflect,
            Dependencies.Version.kotlin,
        )
    )

    implementation(
        Dependencies.buildDependency(
            Dependencies.Group.orgJetbrainsKotlin,
            Dependencies.Module.kotlinStdlibJdk8,
        )
    )

    implementation(
        Dependencies.buildDependency(
            Dependencies.Group.orgJetbrainsKotlin,
            Dependencies.Module.kotlinGradlePlugin,
            Dependencies.Version.kotlin
        )
    )
}

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

fun DependencyHandlerScope.implementation(target: ProjectDependency) {
    add("implementation", target)
}

fun DependencyHandler.implementation(
    dependencyNotation: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "implementation", dependencyNotation, dependencyConfiguration
)

fun DependencyHandler.testImplementation(
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

fun DependencyHandlerScope.addSpringframeworkBoot(module: String) {
    implementation("$ORG_SPRINGFRAMEWORK_BOOT:$module:${Dependencies.Version.springBoot}")
}

fun DependencyHandlerScope.addSpringframeworkBoot(
    module: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "implementation",
    "$ORG_SPRINGFRAMEWORK_BOOT:$module:${Dependencies.Version.springBoot}",
    dependencyConfiguration
)

fun DependencyHandlerScope.addSpringframeworkBootTest(module: String) {
    implementation("$ORG_SPRINGFRAMEWORK_BOOT:$module:${Dependencies.Version.springBoot}")
}

fun DependencyHandlerScope.addSpringframeworkBootTest(
    module: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "implementation",
    "$ORG_SPRINGFRAMEWORK_BOOT:$module:${Dependencies.Version.springBoot}",
    dependencyConfiguration
)

fun ConfigurationContainer.exclude(group: String, modules: Array<String>) {
    modules.forEach {
        all { exclude(group = group, module = it) }
    }
}


