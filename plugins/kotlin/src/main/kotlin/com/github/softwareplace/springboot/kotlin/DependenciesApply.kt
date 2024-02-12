package com.github.softwareplace.springboot.kotlin

import com.github.softwareplace.springboot.buildconfiguration.ORG_SPRINGFRAMEWORK_BOOT
import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.buildconfiguration.kaptAnnotationProcessor
import com.github.softwareplace.springboot.buildconfiguration.testImplementation
import com.github.softwareplace.springboot.utils.testJunitJupiter
import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

fun Project.kotlinReactive() {
    dependencies {
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    }
}

fun Project.mapStructKaptAnnotationProcessor(version: String? = null) {
    dependencies {
        kaptAnnotationProcessor("org.mapstruct:mapstruct-processor:${version ?: Dependencies.Version.mapStructVersion}")
    }
}

fun Project.springBootKaptAnnotationProcessor() {
    dependencies {
        kaptAnnotationProcessor("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-configuration-processor")
    }
}

fun Project.testKotlinMockito(
    mockitoKotlinVersion: String? = null,
    ioMockkMockkVersion: String? = null,
    jUnitJupiterVersion: String? = null
) {
    dependencies {
        testJunitJupiter(jUnitJupiterVersion)
        testImplementation("org.mockito.kotlin:mockito-kotlin:${mockitoKotlinVersion ?: Dependencies.Version.mockitoKotlinVersion}")
        testImplementation("io.mockk:mockk:${ioMockkMockkVersion ?: Dependencies.Version.ioMockkMockkVersion}") {
            exclude("org.slf4j", "slf4j-api")
        }
    }
}

