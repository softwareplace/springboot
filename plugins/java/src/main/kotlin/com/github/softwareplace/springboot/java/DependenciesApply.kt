package com.github.softwareplace.springboot.java

import com.github.softwareplace.springboot.buildconfiguration.*
import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

private const val ORG_APACHE_TOMCAT_EMBED = "org.apache.tomcat.embed"
private const val TOMCAT_EMBED_WEBSOCKET = "tomcat-embed-websocket"
private const val TOMCAT_EMBED_CORE = "tomcat-embed-core"

private const val TOMCAT_EMBED_EL = "spring-boot-tomcat-embed-el"
private const val SPRING_BOOT_STARTER_WEB = "spring-boot-starter-web"
private const val SPRING_BOOT_STARTER_JETTY = "spring-boot-starter-jetty"
private const val SPRING_BOOT_STARTER_SECURITY = "spring-boot-starter-security"


fun Project.loggBack(version: String? = null) {
    dependencies {
        implementation("ch.qos.logback:logback-classic:${version ?: Dependencies.Version.loggBackVersion}")
        implementation("ch.qos.logback:logback-core:${version ?: Dependencies.Version.loggBackVersion}")
    }
}

fun Project.test(mockitoVersion: String? = null, jUnitJupiterVersion: String? = null) {
    dependencies {
        testImplementation("org.mockito:mockito-core:${mockitoVersion ?: Dependencies.Version.mockitoVersion}")
        testImplementation("org.mockito:mockito-junit-jupiter:${mockitoVersion ?: Dependencies.Version.mockitoVersion}")
        testImplementation("org.junit.jupiter:junit-jupiter:${jUnitJupiterVersion ?: Dependencies.Version.jUnitJupiterVersion}")
    }
}

fun Project.springSecurity() {
    dependencies {
        springBootStartWeb()

        addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
            excludeSpringLogging()
        }

        testImplementation("org.springframework.security:spring-security-test:${Dependencies.Version.springBootSecurityTestVersion}") {
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

//    addSpringBootStarterValidation()

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

fun Project.springJettyApi() {
    removeTomcatServer()
    dependencies {
        springBootStartWeb()
        addSpringframeworkBoot(SPRING_BOOT_STARTER_JETTY)
        springConfigurationProcessor()
    }
}

fun Project.springWebFlux() {
    dependencies {
        addSpringframeworkBoot("spring-boot-starter-webflux")
    }
}

fun Project.baseSpringApi() {
    dependencies {
        springBootStartWeb()
        springConfigurationProcessor()
    }
}

fun Project.springConfigurationProcessor() {
    dependencies {
        annotationProcessor("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-configuration-processor")
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

fun Project.jsonLogger(version: String? = null) {
    dependencies {
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
        implementation("com.auth0:java-jwt:${version ?: Dependencies.Version.mapStructVersion}")
    }
}

fun Project.mapstruct(mapStructVersion: String? = null) {
    dependencies {
        implementation("org.mapstruct:mapstruct:${mapStructVersion ?: Dependencies.Version.mapStructVersion}")
        annotationProcessor("org.mapstruct:mapstruct-processor:${mapStructVersion ?: Dependencies.Version.mapStructVersion}")
    }
}

fun Project.flayWayMigration(version: String? = null) {
    dependencies {
        runtimeOnly("org.flywaydb:flyway-core:${version ?: Dependencies.Version.flywaydbVersion}")
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

fun Project.lombok(
    lombokVersion: String? = null,
    lombokMapstructBinding: String? = null,
    mapStructVersion: String? = null
) {
    dependencies {
        implementation("org.projectlombok:lombok:${lombokVersion ?: Dependencies.Version.lombokVersion}")
        annotationProcessor("org.projectlombok:lombok:${lombokVersion ?: Dependencies.Version.lombokVersion}")
        implementation("org.projectlombok:lombok-mapstruct-binding:${lombokMapstructBinding ?: Dependencies.Version.lombokMapstructBinding}")
        mapstruct(mapStructVersion = mapStructVersion)
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
        addSpringframeworkBoot("starter-data-jpa") {
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
    

