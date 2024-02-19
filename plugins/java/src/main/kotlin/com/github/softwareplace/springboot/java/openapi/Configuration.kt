package com.github.softwareplace.springboot.java.openapi

import com.github.softwareplace.springboot.utils.toCamelCase
import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension
import java.io.File

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

            openApiSettings.addCustomFormats.forEach { (key, source) ->
                customImporting[key] = "${source.second}.${source.first}".toCamelCase()
            }

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
    this.groupId.set("${openApiSettings.groupId}${openApiSettings.sourceFolder}")
    packageName.set("${openApiSettings.groupId}${openApiSettings.sourceFolder}")
    inputSpec.set(openApiYamlFilePath)
    generateApiDocumentation.set(true)
    outputDir.set("${projectPath}/build/generated")
    apiPackage.set("${openApiSettings.groupId}${openApiSettings.sourceFolder}${openApiSettings.controllerPackage}")
    invokerPackage.set("${openApiSettings.groupId}${openApiSettings.sourceFolder}${openApiSettings.invokerPackage}")
    apiNameSuffix.set(openApiSettings.apiNameSuffix)
    modelNameSuffix.set(openApiSettings.modelNameSuffix)
    modelPackage.set("${openApiSettings.groupId}${openApiSettings.sourceFolder}${openApiSettings.modelPackage}")
    skipOperationExample.set(true)

    val pluginConfigOptions = mutableMapOf(
        "apiSuffix" to openApiSettings.apiNameSuffix,
        "apiNameSuffix" to openApiSettings.apiNameSuffix,
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

    tasks.register("openapiResourceValidation") {
        revalidateFile(File("$projectDir/build/generated/src/main/java"), openApiSettings)
    }

    tasks.getByName("openApiGenerate") {
        doLast { tasks.findByName("openapiResourceValidation") }
    }

    tasks.withType<KotlinCompile> {
        dependsOn(tasks.findByName("openApiGenerate"))
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = Dependencies.Version.jdkVersion
        }
    }
}

private fun revalidateFile(directory: File, openApiSettings: OpenApiSettings) {
    directory.walkTopDown().forEach { file ->
        if (file.isFile) {
            var didChange = false
            var fileContent = file.readText()

            openApiSettings.addCustomFormats.forEach { (_, value) ->
                val simpleClassNameKey =
                    "${value.second}.${value.first}".toCamelCase()
                val simpleClassNameRestKey =
                    "${value.second}.${value.first}${openApiSettings.modelNameSuffix}".toCamelCase()

                val className = "${value.second}.${value.first}"

                if (fileContent.contains(simpleClassNameKey) || fileContent.contains(simpleClassNameRestKey)) {
                    fileContent = fileContent.replace(Regex("import.*$simpleClassNameRestKey;"), "")
                    fileContent = fileContent.replace(Regex("import.*$simpleClassNameKey;"), "")
                    fileContent = fileContent.replace(simpleClassNameKey, className)
                    didChange = true
                }
            }

            if (didChange) {
                file.writeText(fileContent)
            }

        }
    }
}

