package com.gradle.kts.build.java.openapi

import com.gradle.kts.build.configuration.Dependencies
import com.gradle.kts.build.configuration.implementation
import com.gradle.kts.build.configuration.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class OpenApiPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.openapi.generator")
            applyJavaSourceSets()
            openApiGenerateConfig()
            applyTasks()
            allprojects {
                dependencies {
                    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Dependencies.Version.springdocStarterWebmvc}")
                    implementation("org.openapitools:jackson-databind-nullable:${Dependencies.Version.openapiToolsJacksonDatabindNullable}")
                    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:${Dependencies.Version.springRstDocsMockMVC}")
                }
            }
        }
    }
}
