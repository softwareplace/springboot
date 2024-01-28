package com.github.softwareplace.springboot.java

import com.github.softwareplace.springboot.buildconfiguration.BasePluginConfiguration
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.springframework.boot.gradle.tasks.run.BootRun

class SubmoduleSourcePlugin : BasePluginConfiguration() {

    override fun customApply(target: Project) {
        super.customApply(target)

        with(target) {
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
                            "**/SpringDocConfiguration.java",
                        )
                    }
                }
            }

            kotlinExtension.sourceSets["main"].kotlin {
                exclude(
                    "**/SpringDocConfiguration.java",
                )
            }
        }
    }
}
