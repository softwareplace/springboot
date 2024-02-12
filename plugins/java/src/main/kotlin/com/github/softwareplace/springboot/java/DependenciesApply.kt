package com.github.softwareplace.springboot.java

import com.github.softwareplace.springboot.buildconfiguration.annotationProcessor
import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.buildconfiguration.testImplementation
import com.github.softwareplace.springboot.utils.mapStruct
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

fun Project.javaMapStruct(mapStructVersion: String? = null) {
    mapStruct(mapStructVersion)
    dependencies {
        annotationProcessor("org.mapstruct:mapstruct-processor:${mapStructVersion ?: Dependencies.Version.mapStructVersion}")
    }
}


    

