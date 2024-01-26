package com.github.softwareplace.plugin.kotlinopenapi

import com.github.softwareplace.plugin.buildconfiguration.Dependencies
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

enum class DocumentationProvider(val type: String) {
    SPRING_DOC("springdoc"),
    NONE("none"),
    SOURCE("source")
}

open class OpenApiSettings(
    var generator: String = "kotlin-spring",
    var reactive: Boolean = false,
    var sourceFolder: String = ".rest",
    var modelNameSuffix: String = "Rest",
    var swaggerFileName: String = "openapi.yaml",
    var importMapping: Map<String, String> = mapOf(
        "date" to JAVA_LOCAL_DATE,
        "local-date-time" to JAVA_LOCAL_DATE_TIME,
        "time" to JAVA_LOCAL_TIME
    ),
    var filesExclude: List<String> = listOf("**/ApiUtil.kt"),
    var additionalModelTypeAnnotations: List<String> = listOf()
)

fun Project.getSettings(): OpenApiSettings {
    return try {
        extensions.create("openApiSettings", OpenApiSettings::class.java)
    } catch (ex: Exception) {
        logger.info("Creating a default OpenApiSettings")
        OpenApiSettings()
    }
}

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

fun Project.openApiSettings(
    config: OpenApiSettings
) {
    configure<OpenApiSettings> {
        this.generator = config.generator
        this.reactive = config.reactive
        this.sourceFolder = config.sourceFolder
        this.modelNameSuffix = config.modelNameSuffix
        this.swaggerFileName = config.swaggerFileName
        this.filesExclude = config.filesExclude
        this.importMapping = config.importMapping
        this.additionalModelTypeAnnotations = config.additionalModelTypeAnnotations
    }
}

fun Project.openApiGenerateConfig() {
    val openApiSettings = getSettings()
    beforeEvaluate {
        extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply {
            System.getProperty("kotlin-spring")?.let {
                templateDir.set(it)
            }
        }
    }
    afterEvaluate {
        extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply(
            openApiSettings = openApiSettings,
            groupId = "$group",
            projectPath = projectDir.path,
        )
    }
}


fun Project.applyKotlinSourceSets() {
    val openApiSettings = getSettings()
    extra["snippetsDir"] = file("build/generated-snippets")
    kotlinExtension.sourceSets["main"].kotlin {
        srcDir("$projectDir/build/generate-resources/src/main/kotlin")
        exclude(openApiSettings.filesExclude)
    }
}

fun Project.applyTasks() {
    tasks.withType<KotlinCompile> {
        dependsOn(tasks.findByName("openApiGenerate"))
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = Dependencies.Version.jdk
        }
    }
}
