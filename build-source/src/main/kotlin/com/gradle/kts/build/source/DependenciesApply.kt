package com.gradle.kts.build.source

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
    implementation("javax.servlet:servlet-api:${Dependencies.Version.javaxServlet}")
}

fun DependencyHandlerScope.springBootStartWeb() {
    addSpringframeworkBoot(SPRING_BOOT_STARTER_WEB) { excludeSpringLogging() }

    adSpringBootStarterValidation()

    addSpringframeworkBoot("spring-boot-starter") { excludeSpringLogging() }

    addSpringframeworkBootTest("spring-boot-starter-test") { excludeSpringLogging() }
}

fun DependencyHandlerScope.adSpringBootStarterValidation() {

    addSpringframeworkBoot("spring-boot-starter-validation") {
        excludeSpringLogging()
    }
}

fun Project.springJettyApi() {
    removeTomcatServer()
    dependencies {
        implementation("org.eclipse.jetty.http2:http2-server:${Dependencies.Version.eclipseJettyHttp2Server}")
        springBootStartWeb()
        addSpringframeworkBoot(SPRING_BOOT_STARTER_JETTY)
        springConfigurationProcessor()
    }
}

fun DependencyHandlerScope.springWebFlux() {
    addSpringframeworkBoot("spring-boot-starter-webflux") {
        excludeSpringLogging()
    }
}

fun DependencyHandlerScope.baseSpringApi() {
    springBootStartWeb()
    implementation("")
    springConfigurationProcessor()
}

fun DependencyHandlerScope.springConfigurationProcessor() {
    annotationProcessor("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-configuration-processor:${Dependencies.Version.springBoot}")
}

fun DependencyHandlerScope.springBootSecurityUtils() {
    addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
        excludeSpringLogging()
    }

    implementation(
        Dependencies.buildDependency(
            Dependencies.Group.gitHubEliasMeireles,
            Dependencies.Module.springBootSecurityUtil,
            Dependencies.Version.springBootSecurityUtilVersion,
        )
    ) {
        exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = SPRING_BOOT_STARTER_SECURITY)
        exclude(group = Dependencies.Group.gitHubEliasMeireles, module = Dependencies.Module.jsonLogger)
        exclude(group = Dependencies.Group.orgApacheLogging, module = Dependencies.Module.log4jApiKotlin)
    }
}

fun ExternalModuleDependency.excludeSpringLogging() {
    exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = "spring-boot-starter-logging")
}

fun DependencyHandlerScope.jsonLogger() {
    implementation(
        Dependencies.buildDependency(
            Dependencies.Group.gitHubEliasMeireles,
            Dependencies.Module.jsonLogger,
            Dependencies.Version.jsonLoggerVersion,
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

fun DependencyHandlerScope.passay() {
    implementation("org.passay:passay:${Dependencies.Version.passay}")
}

fun DependencyHandlerScope.jsonWebToken() {
    implementation("io.jsonwebtoken:jjwt:${Dependencies.Version.jsonwebtoken}")
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

fun DependencyHandlerScope.lombok() {
    implementation("org.projectlombok:lombok:${Dependencies.Version.lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${Dependencies.Version.lombokVersion}")
}

fun DependencyHandlerScope.retrofit2() {
    implementation("com.squareup.retrofit2:retrofit:${Dependencies.Version.retrofit2Version}")
    implementation("com.squareup.retrofit2:converter-jackson:${Dependencies.Version.retrofit2Version}")
}

fun DependencyHandlerScope.postgresql() {
    addSpringframeworkBoot("spring-boot-starter-data-jpa") {
        excludeSpringLogging()
    }
    runtimeOnly("org.postgresql:postgresql:${Dependencies.Version.postgresqlVersion}")
}

fun DependencyHandlerScope.testContainersPostgresql() {
    testImplementation("org.testcontainers:junit-jupiter:${Dependencies.Version.testContainersVersion}")
    testImplementation("org.testcontainers:postgresql:${Dependencies.Version.testContainersVersion}")
}

fun DependencyHandlerScope.springDoc() {
    implementation("org.springdoc:springdoc-openapi-webmvc-core:${Dependencies.Version.springDocVersion}")
    implementation("jakarta.annotation:jakarta.annotation-api:${Dependencies.Version.jakartaAnnotation}")
    implementation("org.springdoc:springdoc-openapi-ui:${Dependencies.Version.springDocVersion}")
    implementation("org.openapitools:jackson-databind-nullable:${Dependencies.Version.openapitoolsJacksonDatabindNullable}")
    implementation("javax.annotation:javax.annotation-api:${Dependencies.Version.javaxAnnotationApi}")
    implementation("javax.validation:validation-api:${Dependencies.Version.javaxValidationApi}")
    implementation("io.swagger:swagger-annotations:${Dependencies.Version.swaggerAnnotation}")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:${Dependencies.Version.springRstDocsMockMVC}")
    runtimeOnly("org.springdoc:springdoc-openapi-data-rest:${Dependencies.Version.springDocVersion}")
    runtimeOnly("org.springdoc:springdoc-openapi-kotlin:${Dependencies.Version.springDocVersion}")
}
