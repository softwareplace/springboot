package com.github.softwareplace.springboot.kotlin.openapi

import com.github.softwareplace.springboot.utils.toCamelCase
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
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

    generatorName.set(openApiSettings.generator)
    this.groupId.set("${openApiSettings.groupId}${openApiSettings.sourceFolder}")
    packageName.set("${openApiSettings.groupId}${openApiSettings.sourceFolder}")
    inputSpec.set(openApiYamlFilePath)
    generateApiDocumentation.set(true)
    outputDir.set("${projectPath}/build/generate-resources")
    this.withXml.set(false)
    apiPackage.set("${openApiSettings.groupId}${openApiSettings.sourceFolder}${openApiSettings.controllerPackage}")
    invokerPackage.set("${openApiSettings.groupId}${openApiSettings.sourceFolder}${openApiSettings.invokerPackage}")
    apiNameSuffix.set(openApiSettings.apiNameSuffix)
    modelNameSuffix.set(openApiSettings.modelNameSuffix)
    modelPackage.set("${openApiSettings.groupId}${openApiSettings.sourceFolder}${openApiSettings.modelPackage}")
    skipOperationExample.set(true)

    val pluginConfigOptions: MutableMap<String, String> = mutableMapOf(
        "additionalModelTypeAnnotations" to openApiSettings.additionalModelTypeAnnotations.joinToString(separator = "\n"),
        "documentationProvider" to openApiSettings.documentationProvider.type,
        "apiSuffix" to openApiSettings.apiNameSuffix,
        "apiNameSuffix" to openApiSettings.apiNameSuffix,
        "interfaceOnly" to "true",
        "skipDefaultInterface" to "true",
        "defaultInterfaces" to "false",
        "delegatePattern" to "false",
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

    if (openApiSettings.reactive) {
        pluginConfigOptions.putAll(mapOf("reactive" to "${openApiSettings.reactive}"))
    }

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
        projectPath = projectDir.path,
    )

    extra["snippetsDir"] = file("build/generated-snippets")
    kotlinExtension.sourceSets["main"].kotlin {
        srcDir("$projectDir/build/generate-resources/src/main/kotlin")
        exclude(openApiSettings.filesExclude)
    }

    tasks {
        register("openapiResourceValidation") {
            revalidateFile(File("$projectDir/build/generate-resources/src/main/kotlin"), openApiSettings)
        }

        getByName("openApiGenerate") {
            doLast { tasks.findByName("openapiResourceValidation") }
        }

        withType<KotlinCompile> {
            dependsOn(tasks.findByName("openApiGenerate"))
        }
    }
}

private fun revalidateFile(directory: File, openApiSettings: OpenApiSettings) {
    directory.walkTopDown().forEach { file ->
        if (file.isFile) {
            var didChange = false
            var fileContent = file.readText()

            if (fileContent.contains("@RequestMapping") && fileContent.contains("@Tag")) {
                fileContent = replaceTagInContent(fileContent)
                didChange = true
            }

            openApiSettings.addCustomFormats.forEach { (_, value) ->
                val simpleClassNameKey =
                    "${value.second}.${value.first}${openApiSettings.modelNameSuffix}".toCamelCase()
                val className = "${value.second}.${value.first}"

                if (fileContent.contains(simpleClassNameKey)) {
                    fileContent = fileContent.replace(Regex("import.*$simpleClassNameKey"), "")
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

private fun replaceTagInContent(content: String): String {
    val tagPattern = """\@Tag\([^)]*\)""".toRegex()
    val matchingTag = tagPattern.find(content)?.value

    if (!matchingTag.isNullOrBlank() && matchingTag.isNotBlank()) {
        val namePattern = """name\s+=\s+\".*\"""".toRegex()
        val nameValue = namePattern.find(matchingTag)?.value?.replace("""name\s+=\s+""".toRegex(), "")
        if (!nameValue.isNullOrBlank() && nameValue.isNotBlank()) {
            return content.replaceFirst(nameValue, camelCaseToSeparated(nameValue))
        }
    }

    return content
}

fun camelCaseToSeparated(input: String): String {
    val separated = input.replace(Regex("(\\p{Upper})")) { " ${it.value}" }
    return separated.trim()
        .replace("\" ", "\"")
}
