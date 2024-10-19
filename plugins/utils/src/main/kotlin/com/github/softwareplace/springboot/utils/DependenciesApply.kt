package com.github.softwareplace.springboot.utils

import com.github.softwareplace.springboot.buildconfiguration.*
import com.github.softwareplace.springboot.versions.*
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

const val ORG_APACHE_TOMCAT_EMBED = "org.apache.tomcat.embed"
const val TOMCAT_EMBED_WEBSOCKET = "tomcat-embed-websocket"
const val TOMCAT_EMBED_CORE = "tomcat-embed-core"
const val TOMCAT_EMBED_EL = "tomcat-embed-el"
const val SPRING_BOOT_STARTER_WEB = "spring-boot-starter-web"
const val SPRING_BOOT_STARTER_JETTY = "spring-boot-starter-jetty"
const val SPRING_BOOT_STARTER_SECURITY = "spring-boot-starter-security"

fun Project.jsonLogger() {
    dependencies {
        loggBack()
        implementation("com.github.softwareplace:json-logger:${jsonLoggerVersion}")
    }
}

fun Project.logstashLogbackEncoderVersion() {
    dependencies {
        implementation("net.logstash.logback:logstash-logback-encoder:${logstashLogbackEncoderVersion}")
    }
}

fun Project.loggBack() {
    dependencies {
        implementation("ch.qos.logback:logback-classic:${loggBackVersion}")
        implementation("ch.qos.logback:logback-core:${loggBackVersion}")
    }
}

fun Project.slf4j() {
    dependencies {
        implementation("org.slf4j:slf4j-api:${slf4jApiVersion}")
    }
}

/**
 * Also call [addSpringframeworkBoot]:[SPRING_BOOT_STARTER_SECURITY],
 * [testImplementation]:org.springframework.security:spring-security-test,
 * */
fun Project.springSecurity(
    excludeSpringLogging: Boolean = false,
) {
    dependencies {
        addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
            if (excludeSpringLogging) {
                excludeSpringLogging()
            }
        }

        testImplementation("org.springframework.security:spring-security-test:${springBootSecurityTestVersion}") {
            if (excludeSpringLogging) {
                excludeSpringLogging()
            }
        }
    }
}

fun Project.jakarta() {
    dependencies {
        implementation("jakarta.annotation:jakarta.annotation-api:${jakartaAnnotationVersion}")
    }
}

/**
 * Also call [jakarta], [addSpringframeworkBoot]:[SPRING_BOOT_STARTER_WEB],
 * [addSpringframeworkBoot]:spring-boot-starter,
 * [addSpringframeworkBoot]:spring-boot-starter-test methods.
 * */
fun Project.springBootStartWeb(
    excludeSpringLogging: Boolean = false,
) {
    dependencies {
        jakarta()
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
        implementation("com.github.ben-manes.caffeine:caffeine:${benManesCaffeineVersion}")
    }
}

fun Project.springJettyApi() {
    configurations.all {
        exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = "spring-boot-starter-tomcat")
    }
    dependencies {
        implementation("$ORG_SPRINGFRAMEWORK_BOOT:$SPRING_BOOT_STARTER_JETTY")
        implementation("org.eclipse.jetty.http2:http2-server:${jettyHttp2Server}")
    }
}

fun Project.mapStruct() {
    dependencies {
        implementation("org.mapstruct:mapstruct:${mapStructVersion}")
    }
}

fun Project.springWebFlux() {
    springBootStartWeb()
    dependencies {
        addSpringframeworkBoot("spring-boot-starter-webflux")
        runtimeOnly("$ORG_SPRINGFRAMEWORK_BOOT:$SPRING_BOOT_STARTER_JETTY")
        implementation("org.eclipse.jetty.http2:http2-server:${jettyHttp2Server}")
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

fun Project.springBootSecurityUtil() {
    dependencies {
        springBootOauth2ResourceServer()
        springBootSecurity()
        implementation("com.github.softwareplace.springboot:security")
    }
}

fun Project.excludeSpringLogging() {
    configurations.exclude(
        group = ORG_SPRINGFRAMEWORK_BOOT,
        modules = arrayOf(
            "spring-boot-starter-logging",
            "spring-boot-starter-validation"
        )
    )
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

fun Project.passay() {
    dependencies {
        implementation("org.passay:passay:${passayVersion}")
    }
}

fun Project.springBootOauth2ResourceServer() {
    dependencies {
        addSpringframeworkBoot("spring-boot-starter-oauth2-resource-server")
    }
}

fun Project.springBootThymeleaf() {
    dependencies {
        addSpringframeworkBoot("spring-boot-starter-thymeleaf")
    }
}

fun Project.jsonWebToken() {
    dependencies {
        implementation("com.auth0:java-jwt:${auth0JavaJwtVersion}")
    }
}

/**
 * args: [modules] - List of modules that will use flyway migration. Example: "flyway-database-postgresql".
 * @see <a href="https://github.com/flyway/flyway/tree/main/flyway-database">flyway-database</a> for more modules.
 * */
fun Project.flayWayMigration(modules: List<String> = emptyList()) {
    dependencies {
        implementation("org.flywaydb:flyway-core:${flywaydbVersion}")
        modules.forEach {
            implementation("org.flywaydb:$it:${flywaydbVersion}")
        }
    }
}

fun Project.springBootStarter() {
    dependencies {
        implementation("com.github.softwareplace.springboot:starter")
    }
}

fun Project.springBootDataCommons() {
    dependencies {
        implementation("com.github.softwareplace.springboot:data-commons")
    }
}

fun Project.rxJava() {
    dependencies {
        implementation("io.reactivex.rxjava2:rxjava:${rxJavaVersion}")
    }
}

fun Project.retrofit2() {
    dependencies {
        implementation("com.squareup.retrofit2:retrofit:${retrofit2Version}")
        implementation("com.squareup.retrofit2:converter-jackson:${retrofit2Version}")
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
fun Project.postgresql(excludeSpringLogging: Boolean = false) {
    dependencies {
        springDataJpa(excludeSpringLogging)
        implementation("org.postgresql:postgresql:${postgresqlVersion}")
    }
}


fun Project.testContainersPostgresql() {
    dependencies {
        testImplementation("org.testcontainers:junit-jupiter:${testContainersVersion}")
        testImplementation("org.testcontainers:postgresql:${testContainersVersion}")
    }
}

fun Project.testMockito() {
    dependencies {
        testImplementation("org.mockito:mockito-core:$mockitoVersion")
        testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
        testImplementation("org.junit.jupiter:junit-jupiter:${jUnitJupiterVersion}")
    }
}
