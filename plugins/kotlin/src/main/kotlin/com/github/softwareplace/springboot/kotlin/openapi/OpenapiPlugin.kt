package com.github.softwareplace.springboot.kotlin.openapi

import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.buildconfiguration.testImplementation
import com.github.softwareplace.springboot.kotlin.BuildSourcePlugin
import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies


class OpenapiPlugin : BuildSourcePlugin() {


    override fun customApply(target: Project) {
        super.customApply(target)
        val templateSourceDir = javaClass.classLoader?.getResource(KOTLIN_SPRING)

        with(target) {
            apply(plugin = "org.openapi.generator")
            openApiGenerateConfig(templateSourceDir?.path)
            applyKotlinSourceSets()
            applyTasks()
            allprojects {
                dependencies {
                    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Dependencies.Version.springdocStarterWebmvc}")
                    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:${Dependencies.Version.springRstDocsMockMVCVersion}")
                }
            }
        }
    }
}
