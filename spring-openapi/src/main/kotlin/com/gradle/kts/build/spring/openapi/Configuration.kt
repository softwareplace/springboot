package com.gradle.kts.build.spring.openapi

import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

fun OpenApiGeneratorGenerateExtension.apply(
    groupId: String,
    projectPath: String,
    generator: String = "spring",
    openApiYamlFilePath: String = "${projectPath}/src/main/resources/openapi.yaml"
) {
    generatorName.set(generator)
    this.groupId.set(groupId)
    packageName.set(groupId)
    inputSpec.set(openApiYamlFilePath)
    generateApiDocumentation.set(false)
    outputDir.set("${projectPath}/build/generated")
    apiPackage.set("${groupId}.controller")
    invokerPackage.set("${groupId}.invoker")
    modelPackage.set("${groupId}.model")
    configOptions.set(
        mapOf(
            Pair("interfaceOnly", "true"),
            Pair("delegatePattern", "false"),
            Pair("useTags", "true"),
            Pair("generateApis", "true"),
        )
    )
}

fun Project.openApiGenerateConfig(generator: String = "spring") {
    afterEvaluate {
        extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply(
            groupId = "$group",
            generator = generator,
            projectPath = projectDir.path,
        )
    }
}

fun Project.applyKotlinSourceSets() {
    extra["snippetsDir"] = file("build/generated-snippets")
    kotlinExtension.sourceSets["main"].kotlin.srcDir("$projectDir/build/generated/src/main/kotlin")
}

fun Project.applyJavaSourceSets() {
    extra["snippetsDir"] = file("build/generated-snippets")
    kotlinExtension.sourceSets["main"].kotlin.srcDir("$projectDir/build/generated/src/main/java")
}

fun Project.applyTasks() {
    tasks.withType<KotlinCompile> {
        dependsOn(tasks.findByName("openApiGenerate"))
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
}

