package com.gradle.kts.build.configuration

fun getProperty(name: String, default: () -> String): String {
    val value = System.getProperty(name)
    if (!value.isNullOrBlank()) {
        return value
    }
    return default()
}

object Dependencies {

    object Version {
        val eclipseJettyHttp2Server: String by lazy { getProperty("eclipseJettyHttp2ServerVersion") { "9.4.36.v20210114" } }
        val passay: String by lazy { getProperty("passayVersion") { "1.6.1" } }
        val jsonwebtoken: String by lazy { getProperty("jsonwebtokenVersion") { "0.9.1" } }
        val flywaydb: String by lazy { getProperty("flywaydbVersion") { "8.3.0" } }
        val kotlin: String by lazy { getProperty("kotlinVersion") { "1.6.21" } }
        val jsonLoggerVersion: String by lazy { getProperty("jsonLoggerVersion") { "0.0.3" } }
        val springBootSecurityUtilVersion: String by lazy { getProperty("springBootSecurityUtilVersion") { "0.0.9" } }
        val springBoot: String by lazy { getProperty("springBootVersion") { "2.7.2" } }
        val springBootSecurityTest: String by lazy { getProperty("springBootSecurityTestVersion") { "5.7.6" } }
        val jakartaAnnotation: String by lazy { getProperty("jakartaAnnotationVersion") { "2.1.1" } }
        val jacksonVersion: String by lazy { getProperty("jacksonVersion") { "2.14.2" } }
        val javaxServlet: String by lazy { getProperty("javaxServletVersion") { "2.5" } }
        val rxJava: String by lazy { getProperty("rxJavaVersion") { "2.2.21" } }
        val lombokVersion: String by lazy { getProperty("lombokVersion") { "1.18.12" } }
        val retrofit2Version: String by lazy { getProperty("retrofit2Version") { "2.9.0" } }
        val postgresqlVersion: String by lazy { getProperty("postgresqlVersion") { "42.3.6" } }
        val testContainersVersion: String by lazy { getProperty("testContainersVersion") { "1.17.3" } }
        val ioMockkMockk: String by lazy { getProperty("ioMockkMockkVersion") { "1.13.2" } }
        val openapitoolsJacksonDatabindNullable: String by lazy { getProperty("openapitoolsJacksonDatabindNullableVersion") { "0.2.4" } }
        val javaxAnnotationApi: String by lazy { getProperty("javaxAnnotationApiVersion") { "1.3.2" } }
        val javaxValidationApi: String by lazy { getProperty("javaxValidationApiVersion") { "2.0.1.Final" } }
        val swaggerAnnotation: String by lazy { getProperty("swaggerAnnotationVersion") { "1.6.6" } }
        val springDocVersion: String by lazy { getProperty("springDocVersion") { "1.6.9" } }
        val springRstDocsMockMVC: String by lazy { getProperty("springRstDocsMockMVCVersion") { "2.0.6.RELEASE" } }
        val mockitoKotlin: String by lazy { getProperty("mockitoKotlinVersion") { "4.1.0" } }
        val jUnitJupiter: String by lazy { getProperty("jUnitJupiterVersion") { "5.9.0" } }
        val openapitools: String by lazy { getProperty("openapitoolsVersion") { "6.3.0" } }
    }

    object Group {
        const val orgJetbrainsKotlin = "org.jetbrains.kotlin"
        const val orgJetbrainsKotlinSpring = "org.jetbrains.kotlin.plugin.spring"
        const val orgJetbrainsKotlinJpa = "org.jetbrains.kotlin.plugin.jpa"
        const val orgApacheLogging = "org.apache.logging.log4j"
        const val gitHubEliasMeireles = "com.github.eliasmeireles"
    }

    object Module {
        const val orgJetbrainsKotlinPluginSpring = "org.jetbrains.kotlin.plugin.spring.gradle.plugin"
        const val orgJetbrainsKotlinPluginJpa = "org.jetbrains.kotlin.plugin.jpa.gradle.plugin"
        const val jsonLogger = "json-logger"
        const val springBootSecurityUtil = "spring-boot-security-util"
        const val log4jApiKotlin = "log4j-api-kotlin"
        const val kotlinReflect = "kotlin-reflect"
        const val kotlinStdlibJdk8 = "kotlin-stdlib-jdk8"
        const val kotlinGradlePlugin = "kotlin-gradle-plugin"
    }

    fun buildDependency(domain: String, targetLig: String): String {
        return "$domain:$targetLig"
    }

    fun buildDependency(domain: String, targetLig: String, version: String): String {
        return "$domain:$targetLig:$version"
    }
}
