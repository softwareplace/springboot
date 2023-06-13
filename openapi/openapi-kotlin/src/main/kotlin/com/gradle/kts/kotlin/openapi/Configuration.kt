package com.gradle.kts.kotlin.openapi

import com.gradle.kts.build.configuration.Dependencies
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
    var sourceFolder: String = "rest",
    var modelNameSuffix: String = "Rest",
    var swaggerFileName: String = "openapi.yaml",
    val filesExclude: MutableList<String> = mutableListOf("**/ApiUtil.kt", "**/ApiUtil.java")
)

fun Project.getSettings(): OpenApiSettings {
    return try {
        extensions.create("openApiSettings", OpenApiSettings::class.java)
    } catch (ex: Exception) {
        logger.info("openApiSettings not found")
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
    schemaMappings.putAll(
        mapOf(
            "date-time" to JAVA_LOCAL_DATE_TIME
        )
    )
    importMappings.set(
        mapOf(
            "java.time.OffsetDateTime" to JAVA_LOCAL_DATE_TIME
        )
    )
    typeMappings.set(
        mapOf(
            "date" to JAVA_LOCAL_DATE,
            "local-date-time" to JAVA_LOCAL_DATE_TIME,
            "time" to JAVA_LOCAL_TIME
        )
    )

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

    val pluginConfigOptions = mutableMapOf(
        "apiSuffix" to "Controller",
        "apiNameSuffix" to "Controller",
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

    if (openApiSettings.reactive) {
        pluginConfigOptions.putAll(mapOf("reactive" to "${openApiSettings.reactive}"))
    }

    configOptions.set(pluginConfigOptions)
}

fun Project.openapiSettings(
    generator: String = "kotlin-spring",
    reactive: Boolean = false,
    sourceFolder: String = "rest",
    modelNameSuffix: String = "Rest",
    swaggerFileName: String = "openapi.yaml",
    filesExclude: List<String> = listOf()
) {
    configure<OpenApiSettings> {
        this.generator = generator
        this.reactive = reactive
        this.sourceFolder = sourceFolder
        this.modelNameSuffix = modelNameSuffix
        this.swaggerFileName = swaggerFileName
        this.filesExclude.addAll(filesExclude)
    }
}

fun Project.openApiGenerateConfig() {
    val openApiSettings = getSettings()
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
        srcDir("$projectDir/build/generated/src/main/kotlin")
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
