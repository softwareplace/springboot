package com.github.softwareplace.springboot.java

import com.github.softwareplace.springboot.buildconfiguration.annotationProcessor
import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.utils.mapStruct
import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.lombok(
    lombokVersion: String? = null,
    lombokMapstructBinding: String? = null,
    mapStructVersion: String? = null
) {
    javaMapStruct(mapStructVersion)
    dependencies {
        implementation("org.projectlombok:lombok:${lombokVersion ?: Dependencies.Version.lombokVersion}")
        implementation("org.projectlombok:lombok-mapstruct-binding:${lombokMapstructBinding ?: Dependencies.Version.lombokMapstructBinding}")
        annotationProcessor("org.projectlombok:lombok:${lombokVersion ?: Dependencies.Version.lombokVersion}")
    }
}

fun Project.javaMapStruct(mapStructVersion: String? = null) {
    mapStruct(mapStructVersion)
    dependencies {
        annotationProcessor("org.mapstruct:mapstruct-processor:${mapStructVersion ?: Dependencies.Version.mapStructVersion}")
    }
}


    

