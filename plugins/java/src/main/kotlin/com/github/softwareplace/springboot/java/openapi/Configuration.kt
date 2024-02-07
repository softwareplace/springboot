package com.github.softwareplace.springboot.java.openapi

import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

enum class DocumentationProvider(val type: String) {
    SPRING_DOC("springdoc"),
    NONE("none"),
    SOURCE("source")
}

open class OpenApiSettings(
    var generator: String = "spring",
    var sourceFolder: String = "rest",
    var modelNameSuffix: String = "Rest",
    var swaggerFileName: String = "openapi.yaml",
    var importMapping: MutableMap<String, String> = mutableMapOf(),
    var filesExclude: List<String> = listOf("**/ApiUtil.java"),
    var templateDir: String? = null,
    var additionalModelTypeAnnotations: List<String> = listOf("@lombok.Data", "@lombok.Builder")
)

private const val JAVA_LOCAL_DATE_TIME = "java.time.LocalDateTime"
private const val JAVA_LOCAL_DATE = "java.time.LocalDate"
private const val JAVA_LOCAL_TIME = "java.time.LocalTime"

private val baseOpenApiSettings: MutableMap<String, OpenApiSettings> = mutableMapOf()

fun OpenApiGeneratorGenerateExtension.apply(
    openApiSettings: OpenApiSettings,
    groupId: String = "app",
    projectPath: String,
    openApiYamlFilePath: String = "${projectPath}/src/main/resources/${openApiSettings.swaggerFileName}"
) {
    schemaMappings.putAll(
        mapOf(
            "date-time" to JAVA_LOCAL_DATE_TIME
        )
    )
    importMappings.set(
        mapOf(
            "java.time.OffsetDateTime" to "java.time.LocalDateTime"
        )
    )
    typeMappings.set(
        mapOf(
            "date" to JAVA_LOCAL_DATE,
            "local-date-time" to JAVA_LOCAL_DATE_TIME,
            "time" to JAVA_LOCAL_TIME
        )
    )

    schemaMappings.putAll(openApiSettings.importMapping)
    generatorName.set(openApiSettings.generator)
    this.groupId.set("${groupId}.${openApiSettings.sourceFolder}")
    packageName.set("${groupId}.${openApiSettings.sourceFolder}")
    inputSpec.set(openApiYamlFilePath)
    generateApiDocumentation.set(true)
    outputDir.set("${projectPath}/build/generated")
    apiPackage.set("${groupId}.${openApiSettings.sourceFolder}.controller")
    invokerPackage.set("${groupId}.${openApiSettings.sourceFolder}.invoker")
    apiNameSuffix.set("Controller")
    modelNameSuffix.set(openApiSettings.modelNameSuffix)
    modelPackage.set("${groupId}.${openApiSettings.sourceFolder}.model")
    skipOperationExample.set(true)
    configOptions.set(
        mapOf(
            "apiSuffix" to "Controller",
            "apiNameSuffix" to "Controller",
            "additionalModelTypeAnnotations" to openApiSettings.additionalModelTypeAnnotations.joinToString(separator = "\n"),
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
            "useSwaggerUI" to "true",
            "useTags" to "true",
            "generateApis" to "true",
            "java8" to "true"
        )
    )
}

fun Project.getOpenApiSettings(): OpenApiSettings =
    baseOpenApiSettings.computeIfAbsent(projectDir.name) { OpenApiSettings() }

fun Project.openApiSettings(config: Action<OpenApiSettings>) = config.invoke(getOpenApiSettings())

fun Project.openApiGenerateConfig() {
    val openApiSettings = getOpenApiSettings()
    afterEvaluate {
        extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply {
            val javaTemplateDir = getOpenApiSettings().templateDir

            javaTemplateDir?.let {
                templateDir.set(it)
            }
        }

        extensions.getByName<OpenApiGeneratorGenerateExtension>("openApiGenerate").apply(
            openApiSettings = openApiSettings,
            groupId = "$group",
            projectPath = projectDir.path,
        )
    }
}

fun Project.applyJavaSourceSets() {
    val openApiSettings = getOpenApiSettings()
    extra["snippetsDir"] = file("build/generated-snippets")
    extensions.getByName<SourceSetContainer>("sourceSets").apply {
        getByName("main").apply {
            java {
                srcDir("$projectDir/build/generated/src/main/java")
                exclude(openApiSettings.filesExclude)
            }
        }
    }
}

fun Project.applyTasks() {
    tasks.withType<KotlinCompile> {
        dependsOn(tasks.findByName("openApiGenerate"))
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = Dependencies.Version.jdkVersion
        }
    }
}
