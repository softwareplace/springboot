package com.gradle.kts.build.spring.openapi

import com.gradle.kts.build.configuration.Dependencies
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

enum class DocumentationProvider(val type: String) {
    SPRING_DOC("springdoc"),
    NONE("none"),
    SOURCE("source")
}

fun OpenApiGeneratorGenerateExtension.apply(
    groupId: String,
    projectPath: String,
    generator: String = "spring",
    openApiYamlFilePath: String = "${projectPath}/src/main/resources/openapi.yaml"
) {
    importMappings.set(
        mapOf(
            "java.time.OffsetDateTime" to "java.time.LocalDateTime"
        )
    )
    typeMappings.set(
        mapOf(
            "local-date-time" to "java.time.LocalDateTime"
        )
    )

    generatorName.set(generator)
    this.groupId.set(groupId)
    packageName.set(groupId)
    inputSpec.set(openApiYamlFilePath)
    generateApiDocumentation.set(false)
    outputDir.set("${projectPath}/build/generated")
    apiPackage.set("${groupId}.controller")
    invokerPackage.set("${groupId}.invoker")
    apiNameSuffix.set("Controller")
    modelPackage.set("${groupId}.model")
    skipOperationExample.set(true)
    configOptions.set(
        mapOf(
            "apiSuffix" to "Controller",
            "interfaceOnly" to "true",
            "skipDefaultInterface" to "true",
            "defaultInterfaces" to "false",
            "delegatePattern" to "false",
            "documentationProvider" to DocumentationProvider.SPRING_DOC.type,
            "serializationLibrary" to "jackson",
            "gradleBuildFile" to "false",
            "enumPropertyNaming" to "original",
            "exceptionHandler" to "false",
            "useSpringBoot3" to "true",
            "useSwaggerUI" to "false",
            "useTags" to "true",
            "generateApis" to "false",
            "java8" to "true"
        )
    )
}

fun Project.openApiGenerateConfig(
    generator: String = "spring",
) {
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
    kotlinExtension.sourceSets["main"].kotlin {
        srcDir("$projectDir/build/generated/src/main/kotlin")
        exclude(
            "**/ApiUtil.kt",
            "**/SpringDocConfiguration.kt",
        )
    }
}

fun Project.applyJavaSourceSets() {
    extra["snippetsDir"] = file("build/generated-snippets")
    extensions.getByName<SourceSetContainer>("sourceSets").apply {
        getByName("main").apply {
            java {
                srcDir("$projectDir/build/generated/src/main/java")
                exclude(
                    "**/ApiUtil.java",
                    "**/SpringDocConfiguration.java",
                )
            }
        }
    }
}

fun Project.applyTasks() {
    tasks.withType<KotlinCompile> {
        dependsOn(tasks.findByName("openApiGenerate"))
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = Dependencies.Version.jdk
        }
    }
}
