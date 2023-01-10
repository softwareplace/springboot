package com.gradle.kts.build.source

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.repositories

class ProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            allprojects {
                repositories {
                    google()
                    mavenCentral()
                }
            }
        }
    }
}
