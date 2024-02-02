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

        val projectGradleProperties = File("${target.projectDir}/gradle.properties")

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
            println("PROPERTY_LOADED: ${key}:${System.getProperty(key.toString())}")
        }
    }
}
