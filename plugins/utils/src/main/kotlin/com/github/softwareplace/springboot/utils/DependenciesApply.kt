package com.github.softwareplace.springboot.utils

import com.github.softwareplace.springboot.buildconfiguration.*
import com.github.softwareplace.springboot.versions.Dependencies
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

const val ORG_APACHE_TOMCAT_EMBED = "org.apache.tomcat.embed"
const val TOMCAT_EMBED_WEBSOCKET = "tomcat-embed-websocket"
const val TOMCAT_EMBED_CORE = "tomcat-embed-core"
const val TOMCAT_EMBED_EL = "tomcat-embed-el"
const val SPRING_BOOT_STARTER_WEB = "spring-boot-starter-web"
const val SPRING_BOOT_STARTER_JETTY = "spring-boot-starter-jetty"
const val SPRING_BOOT_STARTER_SECURITY = "spring-boot-starter-security"

fun Project.jsonLogger(version: String? = null, loggBackVersion: String? = null) {
    dependencies {
        loggBack(loggBackVersion)
        implementation("com.github.softwareplace:json-logger:${version ?: Dependencies.Version.jsonLoggerVersion}")
    }
}

fun Project.logstashLogbackEncoderVersion(version: String? = null) {
    dependencies {
        implementation("net.logstash.logback:logstash-logback-encoder:${version ?: Dependencies.Version.logstashLogbackEncoderVersion}")
    }
}

fun Project.loggBack(version: String? = null) {
    dependencies {
        implementation("ch.qos.logback:logback-classic:${version ?: Dependencies.Version.loggBackVersion}")
        implementation("ch.qos.logback:logback-core:${version ?: Dependencies.Version.loggBackVersion}")
    }
}

fun Project.slf4j(version: String? = null) {
    dependencies {
        implementation("org.slf4j:slf4j-api:${version ?: Dependencies.Version.slf4jApiVersion}")
    }
}

/**
 * Also call [addSpringframeworkBoot]:[SPRING_BOOT_STARTER_SECURITY],
 * [testImplementation]:org.springframework.security:spring-security-test,
 * */
fun Project.springSecurity(
    version: String? = null,
    excludeSpringLogging: Boolean = false,
) {
    dependencies {
        addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
            if (excludeSpringLogging) {
                excludeSpringLogging()
            }
        }

        testImplementation("org.springframework.security:spring-security-test:${version ?: Dependencies.Version.springBootSecurityTestVersion}") {
            if (excludeSpringLogging) {
                excludeSpringLogging()
            }
        }
    }
}

fun Project.jakarta(version: String? = null) {
    dependencies {
        implementation("jakarta.annotation:jakarta.annotation-api:${version ?: Dependencies.Version.jakartaAnnotationVersion}")
    }
}

/**
 * Also call [jakarta], [addSpringframeworkBoot]:[SPRING_BOOT_STARTER_WEB],
 * [addSpringframeworkBoot]:spring-boot-starter,
 * [addSpringframeworkBoot]:spring-boot-starter-test methods.
 * */
fun Project.springBootStartWeb(
    excludeSpringLogging: Boolean = false,
    jakartaVersion: String? = null,
) {
    dependencies {
        jakarta(jakartaVersion)
        addSpringframeworkBoot(SPRING_BOOT_STARTER_WEB) {
            if (excludeSpringLogging) {
                excludeSpringLogging()
            }
        }

        addSpringBootStarterValidation(excludeSpringLogging)

        addSpringframeworkBoot("spring-boot-starter") {
            if (excludeSpringLogging) {
                excludeSpringLogging()
            }
        }

        addSpringframeworkBootTest("spring-boot-starter-test") {
            if (excludeSpringLogging) {
                excludeSpringLogging()
            }
        }
    }
}

fun Project.addSpringBootStarterValidation(excludeSpringLogging: Boolean = false) {
    dependencies {
        addSpringframeworkBoot("spring-boot-starter-validation") {
            if (excludeSpringLogging) {
                excludeSpringLogging()
            }
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
    configurations.all {
        exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = "spring-boot-starter-tomcat")
    }
    dependencies {
        implementation("$ORG_SPRINGFRAMEWORK_BOOT:$SPRING_BOOT_STARTER_JETTY")
        implementation("org.eclipse.jetty.http2:http2-server:${Dependencies.Version.jettyHttp2Server}")
    }
}

fun Project.mapStruct(version: String? = null) {
    dependencies {
        implementation("org.mapstruct:mapstruct:${version ?: Dependencies.Version.mapStructVersion}")
    }
}

fun Project.springWebFlux() {
    configurations.all {
        exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = "spring-boot-starter-tomcat")
    }
    dependencies {
        addSpringframeworkBoot("spring-boot-starter-webflux")
        runtimeOnly("$ORG_SPRINGFRAMEWORK_BOOT:$SPRING_BOOT_STARTER_JETTY")
        implementation("org.eclipse.jetty.http2:http2-server:${Dependencies.Version.jettyHttp2Server}")
    }
}

fun Project.springBootSecurity(excludeSpringLogging: Boolean = false) {
    dependencies {
        addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
            if (excludeSpringLogging) {
                excludeSpringLogging()
            }
        }
    }
}

fun Project.springBootSecurityUtil(version: String? = null) {
    dependencies {
        implementation("com.github.softwareplace:spring-boot-security-util:${version ?: Dependencies.Version.springBootSecurityUtilVersion}")
    }
}

fun ExternalModuleDependency.excludeSpringLogging() {
    exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = "spring-boot-starter-logging")
    exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = "spring-boot-starter-validation")
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

fun Project.retrofit2(version: String? = null) {
    dependencies {
        implementation("com.squareup.retrofit2:retrofit:${version ?: Dependencies.Version.retrofit2Version}")
        implementation("com.squareup.retrofit2:converter-jackson:${version ?: Dependencies.Version.retrofit2Version}")
    }
}


fun Project.springDataJpa(excludeSpringLogging: Boolean = false) {
    dependencies {
        addSpringframeworkBoot("spring-boot-starter-data-jpa") {
            if (excludeSpringLogging) {
                excludeSpringLogging()
            }
        }
    }
}

/**
 * Also call [springDataJpa] method.
 * */
fun Project.postgresql(version: String? = null, excludeSpringLogging: Boolean = false) {
    dependencies {
        springDataJpa(excludeSpringLogging)
        implementation("org.postgresql:postgresql:${version ?: Dependencies.Version.postgresqlVersion}")
    }
}


fun Project.testContainersPostgresql(version: String? = null) {
    dependencies {
        testImplementation("org.testcontainers:junit-jupiter:${version ?: Dependencies.Version.testContainersVersion}")
        testImplementation("org.testcontainers:postgresql:${version ?: Dependencies.Version.testContainersVersion}")
    }
}

fun Project.testJunitJupiter(
    jUnitJupiterVersion: String? = null
) {
    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter:${jUnitJupiterVersion ?: Dependencies.Version.jUnitJupiterVersion}")
    }
}
