package com.github.softwareplace.springboot.versions

import org.gradle.api.Project
import java.io.BufferedReader
import java.io.InputStreamReader

fun Project.getTag(): String {
    val loadedVersion = loadVersion()
    println("Compiling ${project.name}:${loadedVersion}")
    return loadedVersion
}

fun Project.loadVersion(): String {
    try {
        val versionRequest: String? = findProperty("version")?.toString()
        if (!versionRequest.isNullOrBlank() && !versionRequest.equals("unspecified", ignoreCase = true)) {
            return versionRequest
        }

        val process = ProcessBuilder("git", "describe", "--tags", "--abbrev=0")
            .redirectErrorStream(true)
            .start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val tag = reader.readLine()

        if (tag.isNotBlank()) {
            return tag
        }
    } catch (err: Throwable) {
        println("Failed to get ${project.name} version")
    }
    return System.getProperty("pluginsVersion")
}
