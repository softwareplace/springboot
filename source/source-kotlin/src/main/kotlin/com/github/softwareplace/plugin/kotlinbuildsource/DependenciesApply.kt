package com.github.softwareplace.plugin.kotlinbuildsource

import com.github.softwareplace.plugin.buildconfiguration.*
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
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:${Dependencies.Version.slf4jApiVersion}")
}

fun DependencyHandlerScope.test() {
    testImplementation("org.junit.jupiter:junit-jupiter:${Dependencies.Version.jUnitJupiterVersion}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Dependencies.Version.mockitoKotlinVersion}")
    testImplementation("io.mockk:mockk:${Dependencies.Version.ioMockkMockkVersion}") {
        exclude("org.slf4j", "slf4j-api")
    }
}

fun DependencyHandlerScope.springSecurity() {
    springBootStartWeb()

    addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
        excludeSpringLogging()
    }

    testImplementation("org.springframework.security:spring-security-test:${Dependencies.Version.springBootSecurityTestVersion}") {
        excludeSpringLogging()
    }
}

fun DependencyHandlerScope.jakarta() {
    implementation("jakarta.annotation:jakarta.annotation-api:${Dependencies.Version.jakartaAnnotationVersion}")
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

fun DependencyHandlerScope.logstashLogbackEncoderVersion() {
    implementation("net.logstash.logback:logstash-logback-encoder:${Dependencies.Version.logstashLogbackEncoderVersion}")
}

fun Project.springJettyApi() {
    configurations.all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    dependencies {
        springBootStartWeb()
        runtimeOnly("$ORG_SPRINGFRAMEWORK_BOOT:$SPRING_BOOT_STARTER_JETTY")
        implementation("org.eclipse.jetty.http2:http2-server:${System.getProperty("jettyHttp2Server")}")
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
        implementation("org.eclipse.jetty.http2:http2-server:${System.getProperty("jettyHttp2Server")}")
    }
}

fun DependencyHandlerScope.baseSpringApi() {
    springBootStartWeb()
    springConfigurationProcessor()
}

fun DependencyHandlerScope.springConfigurationProcessor() {
    kaptAnnotationProcessor("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-configuration-processor")
}

fun DependencyHandlerScope.springBootSecurity() {
    addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
        excludeSpringLogging()
    }
}

fun DependencyHandlerScope.springBootSecurityUtil() {
    implementation("com.github.softwareplace:spring-boot-security-util:${Dependencies.Version.springBootSecurityUtilVersion}")
}

fun DependencyHandlerScope.jsonLogger() {
    loggBack()
    implementation("com.github.softwareplace:json-logger:${Dependencies.Version.jsonLoggerVersion}")
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
    implementation("org.passay:passay:${Dependencies.Version.passayVersion}")
}

fun DependencyHandlerScope.jsonWebToken() {
    implementation("com.auth0:java-jwt:${Dependencies.Version.auth0JavaJwt}")
}

fun DependencyHandlerScope.mappstruct() {
    implementation("org.mapstruct:mapstruct:${Dependencies.Version.mapStruct}")
    kaptAnnotationProcessor("org.mapstruct:mapstruct-processor:${Dependencies.Version.mapStruct}")
}

fun DependencyHandlerScope.flayWayMigration() {
    runtimeOnly("org.flywaydb:flyway-core:${Dependencies.Version.flywaydbVersion}")
}

fun DependencyHandlerScope.kotlinReactive() {
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("io.projectreactor:reactor-test")
}

fun DependencyHandlerScope.rxJava() {
    implementation("io.reactivex.rxjava2:rxjava:${Dependencies.Version.rxJavaVersion}")
}

fun DependencyHandlerScope.modelMapper() {
    implementation("org.modelmapper:modelmapper:${Dependencies.Version.modelMapperVersion}")
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
