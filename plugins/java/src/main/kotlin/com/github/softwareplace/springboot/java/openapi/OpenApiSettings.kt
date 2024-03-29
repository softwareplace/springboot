package com.github.softwareplace.springboot.java.openapi

data class OpenApiSettings(
    /**
     * If not specified, the project groupId will be used.
     * */
    var groupId: String = "",
    var generator: String = "spring",
    var sourceFolder: String = ".rest",
    var modelPackage: String = ".model",
    var controllerPackage: String = ".controller",
    var invokerPackage: String = ".invoker",
    var apiNameSuffix: String = "Controller",
    var modelNameSuffix: String = "Rest",
    var swaggerFileName: String = "openapi.yaml",

    /**
     * If true, [java.time.LocalDateTime], [java.time.LocalDate] and [java.time.LocalTime] will be removed.
     * */
    var overrideImportMapping: Boolean = false,
    var importMapping: Map<String, String> = emptyMap(),
    var filesExclude: List<String> = listOf("**/ApiUtil.java"),
    var documentationProvider: DocumentationProvider = DocumentationProvider.SPRING_DOC,
    var templateDir: String? = null,

    /**
     * When needs to add a custom object importing for format type.
     * Example: mapOf("profile" to ("Profile" to "com.example.shared.model"))
     */
    var addCustomFormats: Map<String, Pair<String, String>> = emptyMap(),

    var overrideAllAdditionalModelTypeAnnotations: Boolean = false,
    var additionalModelTypeAnnotations: List<String> = emptyList(),
    /**
     * Add or override a config option
     * */
    var configOptions: Map<String, String> = emptyMap()
) {
    val lombokData = "@lombok.Data"
    val lombokBuilder = "@lombok.Builder"
    val lombokNoArgsConstructor = "@lombok.NoArgsConstructor"
    val lombokAllArgsConstructor = "@lombok.AllArgsConstructor"
    val lombokRequiredArgsConstructor = "@lombok.RequiredArgsConstructor"

    val lombokDataBuilder = listOf(lombokData, lombokBuilder)
    val lombokDataBuilderRequiredArgsConstructor = listOf(
        lombokData,
        lombokBuilder,
        lombokRequiredArgsConstructor
    )
}
