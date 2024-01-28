package com.github.softwareplace.springboot.kotlin.openapi

import com.github.softwareplace.springboot.buildconfiguration.Dependencies
import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.buildconfiguration.testImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class OpenapiPlugin : BuildSourcePlugin() {


    override fun customApply(target: Project) {
        super.customApply(target)

        with(target) {
            apply(plugin = "org.openapi.generator")
            openApiGenerateConfig()
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
