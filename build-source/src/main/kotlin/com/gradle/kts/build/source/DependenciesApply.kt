package com.gradle.kts.build.source

import com.gradle.kts.build.configuration.*
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.jackson() {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.3")
}

fun DependencyHandlerScope.test() {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("io.mockk:mockk:1.13.2")
}


fun DependencyHandlerScope.springSecurity() {
    addSpringframeworkBoot("spring-boot-starter-jetty")
    addSpringframeworkBoot("spring-boot-starter-validation") {
        excludeSpringLogging()
    }

    annotationProcessor("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-configuration-processor")

    addSpringframeworkBoot("spring-boot-starter-web") {
        excludeSpringLogging()
        exclude(group = "$ORG_SPRINGFRAMEWORK_BOOT", module = "spring-boot-starter-tomcat")
    }
    addSpringframeworkBoot("spring-boot-starter-security") {
        excludeSpringLogging()
    }

    addSpringframeworkBootTest("spring-boot-starter-test") {
        excludeSpringLogging()
    }
    testImplementation("org.springframework.security:spring-security-test") {
        excludeSpringLogging()
    }
}

private fun ExternalModuleDependency.excludeSpringLogging() {
    exclude(group = ORG_SPRINGFRAMEWORK_BOOT, module = "spring-boot-starter-logging")
}

fun DependencyHandlerScope.jsonLogger() {
    implementation("com.softwareplace:json-logger:1.0.0")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")
}

fun DependencyHandlerScope.kotlin() {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

fun DependencyHandlerScope.baseSpringApi() {
    addSpringframeworkBoot("spring-boot-starter") {
        excludeSpringLogging()
    }
    addSpringframeworkBoot("spring-boot-starter-webflux") {
        excludeSpringLogging()
    }

    annotationProcessor("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-configuration-processor")
}

fun DependencyHandlerScope.passay() {
    implementation("org.passay:passay:1.6.1")
}

fun DependencyHandlerScope.flayWayMigration() {
    runtimeOnly("org.flywaydb:flyway-core:8.3.0")
}


fun DependencyHandlerScope.fasterXmlJackson() {
    runtimeOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Dependencies.Version.jacksonVersion}")
    runtimeOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${Dependencies.Version.jacksonVersion}")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:${Dependencies.Version.jacksonVersion}")
}

fun DependencyHandlerScope.postGreSql() {
    addSpringframeworkBoot("spring-boot-starter-data-jpa") {
        excludeSpringLogging()
    }
    runtimeOnly("org.postgresql:postgresql:${Dependencies.Version.postgreSqlVersion}")
    testImplementation("org.testcontainers:junit-jupiter:1.17.3")
    testImplementation("org.testcontainers:postgresql:${Dependencies.Version.testContainersVersion}")
}

fun DependencyHandlerScope.springDoc() {
    implementation("org.springdoc:springdoc-openapi-webmvc-core:${Dependencies.Version.springDocVersion}")
    implementation("org.springdoc:springdoc-openapi-ui:${Dependencies.Version.springDocVersion}")
    implementation("org.openapitools:jackson-databind-nullable:0.2.3")
    implementation("io.swagger:swagger-annotations:1.6.6")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.6.RELEASE")
    runtimeOnly("org.springdoc:springdoc-openapi-data-rest:${Dependencies.Version.springDocVersion}")
    runtimeOnly("org.springdoc:springdoc-openapi-kotlin:${Dependencies.Version.springDocVersion}")
}

