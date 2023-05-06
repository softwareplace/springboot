package com.gradle.kts.build.source

import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.named
import org.springframework.boot.gradle.tasks.run.BootRun

class BuildSubmoduleSourcePlugin : BuildSourcePlugin() {
    override fun Project.applyCustomTasks() {
        tasks.named<Jar>("bootJar").configure {
            enabled = false
        }

        tasks.named<BootRun>("bootRun").configure {
            enabled = false
        }
    }
}
