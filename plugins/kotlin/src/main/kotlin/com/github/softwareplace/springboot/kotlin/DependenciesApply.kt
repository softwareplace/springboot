package com.github.softwareplace.springboot.kotlin

import com.github.softwareplace.springboot.buildconfiguration.*
import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

private const val ORG_APACHE_TOMCAT_EMBED = "org.apache.tomcat.embed"
private const val TOMCAT_EMBED_WEBSOCKET = "tomcat-embed-websocket"
private const val TOMCAT_EMBED_CORE = "tomcat-embed-core"

private const val TOMCAT_EMBED_EL = "tomcat-embed-el"
private const val SPRING_BOOT_STARTER_WEB = "spring-boot-starter-web"
private const val SPRING_BOOT_STARTER_JETTY = "spring-boot-starter-jetty"
private const val SPRING_BOOT_STARTER_SECURITY = "spring-boot-starter-security"


fun Project.loggBack(version: String? = null) {
    dependencies {
        implementation("org.slf4j:slf4j-api:${version ?: Dependencies.Version.slf4jApiVersion}")
    }
}

fun Project.test(
    mockitoKotlinVersion: String? = null,
    ioMockkMockkVersion: String? = null,
    jUnitJupiterVersion: String? = null
) {
    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter:${jUnitJupiterVersion ?: Dependencies.Version.jUnitJupiterVersion}")
        testImplementation("org.mockito.kotlin:mockito-kotlin:${mockitoKotlinVersion ?: Dependencies.Version.mockitoKotlinVersion}")
        testImplementation("io.mockk:mockk:${ioMockkMockkVersion ?: Dependencies.Version.ioMockkMockkVersion}") {
            exclude("org.slf4j", "slf4j-api")
        }
    }
}

fun Project.springSecurity(version: String? = null) {
    dependencies {
        springBootStartWeb()

        addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
            excludeSpringLogging()
        }

        testImplementation("org.springframework.security:spring-security-test:${version ?: Dependencies.Version.springBootSecurityTestVersion}") {
            excludeSpringLogging()
        }
    }
}

fun Project.jakarta(version: String? = null) {
    dependencies {
        implementation("jakarta.annotation:jakarta.annotation-api:${version ?: Dependencies.Version.jakartaAnnotationVersion}")
    }
}

fun Project.springBootStartWeb() {
    dependencies {

        jakarta()
        addSpringframeworkBoot(SPRING_BOOT_STARTER_WEB) { excludeSpringLogging() }

        addSpringBootStarterValidation()

        addSpringframeworkBoot("spring-boot-starter") { excludeSpringLogging() }

        addSpringframeworkBootTest("spring-boot-starter-test") { excludeSpringLogging() }
    }
}

fun Project.addSpringBootStarterValidation() {
    dependencies {

        addSpringframeworkBoot("spring-boot-starter-validation") {
            excludeSpringLogging()
        }
    }
}

fun Project.addCaching() {
    dependencies {
        addSpringframeworkBoot("spring-boot-starter-cache")
        implementation("com.github.ben-manes.caffeine:caffeine:${Dependencies.Version.benManesCaffeineVersion}")
    }
}

fun Project.logstashLogbackEncoderVersion(version: String? = null) {
    dependencies {
        implementation("net.logstash.logback:logstash-logback-encoder:${version ?: Dependencies.Version.logstashLogbackEncoderVersion}")
    }
}

fun Project.springJettyApi() {
    configurations.all {
        exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = "spring-boot-starter-tomcat")
    }
    dependencies {
        springBootStartWeb()
        implementation("$ORG_SPRINGFRAMEWORK_BOOT:$SPRING_BOOT_STARTER_JETTY")
        implementation("org.eclipse.jetty.http2:http2-server:${Dependencies.Version.jettyHttp2Server}")
        springConfigurationProcessor()
    }
}

