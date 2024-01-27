package com.github.softwareplace.springboot.plugin.java.openapi

import com.github.softwareplace.springboot.plugin.buildconfiguration.Dependencies
import com.github.softwareplace.springboot.plugin.buildconfiguration.implementation
import com.github.softwareplace.springboot.plugin.buildconfiguration.testImplementation
import com.github.softwareplace.springboot.plugin.java.BuildSourcePlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class OpenApiPlugin : BuildSourcePlugin() {

    override fun customApply(target: Project) {
        super.customApply(target)
        with(target) {
            apply(plugin = "org.openapi.generator")
            openApiGenerateConfig()
            applyJavaSourceSets()
            applyTasks()
            allprojects {
                dependencies {
                    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Dependencies.Version.springdocStarterWebmvc}")
                    implementation("org.openapitools:jackson-databind-nullable:${Dependencies.Version.openApiToolsJacksonDatabindNullableVersion}")
                    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:${Dependencies.Version.springRstDocsMockMVCVersion}")
                }
            }
        }
    }
}
