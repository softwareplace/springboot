package com.github.softwareplace.springboot.kotlin.openapi

data class OpenApiSettings(
    /**
     * If not specified, the project groupId will be used.
     * */
    var groupId: String = "",
    var generator: String = "kotlin-spring",
    /**
     * When true, controllers interface methods will be generated with suspend keyword
     *  */
    var reactive: Boolean = true,
    var sourceFolder: String = ".rest",
    var modelNameSuffix: String = "Rest",
    var swaggerFileName: String = "openapi.yaml",
    /**
     * If true, [java.time.LocalDateTime], [java.time.LocalDate] and [java.time.LocalTime] will be removed.
     * */
    var overrideImportMapping: Boolean = false,
    var importMapping: Map<String, String> = emptyMap(),
    var filesExclude: List<String> = listOf("**/ApiUtil.kt"),
    var documentationProvider: DocumentationProvider = DocumentationProvider.SPRING_DOC,
    var additionalModelTypeAnnotations: List<String> = listOf(),
    var templateDir: String? = null,

    /**
     * Add or override a config option
     * */
    var configOptions: Map<String, String> = emptyMap()
)