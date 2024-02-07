package com.github.softwareplace.springboot.kotlin.openapi

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension
import java.io.File

const val KOTLIN_SPRING = "kotlin-spring"

enum class DocumentationProvider(val type: String) {
    SPRING_DOC("springdoc"),
    NONE("none"),
    SOURCE("source")
}

open class OpenApiSettings(
    var generator: String = "kotlin-spring",
    var reactive: Boolean = true,
    var sourceFolder: String = ".rest",
    var modelNameSuffix: String = "Rest",
    var swaggerFileName: String = "openapi.yaml",
    var importMapping: Map<String, String> = mapOf(
        "date" to JAVA_LOCAL_DATE,
        "local-date-time" to JAVA_LOCAL_DATE_TIME,
        "time" to JAVA_LOCAL_TIME
    ),
    var filesExclude: List<String> = listOf("**/ApiUtil.kt"),
    var additionalModelTypeAnnotations: List<String> = listOf(),
    var templateDir: String? = null,
)

private val baseOpenApiSettings: MutableMap<String, OpenApiSettings> = mutableMapOf()

private const val JAVA_LOCAL_DATE_TIME = "java.time.LocalDateTime"
private const val JAVA_LOCAL_DATE = "java.time.LocalDate"
private const val JAVA_LOCAL_TIME = "java.time.LocalTime"


fun OpenApiGeneratorGenerateExtension.apply(
    openApiSettings: OpenApiSettings,
    groupId: String = "app",
    projectPath: String,
    openApiYamlFilePath: String = "${projectPath}/src/main/resources/${openApiSettings.swaggerFileName}"
) {

    schemaMappings.putAll(openApiSettings.importMapping)
    importMappings.putAll(openApiSettings.importMapping)
    typeMappings.putAll(openApiSettings.importMapping)

    generatorName.set(openApiSettings.generator)
    this.groupId.set("${groupId}${openApiSettings.sourceFolder}")
    packageName.set("${groupId}${openApiSettings.sourceFolder}")
    inputSpec.set(openApiYamlFilePath)
    generateApiDocumentation.set(true)
    outputDir.set("${projectPath}/build/generate-resources")
    this.withXml.set(false)
    apiPackage.set("${groupId}${openApiSettings.sourceFolder}.controller")
    invokerPackage.set("${groupId}${openApiSettings.sourceFolder}.invoker")
    apiNameSuffix.set("Controller")
    modelNameSuffix.set(openApiSettings.modelNameSuffix)
    modelPackage.set("${groupId}${openApiSettings.sourceFolder}.model")
    skipOperationExample.set(true)

    val pluginConfigOptions = mutableMapOf(
        "apiSuffix" to "Controller",
        "apiNameSuffix" to "Controller",
        "interfaceOnly" to "true",
        "skipDefaultInterface" to "true",
        "defaultInterfaces" to "false",
        "delegatePattern" to "false",
        "additionalModelTypeAnnotations" to openApiSettings.additionalModelTypeAnnotations.joinToString(separator = "\n"),
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

    if (openApiSettings.reactive) {
        pluginConfigOptions.putAll(mapOf("reactive" to "${openApiSettings.reactive}"))
    }

    configOptions.set(pluginConfigOptions)
}

fun Project.getOpenApiSettings(): OpenApiSettings =
    baseOpenApiSettings.computeIfAbsent(projectDir.name) { OpenApiSettings() }

fun Project.openApiSettings(config: Action<OpenApiSettings>) = config.invoke(getOpenApiSettings())

fun Project.openApiGenerateConfig(templateSourceDir: String?) {
    afterEvaluate {
        extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply {
            val kotlinTemplateDir = when (templateSourceDir.isNullOrBlank()) {
                true -> templateSourceDir
                false -> getOpenApiSettings().templateDir
            }

            kotlinTemplateDir?.let {
                templateDir.set(it)
            }
        }

        extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply(
            openApiSettings = getOpenApiSettings(),
            groupId = "$group",
            projectPath = projectDir.path,
        )
    }
}

fun Project.applyKotlinSourceSets() {
    val openApiSettings = getOpenApiSettings()
    extra["snippetsDir"] = file("build/generated-snippets")
    kotlinExtension.sourceSets["main"].kotlin {
        srcDir("$projectDir/build/generate-resources/src/main/kotlin")
        exclude(openApiSettings.filesExclude)
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
