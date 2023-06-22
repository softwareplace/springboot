package com.gradle.kts.build.configuration

fun getProperty(name: String): String {
    val value = System.getProperty(name)
    if (!value.isNullOrBlank()) {
        return value
    }
    throw RuntimeException("$name version not found")
}

object Dependencies {

    object Version {
        val jdk: String by lazy { getProperty("jdkVersion") }
        val glassfishJakarta: String by lazy { getProperty("glassfishJakarta") }
        val loggBack: String by lazy { getProperty("loggBack") }
        val graalvmBuildToolsNative: String by lazy { getProperty("graalvmBuildToolsNativeVersion") }
        val eclipseJettyHttp2Server: String by lazy { getProperty("eclipseJettyHttp2ServerVersion") }
        val passay: String by lazy { getProperty("passayVersion") }
        val jsonwebtoken: String by lazy { getProperty("jsonwebtokenVersion") }
        val auth0JavaJwt: String by lazy { getProperty("auth0JavaJwt") }
        val flywaydb: String by lazy { getProperty("flywaydbVersion") }
        val kotlin: String by lazy { getProperty("kotlinVersion") }
        val jsonLoggerVersion: String by lazy { getProperty("jsonLoggerVersion") }
        val springBootSecurityUtilVersion: String by lazy { getProperty("springBootSecurityUtilVersion") }
        val springBoot: String by lazy { getProperty("springBootVersion") }
        val springBootSecurityTest: String by lazy { getProperty("springBootSecurityTestVersion") }
        val jakartaAnnotation: String by lazy { getProperty("jakartaAnnotationVersion") }
        val jacksonVersion: String by lazy { getProperty("jacksonVersion") }
        val javaxServlet: String by lazy { getProperty("javaxServletVersion") }
        val rxJava: String by lazy { getProperty("rxJavaVersion") }
        val modelMapper: String by lazy { getProperty("modelMapperVersion") }
        val lombokVersion: String by lazy { getProperty("lombokVersion") }
        val retrofit2Version: String by lazy { getProperty("retrofit2Version") }
        val postgresqlVersion: String by lazy { getProperty("postgresqlVersion") }
        val testContainersVersion: String by lazy { getProperty("testContainersVersion") }
        val ioMockkMockk: String by lazy { getProperty("ioMockkMockkVersion") }
        val openapiToolsJacksonDatabindNullable: String by lazy { getProperty("openapitoolsJacksonDatabindNullableVersion") }
        val springdocStarterWebmvc: String by lazy { getProperty("springdocStarterWebmvc") }
        val javaxAnnotationApi: String by lazy { getProperty("javaxAnnotationApiVersion") }
        val javaxValidationApi: String by lazy { getProperty("javaxValidationApiVersion") }
        val swaggerAnnotation: String by lazy { getProperty("swaggerAnnotationVersion") }
        val springDocVersion: String by lazy { getProperty("springDocVersion") }
        val springRstDocsMockMVC: String by lazy { getProperty("springRstDocsMockMVCVersion") }
        val mockitoKotlin: String by lazy { getProperty("mockitoKotlinVersion") }
        val jUnitJupiter: String by lazy { getProperty("jUnitJupiterVersion") }
        val openapiTools: String by lazy { getProperty("openapitoolsVersion") }
        val benManesCaffeine: String by lazy { getProperty("benManesCaffeine") }
        val mapStruct: String by lazy { getProperty("mapStruct") }
        val slf4jApiVersion: String by lazy { getProperty("slf4jApiVersion") }
    }

    object Group {
        const val orgJetbrainsKotlin = "org.jetbrains.kotlin"
        const val orgJetbrainsKotlinSpring = "org.jetbrains.kotlin.plugin.spring"
        const val orgJetbrainsKotlinJpa = "org.jetbrains.kotlin.plugin.jpa"
    }

    object Module {
        const val orgJetbrainsKotlinPluginSpring = "org.jetbrains.kotlin.plugin.spring.gradle.plugin"
        const val orgJetbrainsKotlinPluginJpa = "org.jetbrains.kotlin.plugin.jpa.gradle.plugin"
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
