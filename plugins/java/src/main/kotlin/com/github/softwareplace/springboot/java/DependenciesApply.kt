package com.github.softwareplace.springboot.java

import com.github.softwareplace.springboot.buildconfiguration.annotationProcessor
import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.utils.mapStruct
import com.github.softwareplace.springboot.versions.lombokMapstructBinding
import com.github.softwareplace.springboot.versions.lombokVersion
import com.github.softwareplace.springboot.versions.mapStructVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.lombok() {
    javaMapStruct()
    dependencies {
        implementation("org.projectlombok:lombok:${lombokVersion}")
        implementation("org.projectlombok:lombok-mapstruct-binding:${lombokMapstructBinding}")
        annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    }
}

fun Project.javaMapStruct() {
    mapStruct()
    dependencies {
        annotationProcessor("org.mapstruct:mapstruct-processor:${mapStructVersion}")
    }
}


    

