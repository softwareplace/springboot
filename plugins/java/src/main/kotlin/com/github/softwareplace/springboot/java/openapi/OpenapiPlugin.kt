package com.github.softwareplace.springboot.java.openapi

import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.buildconfiguration.testImplementation
import com.github.softwareplace.springboot.versions.openApiToolsJacksonDatabindNullableVersion
import com.github.softwareplace.springboot.versions.springRstDocsMockMVCVersion
import com.github.softwareplace.springboot.versions.springdocStarterWebmvc
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke

fun Project.javaOpenApiSettings(config: Action<OpenApiSettings>? = null) {
    apply(plugin = "org.openapi.generator")

    val openApiSettings = OpenApiSettings()
    config?.invoke(openApiSettings)

//    if (openApiSettings.templateDir.isNullOrBlank()) {
//        val templateSourceDir = javaClass.classLoader?.getResource("java-spring")
//        templateSourceDir?.let {
//            openApiSettings.templateDir = it.path
//        }
//    }

    openApiGenerateConfig(openApiSettings)

    dependencies {
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocStarterWebmvc}")
        implementation("org.openapitools:jackson-databind-nullable:${openApiToolsJacksonDatabindNullableVersion}")
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:${springRstDocsMockMVCVersion}")
    }
}
