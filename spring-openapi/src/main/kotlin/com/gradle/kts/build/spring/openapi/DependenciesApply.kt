package com.gradle.kts.build.openapi

import com.gradle.kts.build.configuration.*
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.springDoc() {
    implementation("org.springdoc:springdoc-openapi-webmvc-core:${Dependencies.Version.springDocVersion}")
    implementation("org.springdoc:springdoc-openapi-ui:${Dependencies.Version.springDocVersion}")
    implementation("org.openapitools:jackson-databind-nullable:0.2.3")
    implementation("io.swagger:swagger-annotations:1.6.6")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.6.RELEASE")
    runtimeOnly("org.springdoc:springdoc-openapi-data-rest:${Dependencies.Version.springDocVersion}")
    runtimeOnly("org.springdoc:springdoc-openapi-kotlin:${Dependencies.Version.springDocVersion}")
}

