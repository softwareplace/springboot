package com.github.softwareplace.springboot.utils

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.run.BootRun


fun Project.submoduleConfig() {
    tasks.named<Jar>("bootJar").configure {
        enabled = false
    }

    tasks.named<BootRun>("bootRun").configure {
        enabled = false
    }

    extensions.getByName<SourceSetContainer>("sourceSets").apply {
        getByName("main").apply {
            java {
                exclude(
                    "**/SpringDocConfiguration.kt",
                )
            }
        }
    }

    kotlinExtension.sourceSets["main"].kotlin {
        exclude(
            "**/SpringDocConfiguration.kt",
        )
    }
}

fun Project.bootBuildImageConfig(action: Action<BootBuildImage>) {
    project.extensions.getByType(BootBuildImage::class.java).apply {
        action.execute(this)
    }
}

fun Project.nativeSubmoduleConfig() {
    tasks.apply {
        named("bootBuildImage") {
            enabled = false
        }

        named("nativeCompile") {
            enabled = false
        }

        named("nativeRun") {
            enabled = false
        }

        named("nativeCopy") {
            enabled = false
        }

        named("nativeTestCompile") {
            enabled = false
        }
    }
}
