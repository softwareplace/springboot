package com.github.softwareplace.springboot.java.openapi

import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.buildconfiguration.testImplementation
import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke

fun Project.javaOpenapiSettings(config: Action<OpenapiSettings>? = null) {
    apply(plugin = "org.openapi.generator")

    val openApiSettings = OpenapiSettings()
    config?.invoke(openApiSettings)

    applyTasks()
    openapiGenerateConfig(openApiSettings)
    applyJavaSourceSets(openApiSettings)
    dependencies {
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Dependencies.Version.springdocStarterWebmvc}")
        implementation("org.openapitools:jackson-databind-nullable:${Dependencies.Version.openApiToolsJacksonDatabindNullableVersion}")
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:${Dependencies.Version.springRstDocsMockMVCVersion}")
    }
}
