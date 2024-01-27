package com.github.softwareplace.springboot.plugin.buildconfiguration

import org.gradle.api.Project
import java.io.BufferedReader
import java.io.InputStreamReader

fun Project.getTag(): String {
    try {
        val versionRequest: String? = findProperty("version")?.toString()
        if (!versionRequest.isNullOrBlank() && !versionRequest.equals("unspecified", ignoreCase = true)) {
            println("Current request tag $versionRequest")
            return versionRequest
        }

        val process = ProcessBuilder("git", "describe", "--tags", "--abbrev=0")
            .redirectErrorStream(true)
            .start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val tag = reader.readLine()

        if (tag.isNotBlank()) {
            println("Current app tag $tag")
            return tag
        }
    } catch (err: Throwable) {
        println("Failed to get current tag")
    }

    val tag = System.getProperty("pluginsVersion")
    println("Current default tag $tag")
    return tag
}