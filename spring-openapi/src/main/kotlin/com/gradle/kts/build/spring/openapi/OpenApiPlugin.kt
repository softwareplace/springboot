package com.gradle.kts.build.spring.openapi

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

class OpenApiPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.openapi.generator")
            applySourceSets()
            openApiGenerateConfig()
            applyTasks()
        }
    }

    private fun Project.openApiGenerateConfig() {
        afterEvaluate {
            extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply(
                groupId = "$group",
                projectPath = projectDir.path,
            )
        }
    }

    private fun Project.applySourceSets() {
        extra["snippetsDir"] = file("build/generated-snippets")
        kotlinExtension.sourceSets["main"].kotlin.srcDir("$projectDir/build/generated/src/main/kotlin")
    }

    private fun Project.applyTasks() {
        tasks.withType<KotlinCompile> {
            dependsOn(tasks.findByName("openApiGenerate"))
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }
    }
}
