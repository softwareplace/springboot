package com.gradle.kts.build.source

import com.gradle.kts.build.configuration.Dependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyApplicationDependencies()
            applyPlugins()
        }
    }

    private fun Project.applyPlugins() {
        plugins.apply("org.jetbrains.kotlin.jvm")
    }

    private fun Project.applyApplicationDependencies() {

        dependencies {
//            implementation("com.softwareplace:json-logger:1.0.0")
//            implementation("com.softwareplace:spring-boot-security-utils:1.0.0")
//
//            implementation("org.springframework.boot:spring-boot-starter-web:${Dependencies.Version.springBootVersion}") {
//                exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
//            }
//
//            implementation("org.springframework.boot:spring-boot-starter-jetty:${Dependencies.Version.springBootVersion}")
//            implementation("org.springframework.boot:spring-boot-starter-webflux:${Dependencies.Version.springBootVersion}")
//            implementation("org.springframework.boot:spring-boot-starter:${Dependencies.Version.springBootVersion}")
//            implementation("org.springframework.boot:spring-boot-starter-data-jpa:${Dependencies.Version.springBootVersion}")
//            implementation("org.springframework.boot:spring-boot-starter-security:${Dependencies.Version.springBootVersion}")
//            implementation("org.springframework.boot:spring-boot-starter-validation:${Dependencies.Version.springBootVersion}")
//            annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:${Dependencies.Version.springBootVersion}")
//
//            implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")
//            implementation("org.jetbrains.kotlin:kotlin-reflect")
//            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//
//            implementation("org.passay:passay:1.6.1")
//
//            runtimeOnly("org.flywaydb:flyway-core:8.3.0")
//
//            implementation("org.jetbrains.kotlin:kotlin-reflect")
//            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//
//            implementation("org.springdoc:springdoc-openapi-webmvc-core:${Dependencies.Version.springDocVersion}")
//            implementation("org.springdoc:springdoc-openapi-ui:${Dependencies.Version.springDocVersion}")
//
//            implementation("org.openapitools:jackson-databind-nullable:0.2.3")
//            implementation("io.swagger:swagger-annotations:1.6.6")
//
//            runtimeOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Dependencies.Version.jacksonVersion}")
//            runtimeOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${Dependencies.Version.jacksonVersion}")
//            runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:${Dependencies.Version.jacksonVersion}")
//
//            runtimeOnly("org.springdoc:springdoc-openapi-data-rest:${Dependencies.Version.springDocVersion}")
//            runtimeOnly("org.springdoc:springdoc-openapi-kotlin:${Dependencies.Version.springDocVersion}")
//
//            runtimeOnly("org.postgresql:postgresql:${Dependencies.Version.postgreSqlVersion}")
//
////            pitest("org.pitest:pitest-junit5-plugin:1.0.0")
//            testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.6.RELEASE")
//            testImplementation("org.testcontainers:junit-jupiter:1.17.3")
//            testImplementation("org.testcontainers:postgresql:${Dependencies.Version.testContainersVersion}")
//            testImplementation("org.springframework.boot:spring-boot-starter-test:${Dependencies.Version.springBootVersion}")
//            testImplementation("org.springframework.security:spring-security-test")
        }
    }
}
