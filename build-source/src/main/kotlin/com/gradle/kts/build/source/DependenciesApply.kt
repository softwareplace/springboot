package com.gradle.kts.build.source

import com.gradle.kts.build.configuration.Dependencies

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
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

fun DependencyHandlerScope.logBack() {
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")
}

fun DependencyHandlerScope.jsonLogger() {
    implementation("com.softwareplace:json-logger:1.0.0")
}

fun DependencyHandlerScope.kotlin() {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

fun DependencyHandlerScope.baseSpringAppDeps() {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.passay:passay:1.6.1")

    runtimeOnly("org.flywaydb:flyway-core:8.3.0")

    implementation("org.springdoc:springdoc-openapi-webmvc-core:${Dependencies.Version.springDocVersion}")
    implementation("org.springdoc:springdoc-openapi-ui:${Dependencies.Version.springDocVersion}")

    implementation("org.openapitools:jackson-databind-nullable:0.2.3")
    implementation("io.swagger:swagger-annotations:1.6.6")

    runtimeOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Dependencies.Version.jacksonVersion}")
    runtimeOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${Dependencies.Version.jacksonVersion}")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:${Dependencies.Version.jacksonVersion}")

    runtimeOnly("org.springdoc:springdoc-openapi-data-rest:${Dependencies.Version.springDocVersion}")
    runtimeOnly("org.springdoc:springdoc-openapi-kotlin:${Dependencies.Version.springDocVersion}")

    runtimeOnly("org.postgresql:postgresql:${Dependencies.Version.postgreSqlVersion}")

    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.6.RELEASE")
    testImplementation("org.testcontainers:junit-jupiter:1.17.3")
    testImplementation("org.testcontainers:postgresql:${Dependencies.Version.testContainersVersion}")
}

