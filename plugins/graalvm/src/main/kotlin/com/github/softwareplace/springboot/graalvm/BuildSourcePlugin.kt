package com.github.softwareplace.springboot.graalvm

import com.github.softwareplace.springboot.buildconfiguration.BasePluginConfiguration
import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.versions.graalvmBuildToolsNativeVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

open class BuildSourcePlugin : BasePluginConfiguration() {
    override fun customApply(target: Project) {
        with(target) {
            allprojects {
                apply(plugin = "org.graalvm.buildtools.native")
                apply(plugin = "org.springframework.boot")

                dependencies {
                    implementation("org.graalvm.buildtools:native-gradle-plugin:${graalvmBuildToolsNativeVersion}")
                    implementation("org.springframework.boot:spring-boot-gradle-plugin")
                }
            }
        }
    }
}