fun Project.springWebFlux() {
    configurations.all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    dependencies {
        kotlinReactive()
        springConfigurationProcessor()
        addSpringframeworkBoot("spring-boot-starter-webflux")
        runtimeOnly("$ORG_SPRINGFRAMEWORK_BOOT:$SPRING_BOOT_STARTER_JETTY")
        implementation("org.eclipse.jetty.http2:http2-server:${Dependencies.Version.jettyHttp2Server}")
    }
}

fun Project.baseSpringApi() {
    springBootStartWeb()
    springConfigurationProcessor()
}

fun Project.springConfigurationProcessor() {
    dependencies {
        kaptAnnotationProcessor("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-configuration-processor")
    }
}

fun Project.springBootSecurity() {
    dependencies {
        addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
            excludeSpringLogging()
        }
    }
}

fun Project.springBootSecurityUtil(version: String? = null) {
    dependencies {
        implementation("com.github.softwareplace:spring-boot-security-util:${version ?: Dependencies.Version.springBootSecurityUtilVersion}")
    }
}

fun Project.jsonLogger(version: String? = null, loggBackVersion: String? = null) {
    dependencies {
        loggBack(loggBackVersion)
        implementation("com.github.softwareplace:json-logger:${version ?: Dependencies.Version.jsonLoggerVersion}")
    }
}

fun ExternalModuleDependency.excludeSpringLogging() {
    exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = "spring-boot-starter-logging")
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-validation")
}

fun Project.removeTomcatServer() {
    configurations.exclude(
        group = ORG_APACHE_TOMCAT_EMBED,
        modules = arrayOf(
            TOMCAT_EMBED_CORE,
            TOMCAT_EMBED_EL,
            TOMCAT_EMBED_WEBSOCKET
        )
    )
}

fun Project.passay(version: String? = null) {
    dependencies {
        implementation("org.passay:passay:${version ?: Dependencies.Version.passayVersion}")
    }
}

fun Project.jsonWebToken(version: String? = null) {
    dependencies {
        implementation("com.auth0:java-jwt:${version ?: Dependencies.Version.auth0JavaJwtVersion}")
    }
}

fun Project.mappstruct(version: String? = null) {
    dependencies {
        implementation("org.mapstruct:mapstruct:${version ?: Dependencies.Version.mapStructVersion}")
        kaptAnnotationProcessor("org.mapstruct:mapstruct-processor:${version ?: Dependencies.Version.mapStructVersion}")
    }
}

fun Project.flayWayMigration(version: String? = null) {
    dependencies {
        runtimeOnly("org.flywaydb:flyway-core:${version ?: Dependencies.Version.flywaydbVersion}")
    }
}

fun Project.kotlinReactive() {
    dependencies {
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        testImplementation("io.projectreactor:reactor-test")
    }
}

fun Project.rxJava(version: String? = null) {
    dependencies {
        implementation("io.reactivex.rxjava2:rxjava:${version ?: Dependencies.Version.rxJavaVersion}")
    }
}

fun Project.modelMapper(version: String? = null) {
    dependencies {
        implementation("org.modelmapper:modelmapper:${version ?: Dependencies.Version.modelMapperVersion}")
    }
}

fun Project.retrofit2(version: String? = null) {
    dependencies {
        implementation("com.squareup.retrofit2:retrofit:${version ?: Dependencies.Version.retrofit2Version}")
        implementation("com.squareup.retrofit2:converter-jackson:${version ?: Dependencies.Version.retrofit2Version}")
    }
}


fun Project.springDataJpa() {
    dependencies {
        addSpringframeworkBoot("spring-boot-starter-data-jpa") {
            excludeSpringLogging()
        }
    }
}


fun Project.postgresql(version: String? = null) {
    dependencies {
        springDataJpa()
        implementation("org.postgresql:postgresql:${version ?: Dependencies.Version.postgresqlVersion}")
    }
}

fun Project.testContainersPostgresql(version: String? = null) {
    dependencies {
        testImplementation("org.testcontainers:junit-jupiter:${version ?: Dependencies.Version.testContainersVersion}")
        testImplementation("org.testcontainers:postgresql:${version ?: Dependencies.Version.testContainersVersion}")
    }
}
