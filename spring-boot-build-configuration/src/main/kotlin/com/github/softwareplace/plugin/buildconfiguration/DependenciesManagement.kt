package com.github.softwareplace.plugin.buildconfiguration

import org.gradle.api.Action
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo
import org.gradle.kotlin.dsl.exclude


const val ORG_SPRINGFRAMEWORK_BOOT = "org.springframework.boot"

@Suppress("UNCHECKED_CAST")
fun <T> uncheckedCast(obj: Any?): T =
    obj as T

fun DependencyHandlerScope.fasterXmlJackson() {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Dependencies.Version.jacksonVersion}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${Dependencies.Version.jacksonVersion}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Dependencies.Version.jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${Dependencies.Version.jacksonVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Dependencies.Version.jacksonVersion}")
}

fun DependencyHandlerScope.kotlinDeps() {
    implementation(
        Dependencies.buildDependency(
            Dependencies.Group.orgJetbrainsKotlinSpring,
            Dependencies.Module.orgJetbrainsKotlinPluginSpring,
            Dependencies.Version.kotlinVersion,
        )
    )
    implementation(
        Dependencies.buildDependency(
            Dependencies.Group.orgJetbrainsKotlinJpa,
            Dependencies.Module.orgJetbrainsKotlinPluginJpa,
            Dependencies.Version.kotlinVersion,
        )
    )
    implementation(
        Dependencies.buildDependency(
            Dependencies.Group.orgJetbrainsKotlin,
            Dependencies.Module.kotlinReflect,
            Dependencies.Version.kotlinVersion,
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
            Dependencies.Version.kotlinVersion
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

fun DependencyHandlerScope.kaptAnnotationProcessor(target: String) {
    add("kapt", target)
}

fun DependencyHandlerScope.annotationProcessor(target: String) {
    add("annotationProcessor", target)
}

fun DependencyHandlerScope.addSpringframeworkBoot(module: String) {
    implementation("$ORG_SPRINGFRAMEWORK_BOOT:$module")
}

fun DependencyHandlerScope.addSpringframeworkBoot(
    module: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "implementation",
    "$ORG_SPRINGFRAMEWORK_BOOT:$module",
    dependencyConfiguration
)

fun DependencyHandlerScope.addSpringframeworkBootTest(module: String) {
    implementation("$ORG_SPRINGFRAMEWORK_BOOT:$module")
}

fun DependencyHandlerScope.addSpringframeworkBootTest(
    module: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "implementation",
    "$ORG_SPRINGFRAMEWORK_BOOT:$module",
    dependencyConfiguration
)

fun ConfigurationContainer.exclude(group: String, modules: Array<String>) {
    modules.forEach {
        all { exclude(group = group, module = it) }
    }
}


