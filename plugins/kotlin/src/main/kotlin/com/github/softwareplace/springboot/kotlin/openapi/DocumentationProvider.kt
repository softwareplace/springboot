package com.github.softwareplace.springboot.kotlin.openapi

enum class DocumentationProvider(val type: String) {
    SPRING_DOC("springdoc"),
    NONE("none"),
    SOURCE("source")
}