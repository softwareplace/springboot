package com.gradle.kts.java.buildsource

import com.gradle.kts.build.configuration.*
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

private const val ORG_APACHE_TOMCAT_EMBED = "org.apache.tomcat.embed"
private const val TOMCAT_EMBED_WEBSOCKET = "tomcat-embed-websocket"
private const val TOMCAT_EMBED_CORE = "tomcat-embed-core"

private const val TOMCAT_EMBED_EL = "tomcat-embed-el"
private const val SPRING_BOOT_STARTER_WEB = "spring-boot-starter-web"
private const val SPRING_BOOT_STARTER_JETTY = "spring-boot-starter-jetty"
private const val SPRING_BOOT_STARTER_SECURITY = "spring-boot-starter-security"


fun DependencyHandlerScope.loggBack() {
    implementation("ch.qos.logback:logback-classic:${Dependencies.Version.loggBack}")
    implementation("ch.qos.logback:logback-core:${Dependencies.Version.loggBack}")
}

fun DependencyHandlerScope.test() {
    testImplementation("org.junit.jupiter:junit-jupiter:${Dependencies.Version.jUnitJupiter}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Dependencies.Version.mockitoKotlin}")
    testImplementation("io.mockk:mockk:${Dependencies.Version.ioMockkMockk}") {
        exclude("org.slf4j", "slf4j-api")
    }
}

fun DependencyHandlerScope.springSecurity() {
    springBootStartWeb()

    addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
        excludeSpringLogging()
    }

    testImplementation("org.springframework.security:spring-security-test:${Dependencies.Version.springBootSecurityTest}") {
        excludeSpringLogging()
    }
}

fun DependencyHandlerScope.javaxServlet() {
    implementation("javax.annotation:javax.annotation-api:${Dependencies.Version.javaxAnnotationApi}")
    implementation("javax.validation:validation-api:${Dependencies.Version.javaxValidationApi}")
    implementation("javax.servlet:servlet-api:${Dependencies.Version.javaxServlet}")
}

fun DependencyHandlerScope.jakarta() {
    implementation("jakarta.annotation:jakarta.annotation-api:${Dependencies.Version.jakartaAnnotation}")
}

fun DependencyHandlerScope.springBootStartWeb() {
    jakarta()
    addSpringframeworkBoot(SPRING_BOOT_STARTER_WEB) { excludeSpringLogging() }

    addSpringBootStarterValidation()

    addSpringframeworkBoot("spring-boot-starter") { excludeSpringLogging() }

    addSpringframeworkBootTest("spring-boot-starter-test") { excludeSpringLogging() }
}

fun DependencyHandlerScope.addSpringBootStarterValidation() {

    addSpringframeworkBoot("spring-boot-starter-validation") {
        excludeSpringLogging()
    }
}

fun DependencyHandlerScope.addCaching() {
    addSpringframeworkBoot("spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine:${Dependencies.Version.benManesCaffeine}")
}

fun Project.springJettyApi() {
    removeTomcatServer()
    dependencies {
        implementation("org.glassfish:jakarta.el:${Dependencies.Version.glassfishJakarta}")
        implementation("org.eclipse.jetty.http2:http2-server:${Dependencies.Version.eclipseJettyHttp2Server}")
        springBootStartWeb()
        addSpringframeworkBoot(SPRING_BOOT_STARTER_JETTY)
        springConfigurationProcessor()
    }
}

fun DependencyHandlerScope.springWebFlux() {
    addSpringframeworkBoot("spring-boot-starter-webflux")
}

fun DependencyHandlerScope.baseSpringApi() {
    springBootStartWeb()
    springConfigurationProcessor()
}

fun DependencyHandlerScope.springConfigurationProcessor() {
    annotationProcessor("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-configuration-processor:${Dependencies.Version.springBoot}")
}

fun DependencyHandlerScope.springBootSecurity() {
    addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
        excludeSpringLogging()
    }
}

fun DependencyHandlerScope.springBootSecurityUtil() {
    implementation("com.github.eliasmeireles:spring-boot-security-util:${Dependencies.Version.springBootSecurityUtilVersion}")
}

fun DependencyHandlerScope.jsonLogger() {
    implementation("com.github.eliasmeireles:json-logger:${Dependencies.Version.jsonLoggerVersion}")
}

fun ExternalModuleDependency.excludeSpringLogging() {
    exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = "spring-boot-starter-logging")
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

fun DependencyHandlerScope.passay() {
    implementation("org.passay:passay:${Dependencies.Version.passay}")
}

fun DependencyHandlerScope.jsonWebToken() {
    implementation("com.auth0:java-jwt:${Dependencies.Version.auth0JavaJwt}")
}

fun DependencyHandlerScope.mapStructJava() {
    implementation("org.mapstruct:mapstruct:${Dependencies.Version.mapStruct}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${Dependencies.Version.mapStruct}")
}

fun DependencyHandlerScope.mapstruct() {
    implementation("org.mapstruct:mapstruct:${Dependencies.Version.mapStruct}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${Dependencies.Version.mapStruct}")
}

fun DependencyHandlerScope.flayWayMigration() {
    runtimeOnly("org.flywaydb:flyway-core:${Dependencies.Version.flywaydb}")
}

fun DependencyHandlerScope.fasterXmlJackson() {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Dependencies.Version.jacksonVersion}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${Dependencies.Version.jacksonVersion}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Dependencies.Version.jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${Dependencies.Version.jacksonVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Dependencies.Version.jacksonVersion}")
}

fun DependencyHandlerScope.rxJava() {
    implementation("io.reactivex.rxjava2:rxjava:${Dependencies.Version.rxJava}")
}

fun DependencyHandlerScope.modelMapper() {
    implementation("org.modelmapper:modelmapper:${Dependencies.Version.modelMapper}")
}

fun DependencyHandlerScope.lombok() {
    implementation("org.projectlombok:lombok:${Dependencies.Version.lombokVersion}")
    implementation("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    annotationProcessor("org.projectlombok:lombok:${Dependencies.Version.lombokVersion}")
    mapStructJava()
}

fun DependencyHandlerScope.retrofit2() {
    implementation("com.squareup.retrofit2:retrofit:${Dependencies.Version.retrofit2Version}")
    implementation("com.squareup.retrofit2:converter-jackson:${Dependencies.Version.retrofit2Version}")
}


fun DependencyHandlerScope.springDataJpa() {
    addSpringframeworkBoot("spring-boot-starter-data-jpa") {
        excludeSpringLogging()
    }
}

fun DependencyHandlerScope.postgresql() {
    springDataJpa()
    implementation("org.postgresql:postgresql:${Dependencies.Version.postgresqlVersion}")
}

fun DependencyHandlerScope.testContainersPostgresql() {
    testImplementation("org.testcontainers:junit-jupiter:${Dependencies.Version.testContainersVersion}")
    testImplementation("org.testcontainers:postgresql:${Dependencies.Version.testContainersVersion}")
}

