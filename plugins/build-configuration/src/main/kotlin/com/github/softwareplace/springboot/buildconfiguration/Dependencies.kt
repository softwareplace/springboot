package com.github.softwareplace.springboot.buildconfiguration


object Dependencies {

    object Version {
        val pluginsVersion: String by lazy { System.getProperty("pluginsVersion", "1.0.0") }
        val jdkVersion: String by lazy { System.getProperty("jdkVersion", "21") }
        val kotlinVersion: String by lazy { System.getProperty("kotlinVersion", "1.9.22") }
        val springBootVersion: String by lazy { System.getProperty("springBootVersion", "3.2.2") }
        val jettyHttp2Server: String by lazy { System.getProperty("jettyHttp2Server", "11.0.18") }
        val springBootSecurityTestVersion: String by lazy {
            System.getProperty(
                "springBootSecurityTestVersion",
                "6.2.1"
            )
        }
        val springdocStarterWebmvc: String by lazy { System.getProperty("springdocStarterWebmvc", "2.3.0") }
        val springRstDocsMockMVCVersion: String by lazy { System.getProperty("springRstDocsMockMVCVersion", "3.0.1") }
        val springDependencyManagementVersion: String by lazy {
            System.getProperty(
                "springDependencyManagementVersion",
                "1.1.4"
            )
        }
        val openApiToolsVersion: String by lazy { System.getProperty("openApiToolsVersion", "7.2.0") }
        val openApiToolsJacksonDatabindNullableVersion: String by lazy {
            System.getProperty(
                "openApiToolsJacksonDatabindNullableVersion",
                "0.2.6"
            )
        }
        val graalvmBuildToolsNativeVersion: String by lazy {
            System.getProperty(
                "graalvmBuildToolsNativeVersion",
                "0.9.28"
            )
        }
        val loggBackVersion: String by lazy { System.getProperty("loggBackVersion", "1.4.14") }
        val passayVersion: String by lazy { System.getProperty("passayVersion", "1.6.4") }
        val jsonWebTokenVersion: String by lazy { System.getProperty("jsonWebTokenVersion", "0.12.3") }
        val auth0JavaJwtVersion: String by lazy { System.getProperty("auth0JavaJwtVersion", "4.4.0") }
        val flywaydbVersion: String by lazy { System.getProperty("flywaydbVersion", "10.6.0") }
        val jsonLoggerVersion: String by lazy { System.getProperty("jsonLoggerVersion", "v0.0.1") }
        val springBootSecurityUtilVersion: String by lazy {
            System.getProperty(
                "springBootSecurityUtilVersion",
                "v0.0.4"
            )
        }
        val logstashLogbackEncoderVersion: String by lazy { System.getProperty("logstashLogbackEncoderVersion", "7.4") }
        val jakartaAnnotationVersion: String by lazy { System.getProperty("jakartaAnnotationVersion", "2.1.1") }
        val jacksonVersion: String by lazy { System.getProperty("jacksonVersion", "2.16.0") }
        val rxJavaVersion: String by lazy { System.getProperty("rxJavaVersion", "3.1.8") }
        val modelMapperVersion: String by lazy { System.getProperty("modelMapperVersion", "3.2.0") }
        val lombokVersion: String by lazy { System.getProperty("lombokVersion", "1.18.30") }
        val retrofit2Version: String by lazy { System.getProperty("retrofit2Version", "2.9.0") }
        val postgresqlVersion: String by lazy { System.getProperty("postgresqlVersion", "42.7.1") }
        val testContainersVersion: String by lazy { System.getProperty("testContainersVersion", "1.19.3") }
        val snakeYamlVersion: String by lazy { System.getProperty("snakeYamlVersion", "2.2") }
        val ioMockkMockkVersion: String by lazy { System.getProperty("ioMockkMockkVersion", "1.13.9") }
        val mockitoKotlinVersion: String by lazy { System.getProperty("mockitoKotlinVersion", "5.2.1") }
        val mockitoVersion: String by lazy { System.getProperty("mockitoVersion", "5.9.0") }
        val jUnitJupiterVersion: String by lazy { System.getProperty("jUnitJupiterVersion", "5.10.1") }
        val benManesCaffeineVersion: String by lazy { System.getProperty("benManesCaffeineVersion", "3.1.8") }
        val mapStructVersion: String by lazy { System.getProperty("mapStructVersion", "1.5.5.Final") }
        val slf4jApiVersion: String by lazy { System.getProperty("slf4jApiVersion", "2.0.11") }
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
