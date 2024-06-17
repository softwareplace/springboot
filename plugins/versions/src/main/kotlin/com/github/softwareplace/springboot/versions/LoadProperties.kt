package com.github.softwareplace.springboot.versions

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.*

class LoadProperties : Plugin<Project> {

    override fun apply(target: Project) {

        val inputStream = javaClass.classLoader.getResourceAsStream("gradle.properties")
        target.loadGradleProperties(inputStream)

        val rootGradleProperties = File("${target.rootDir}/gradle.properties")
        val projectGradleProperties = File("${target.projectDir}/gradle.properties")

        loadGradleProperties(rootGradleProperties, target)
        loadGradleProperties(projectGradleProperties, target)
    }

    private fun loadGradleProperties(
        projectGradleProperties: File,
        target: Project
    ) {
        if (projectGradleProperties.exists()) {
            println("Loading ${projectGradleProperties.path}")
            target.loadGradleProperties(FileInputStream(projectGradleProperties))
        }
    }
}

fun Project.loadGradleProperties(inputStream: InputStream?) {
    inputStream?.let {
        val properties = Properties()
        properties.load(inputStream)

        properties.forEach { (key, value) ->
            extensions.extraProperties[key.toString()] = value
            System.setProperty(key.toString(), value.toString())
        }
    }
}
