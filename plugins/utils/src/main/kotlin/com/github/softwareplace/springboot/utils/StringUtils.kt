package com.github.softwareplace.springboot.utils

import java.util.*

fun String.toCamelCase(): String {
    val segments = split('.')
    val camelCaseSegments = segments.map { it.parseFirstLetterToUpperCase() }
    return camelCaseSegments.joinToString("")
}

fun String.parseFirstLetterToUpperCase() =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
