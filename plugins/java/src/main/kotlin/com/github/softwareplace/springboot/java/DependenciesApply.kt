package com.github.softwareplace.springboot.java

import com.github.softwareplace.springboot.buildconfiguration.ORG_SPRINGFRAMEWORK_BOOT
import com.github.softwareplace.springboot.buildconfiguration.annotationProcessor
import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.buildconfiguration.testImplementation
import com.github.softwareplace.springboot.utils.testJunitJupiter
import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.testJavaMockito(mockitoVersion: String? = null, jUnitJupiterVersion: String? = null) {
    dependencies {
        testJunitJupiter(jUnitJupiterVersion)
        testImplementation("org.mockito:mockito-core:${mockitoVersion ?: Dependencies.Version.mockitoVersion}")
        testImplementation("org.mockito:mockito-junit-jupiter:${mockitoVersion ?: Dependencies.Version.mockitoVersion}")
    }
}

fun Project.lombok(
    lombokVersion: String? = null,
    lombokMapstructBinding: String? = null,
) {
    dependencies {
        implementation("org.projectlombok:lombok:${lombokVersion ?: Dependencies.Version.lombokVersion}")
        annotationProcessor("org.projectlombok:lombok:${lombokVersion ?: Dependencies.Version.lombokVersion}")
        implementation("org.projectlombok:lombok-mapstruct-binding:${lombokMapstructBinding ?: Dependencies.Version.lombokMapstructBinding}")
    }
}

fun Project.mapStructAnnotationProcessor(mapStructVersion: String? = null) {
    dependencies {
        annotationProcessor("org.mapstruct:mapstruct-processor:${mapStructVersion ?: Dependencies.Version.mapStructVersion}")
    }
}

fun Project.springBootConfigurationProcessor() {
    dependencies {
        annotationProcessor("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-configuration-processor")
    }
}
    

