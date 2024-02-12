package com.github.softwareplace.springboot.java.openapi

import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

private const val JAVA_LOCAL_DATE_TIME = "java.time.LocalDateTime"
private const val JAVA_LOCAL_DATE = "java.time.LocalDate"
private const val JAVA_LOCAL_TIME = "java.time.LocalTime"

fun OpenApiGeneratorGenerateExtension.apply(
    openApiSettings: OpenApiSettings,
    projectPath: String,
    openApiYamlFilePath: String = "${projectPath}/src/main/resources/${openApiSettings.swaggerFileName}"
) {

    val customImportingMapping: Map<String, String> = when (openApiSettings.overrideImportMapping) {
        true -> openApiSettings.importMapping
        else -> {
            val customImporting = mutableMapOf(
                "date" to JAVA_LOCAL_DATE,
                "local-date-time" to JAVA_LOCAL_DATE_TIME,
                "time" to JAVA_LOCAL_TIME
            )
            customImporting.putAll(openApiSettings.importMapping)
            customImporting
        }
    }

    schemaMappings.putAll(customImportingMapping)
    importMappings.putAll(customImportingMapping)
    typeMappings.putAll(customImportingMapping)

    val lombokAdditionalModelTypeANotations: List<String> =
        when (openApiSettings.overrideAllAdditionalModelTypeAnnotations) {
            true -> openApiSettings.additionalModelTypeAnnotations
            false -> {
                val lombokAnnotation = mutableListOf(
                    "@lombok.Data",
                    "@lombok.Builder",
                    "@lombok.NoArgsConstructor",
                    "@lombok.AllArgsConstructor",
                )

                lombokAnnotation.addAll(openApiSettings.additionalModelTypeAnnotations)
                lombokAnnotation
            }
        }


    schemaMappings.putAll(openApiSettings.importMapping)
    generatorName.set(openApiSettings.generator)
    this.groupId.set("${openApiSettings.groupId}.${openApiSettings.sourceFolder}")
    packageName.set("${openApiSettings.groupId}.${openApiSettings.sourceFolder}")
    inputSpec.set(openApiYamlFilePath)
    generateApiDocumentation.set(true)
    outputDir.set("${projectPath}/build/generated")
    apiPackage.set("${openApiSettings.groupId}.${openApiSettings.sourceFolder}.controller")
    invokerPackage.set("${openApiSettings.groupId}.${openApiSettings.sourceFolder}.invoker")
    apiNameSuffix.set("Controller")
    modelNameSuffix.set(openApiSettings.modelNameSuffix)
    modelPackage.set("${openApiSettings.groupId}.${openApiSettings.sourceFolder}.model")
    skipOperationExample.set(true)

    val pluginConfigOptions = mutableMapOf(
        "apiSuffix" to "Controller",
        "apiNameSuffix" to "Controller",
        "additionalModelTypeAnnotations" to lombokAdditionalModelTypeANotations.joinToString(separator = "\n"),
        "interfaceOnly" to "true",
        "skipDefaultInterface" to "true",
        "defaultInterfaces" to "false",
        "delegatePattern" to "false",
        "documentationProvider" to openApiSettings.documentationProvider.type,
        "serializationLibrary" to "jackson",
        "gradleBuildFile" to "false",
        "enumPropertyNaming" to "original",
        "exceptionHandler" to "false",
        "useSpringBoot3" to "true",
        "useSwaggerUI" to "true",
        "useTags" to "true",
        "generateApis" to "true",
        "java8" to "true"
    )

    pluginConfigOptions.putAll(openApiSettings.configOptions)
    configOptions.set(pluginConfigOptions)
}

fun Project.openApiGenerateConfig(openApiSettings: OpenApiSettings) {
    extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply {
        openApiSettings.templateDir?.let {
            templateDir.set(it)
        }
    }

    if (openApiSettings.groupId.isBlank()) {
        openApiSettings.groupId = group.toString()
    }

    extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply(
        openApiSettings = openApiSettings,
        projectPath = projectDir.path
    )

    extra["snippetsDir"] = file("build/generated-snippets")

    extensions.getByName<SourceSetContainer>("sourceSets").apply {
        getByName("main").apply {
            java {
                srcDir("$projectDir/build/generated/src/main/java")
                exclude(openApiSettings.filesExclude)
            }
        }
    }

    tasks.withType<KotlinCompile> {
        dependsOn(tasks.findByName("openApiGenerate"))
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = Dependencies.Version.jdkVersion
        }
    }
}

