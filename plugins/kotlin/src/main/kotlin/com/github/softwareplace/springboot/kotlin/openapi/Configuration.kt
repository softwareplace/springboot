package com.github.softwareplace.springboot.kotlin.openapi

import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension
import java.io.File

enum class DocumentationProvider(val type: String) {
    SPRING_DOC("springdoc"),
    NONE("none"),
    SOURCE("source")
}

open class OpenapiSettings(
    var generator: String = "kotlin-spring",
    /** Disable or enable kotlin function with suspend key work */
    var reactive: Boolean = true,
    var sourceFolder: String = ".rest",
    var modelNameSuffix: String = "Rest",
    var swaggerFileName: String = "openapi.yaml",
    var overrideImportMapping: Boolean = false,
    var importMapping: Map<String, String> = emptyMap(),
    var filesExclude: List<String> = listOf("**/ApiUtil.kt"),
    var additionalModelTypeAnnotations: List<String> = listOf(),
    var templateDir: String? = null,
)

private const val JAVA_LOCAL_DATE_TIME = "java.time.LocalDateTime"
private const val JAVA_LOCAL_DATE = "java.time.LocalDate"
private const val JAVA_LOCAL_TIME = "java.time.LocalTime"


fun OpenApiGeneratorGenerateExtension.apply(
    openapiSettings: OpenapiSettings,
    groupId: String = "app",
    projectPath: String,
    openApiYamlFilePath: String = "${projectPath}/src/main/resources/${openapiSettings.swaggerFileName}"
) {

    val customImportingMapping: Map<String, String> = when (openapiSettings.overrideImportMapping) {
        true -> openapiSettings.importMapping
        else -> {
            val customImporting = mutableMapOf(
                "date" to JAVA_LOCAL_DATE,
                "local-date-time" to JAVA_LOCAL_DATE_TIME,
                "time" to JAVA_LOCAL_TIME
            )
            customImporting.putAll(openapiSettings.importMapping)
            customImporting
        }
    }

    schemaMappings.putAll(customImportingMapping)
    importMappings.putAll(customImportingMapping)
    typeMappings.putAll(customImportingMapping)

    generatorName.set(openapiSettings.generator)
    this.groupId.set("${groupId}${openapiSettings.sourceFolder}")
    packageName.set("${groupId}${openapiSettings.sourceFolder}")
    inputSpec.set(openApiYamlFilePath)
    generateApiDocumentation.set(true)
    outputDir.set("${projectPath}/build/generate-resources")
    this.withXml.set(false)
    apiPackage.set("${groupId}${openapiSettings.sourceFolder}.controller")
    invokerPackage.set("${groupId}${openapiSettings.sourceFolder}.invoker")
    apiNameSuffix.set("Controller")
    modelNameSuffix.set(openapiSettings.modelNameSuffix)
    modelPackage.set("${groupId}${openapiSettings.sourceFolder}.model")
    skipOperationExample.set(true)

    val pluginConfigOptions = mutableMapOf(
        "apiSuffix" to "Controller",
        "apiNameSuffix" to "Controller",
        "interfaceOnly" to "true",
        "skipDefaultInterface" to "true",
        "defaultInterfaces" to "false",
        "delegatePattern" to "false",
        "additionalModelTypeAnnotations" to openapiSettings.additionalModelTypeAnnotations.joinToString(separator = "\n"),
        "documentationProvider" to DocumentationProvider.SPRING_DOC.type,
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

    if (openapiSettings.reactive) {
        pluginConfigOptions.putAll(mapOf("reactive" to "${openapiSettings.reactive}"))
    }

    configOptions.set(pluginConfigOptions)
}

fun Project.openApiGenerateConfig(openapiSettings: OpenapiSettings) {
    afterEvaluate {
        extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply {
            openapiSettings.templateDir?.let {
                templateDir.set(it)
            }
        }

        extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply(
            openapiSettings = openapiSettings,
            groupId = group.toString(),
            projectPath = projectDir.path,
        )
    }
}

fun Project.applyKotlinSourceSets(openapiSettings: OpenapiSettings) {
    extra["snippetsDir"] = file("build/generated-snippets")
    kotlinExtension.sourceSets["main"].kotlin {
        srcDir("$projectDir/build/generate-resources/src/main/kotlin")
        exclude(openapiSettings.filesExclude)
    }
}

fun Project.applyTasks() {
    tasks {
        register("openapiResourceValidation") {
            replaceTagInFiles(File("$projectDir/build/generate-resources/src/main/kotlin"))
        }

        getByName("openApiGenerate") {
            doLast { tasks.findByName("openapiResourceValidation") }
        }

        withType<KotlinCompile> {
            dependsOn(tasks.findByName("openApiGenerate"))
        }
    }
}

fun replaceTagInFiles(directory: File) {
    directory.walkTopDown().forEach { file ->
        if (file.isFile) {
            val fileContent = file.readText()
            if (fileContent.contains("@RequestMapping") && fileContent.contains("@Tag")) {
                val updatedContent = replaceTagInContent(fileContent)
                file.writeText(updatedContent)
            }
        }
    }
}

fun replaceTagInContent(content: String): String {
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
