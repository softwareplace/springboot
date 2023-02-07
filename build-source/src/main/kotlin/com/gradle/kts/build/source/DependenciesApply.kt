package com.gradle.kts.build.source

import com.gradle.kts.build.configuration.*
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope

private const val ORG_APACHE_TOMCAT_EMBED = "org.apache.tomcat.embed"
private const val TOMCAT_EMBED_EL = "tomcat-embed-el"
private const val SPRING_BOOT_STARTER_WEB = "spring-boot-starter-web"
private const val SPRING_BOOT_STARTER_JETTY = "spring-boot-starter-jetty"
private const val SPRING_BOOT_STARTER_TOMCAT = "spring-boot-starter-tomcat"
private const val SPRING_BOOT_STARTER_SECURITY = "spring-boot-starter-security"

fun DependencyHandlerScope.test() {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("io.mockk:mockk:1.13.2") {
        exclude("org.slf4j", "slf4j-api")
    }
}

fun DependencyHandlerScope.springSecurity() {
    springBootStartWeb()
    addSpringframeworkBoot(SPRING_BOOT_STARTER_WEB) {
        excludeSpringLogging()
    }

    addSpringframeworkBoot(SPRING_BOOT_STARTER_SECURITY) {
        excludeSpringLogging()
    }

    testImplementation("org.springframework.security:spring-security-test:${Dependencies.Version.springBootSecurityTest}") {
        excludeSpringLogging()
    }
}

fun DependencyHandlerScope.springBootStartWeb() {
    addSpringframeworkBoot(SPRING_BOOT_STARTER_JETTY) {
        excludeSpringLogging()
    }

    adSpringBootStarterValidation()

    addSpringframeworkBoot("spring-boot-starter") {
        excludeSpringLogging()
    }

    addSpringframeworkBootTest("spring-boot-starter-test") {
        excludeSpringLogging()
    }
}

fun DependencyHandlerScope.adSpringBootStarterValidation() {
    addSpringframeworkBoot("spring-boot-starter-validation") {
        excludeSpringLogging()
    }
}

fun DependencyHandlerScope.baseSpringApi() {
    springBootStartWeb()
    addSpringframeworkBoot("spring-boot-starter-webflux") {
        excludeSpringLogging()
    }

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
            Dependencies.LibDomain.gitHubEliasMeireles,
            Dependencies.Module.springBootSecurityUtil,
            Dependencies.Version.springBootSecurityUtilVersion,
        )
    ) {
        exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = SPRING_BOOT_STARTER_SECURITY)
        exclude(group = Dependencies.LibDomain.gitHubEliasMeireles, module = Dependencies.Module.jsonLogger)
        exclude(group = Dependencies.LibDomain.orgApacheLogging, module = Dependencies.Module.log4jApiKotlin)
    }
}

private fun ExternalModuleDependency.excludeSpringLogging() {
    exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = "spring-boot-starter-logging")
    exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = SPRING_BOOT_STARTER_TOMCAT)
    exclude(group = ORG_APACHE_TOMCAT_EMBED, module = TOMCAT_EMBED_EL)
}

fun DependencyHandlerScope.jsonLogger() {
    implementation(
        Dependencies.buildDependency(
            Dependencies.LibDomain.gitHubEliasMeireles,
            Dependencies.Module.jsonLogger,
            Dependencies.Version.jsonLoggerVersion,
        )
    )
}

fun DependencyHandlerScope.passay() {
    implementation("org.passay:passay:1.6.1")
}

fun DependencyHandlerScope.jsonWebToken() {
    implementation("io.jsonwebtoken:jjwt:0.9.1")
}

fun DependencyHandlerScope.flayWayMigration() {
    runtimeOnly("org.flywaydb:flyway-core:8.3.0")
}

fun DependencyHandlerScope.fasterXmlJackson() {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Dependencies.Version.jacksonVersion}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${Dependencies.Version.jacksonVersion}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Dependencies.Version.jacksonVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Dependencies.Version.jacksonVersion}")
}

fun DependencyHandlerScope.rxJava() {
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
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
    runtimeOnly("org.postgresql:postgresql:${Dependencies.Version.postgreSqlVersion}")
}

fun DependencyHandlerScope.testContainersPostgresql() {
    testImplementation("org.testcontainers:junit-jupiter:1.17.3")
    testImplementation("org.testcontainers:postgresql:${Dependencies.Version.testContainersVersion}")
}

fun DependencyHandlerScope.springDoc() {
    implementation("org.springdoc:springdoc-openapi-webmvc-core:${Dependencies.Version.springDocVersion}")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    implementation("org.springdoc:springdoc-openapi-ui:${Dependencies.Version.springDocVersion}")
    implementation("org.openapitools:jackson-databind-nullable:0.2.4")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("io.swagger:swagger-annotations:1.6.6")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.6.RELEASE")
    runtimeOnly("org.springdoc:springdoc-openapi-data-rest:${Dependencies.Version.springDocVersion}")
    runtimeOnly("org.springdoc:springdoc-openapi-kotlin:${Dependencies.Version.springDocVersion}")
}
