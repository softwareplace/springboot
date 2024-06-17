package com.github.softwareplace.springboot.kotlin.openapi

import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.buildconfiguration.testImplementation
import com.github.softwareplace.springboot.versions.springRstDocsMockMVCVersion
import com.github.softwareplace.springboot.versions.springdocStarterWebmvc
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke


fun Project.kotlinOpenApiSettings(config: Action<OpenApiSettings>? = null) {
    val openApiSettings = OpenApiSettings()
    config?.invoke(openApiSettings)
    apply(plugin = "org.openapi.generator")

    if (openApiSettings.templateDir.isNullOrBlank()) {
        val templateSourceDir = javaClass.classLoader?.getResource("kotlin-spring")
        templateSourceDir?.let {
            openApiSettings.templateDir = it.path
        }
    }

    openApiGenerateConfig(openApiSettings)
    dependencies {
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocStarterWebmvc}")
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:${springRstDocsMockMVCVersion}")
    }
}
