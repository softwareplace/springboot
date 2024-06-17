package com.github.softwareplace.springboot.kotlin

import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.buildconfiguration.kaptAnnotationProcessor
import com.github.softwareplace.springboot.buildconfiguration.testImplementation
import com.github.softwareplace.springboot.utils.mapStruct
import com.github.softwareplace.springboot.utils.testMockito
import com.github.softwareplace.springboot.versions.ioMockkMockkVersion
import com.github.softwareplace.springboot.versions.mapStructVersion
import com.github.softwareplace.springboot.versions.mockitoKotlinVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

fun Project.kotlinReactive() {
    dependencies {
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    }
}

fun Project.kotlinMapStruct() {
    mapStruct()
    dependencies {
        kaptAnnotationProcessor("org.mapstruct:mapstruct-processor:${mapStructVersion}")
    }
}


fun Project.testKotlinMockito() {
    dependencies {
        testMockito()
        testImplementation("org.mockito.kotlin:mockito-kotlin:${mockitoKotlinVersion}")
        testImplementation("io.mockk:mockk:${ioMockkMockkVersion}") {
            exclude("org.slf4j", "slf4j-api")
        }
    }
}

