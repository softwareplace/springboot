package com.github.softwareplace.springboot.java

import com.github.softwareplace.springboot.buildconfiguration.BasePluginConfiguration
import com.github.softwareplace.springboot.buildconfiguration.ORG_SPRINGFRAMEWORK_BOOT
import com.github.softwareplace.springboot.buildconfiguration.annotationProcessor
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

open class BuildSourcePlugin : BasePluginConfiguration() {
    override fun customApply(target: Project) {
        with(target) {
            allprojects {
                dependencies {
                    annotationProcessor("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-configuration-processor")
                }
            }
        }
    }
}
