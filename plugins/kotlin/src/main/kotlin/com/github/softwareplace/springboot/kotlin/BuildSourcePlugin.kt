package com.github.softwareplace.springboot.kotlin

import com.github.softwareplace.springboot.buildconfiguration.BasePluginConfiguration
import com.github.softwareplace.springboot.buildconfiguration.ORG_SPRINGFRAMEWORK_BOOT
import com.github.softwareplace.springboot.buildconfiguration.kaptAnnotationProcessor
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

open class BuildSourcePlugin : BasePluginConfiguration() {

    override fun customApply(target: Project) {
        with(target) {
            allprojects {
                apply(plugin = "org.jetbrains.kotlin.kapt")
                dependencies {
                    kaptAnnotationProcessor("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-configuration-processor")
                }
            }
        }
    }
}
