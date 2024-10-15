package com.github.softwareplace.springboot.security.extension

import java.util.*


fun String.addAtStartAsCamelCase(value: String): String {
    val firstLetter = substring(0, 1).uppercase(Locale.getDefault())
    return "$value$firstLetter${substring(1, length)}"
}

fun String.asPathRegex(): Regex {
    val pattern = replaceFirst("/**", ".*")
        .replace("**", ".*")
    return Regex(pattern)
}

fun String.convertSnakeCaseToCamelCase(): String {
    val parts = split("_")
    val camelCase = parts.mapIndexed { index, part ->
        if (index > 0) part.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } else part
    }.joinToString("")
    return camelCase
}
