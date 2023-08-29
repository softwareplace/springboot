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
        val eclipseJettyHttp2ServerVersion: String by lazy { getProperty("eclipseJettyHttp2ServerVersion") }
        val passayVersion: String by lazy { getProperty("passayVersion") }
        val jsonwebtokenVersion: String by lazy { getProperty("jsonwebtokenVersion") }
        val auth0JavaJwt: String by lazy { getProperty("auth0JavaJwt") }
        val flywaydbVersion: String by lazy { getProperty("flywaydbVersion") }
        val kotlinVersion: String by lazy { getProperty("kotlinVersion") }
        val jsonLoggerVersion: String by lazy { getProperty("jsonLoggerVersion") }
        val springBootSecurityUtilVersion: String by lazy { getProperty("springBootSecurityUtilVersion") }
        val springBootVersion: String by lazy { getProperty("springBootVersion") }
        val springBootSecurityTestVersion: String by lazy { getProperty("springBootSecurityTestVersion") }
        val jakartaAnnotationVersion: String by lazy { getProperty("jakartaAnnotationVersion") }
        val jacksonVersion: String by lazy { getProperty("jacksonVersion") }
        val javaxServletVersion: String by lazy { getProperty("javaxServletVersion") }
        val rxJavaVersion: String by lazy { getProperty("rxJavaVersion") }
        val modelMapperVersion: String by lazy { getProperty("modelMapperVersion") }
        val lombokVersion: String by lazy { getProperty("lombokVersion") }
        val retrofit2Version: String by lazy { getProperty("retrofit2Version") }
        val postgresqlVersion: String by lazy { getProperty("postgresqlVersion") }
        val testContainersVersion: String by lazy { getProperty("testContainersVersion") }
        val ioMockkMockkVersion: String by lazy { getProperty("ioMockkMockkVersion") }
        val openApiToolsJacksonDatabindNullableVersion: String by lazy { getProperty("openApiToolsJacksonDatabindNullableVersion") }
        val springdocStarterWebmvc: String by lazy { getProperty("springdocStarterWebmvc") }
        val javaxAnnotationApiVersion: String by lazy { getProperty("javaxAnnotationApiVersion") }
        val javaxValidationApiVersion: String by lazy { getProperty("javaxValidationApiVersion") }
        val springRstDocsMockMVCVersion: String by lazy { getProperty("springRstDocsMockMVCVersion") }
        val mockitoKotlinVersion: String by lazy { getProperty("mockitoKotlinVersion") }
        val jUnitJupiterVersion: String by lazy { getProperty("jUnitJupiterVersion") }
        val openApiToolsVersion: String by lazy { getProperty("openApiToolsVersion") }
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
        val orgJetbrainsKotlinPluginSpring = "org.jetbrains.kotlin.plugin.spring.gradle.plugin"
        val orgJetbrainsKotlinPluginJpa = "org.jetbrains.kotlin.plugin.jpa.gradle.plugin"
        val kotlinReflect = "kotlin-reflect"
        val kotlinStdlibJdk8 = "kotlin-stdlib-jdk8"
        val kotlinGradlePlugin = "kotlin-gradle-plugin"
    }

    fun buildDependency(domain: String, targetLig: String): String {
        return "$domain:$targetLig"
    }

    fun buildDependency(domain: String, targetLig: String, version: String): String {
        return "$domain:$targetLig:$version"
    }
}
