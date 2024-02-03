package com.github.softwareplace.springboot.versions

object Dependencies {

    object Version {
        val pluginsVersion: String = System.getProperty("pluginsVersion", "1.0.0")
        val jdkVersion: String = System.getProperty("jdkVersion", "21")

        val kotlinVersion: String = System.getProperty("kotlinVersion", "1.9.22")
        val springBootVersion: String = System.getProperty("springBootVersion", "3.2.2")
        val jettyHttp2Server: String = System.getProperty("jettyHttp2Server", "11.0.18")
        val springBootSecurityTestVersion: String = System.getProperty(
            "springBootSecurityTestVersion",
            "6.2.1"
        )

        val springdocStarterWebmvc: String = System.getProperty("springdocStarterWebmvc", "2.3.0")
        val springRstDocsMockMVCVersion: String = System.getProperty("springRstDocsMockMVCVersion", "3.0.1")
        val springDependencyManagementVersion: String = System.getProperty(
            "springDependencyManagementVersion",
            "1.1.4"
        )

        val openApiToolsVersion: String = System.getProperty("openApiToolsVersion", "7.2.0")
        val openApiToolsJacksonDatabindNullableVersion: String = System.getProperty(
            "openApiToolsJacksonDatabindNullableVersion",
            "0.2.6"
        )

        val graalvmBuildToolsNativeVersion: String = System.getProperty("graalvmBuildToolsNativeVersion", "0.9.28")

        val loggBackVersion: String = System.getProperty("loggBackVersion", "1.4.14")

        val passayVersion: String = System.getProperty("passayVersion", "1.6.4")
        val jsonWebTokenVersion: String = System.getProperty("jsonWebTokenVersion", "0.12.3")
        val auth0JavaJwtVersion: String = System.getProperty("auth0JavaJwtVersion", "4.4.0")
        val flywaydbVersion: String = System.getProperty("flywaydbVersion", "10.6.0")
        val jsonLoggerVersion: String = System.getProperty("jsonLoggerVersion", "v0.0.1")
        val springBootSecurityUtilVersion: String = System.getProperty(
            "springBootSecurityUtilVersion",
            "v0.0.4"
        )

        val logstashLogbackEncoderVersion: String = System.getProperty("logstashLogbackEncoderVersion", "7.4")
        val jakartaAnnotationVersion: String = System.getProperty("jakartaAnnotationVersion", "2.1.1")
        val jacksonVersion: String = System.getProperty("jacksonVersion", "2.16.0")
        val rxJavaVersion: String = System.getProperty("rxJavaVersion", "3.1.8")
        val modelMapperVersion: String = System.getProperty("modelMapperVersion", "3.2.0")
        val lombokVersion: String = System.getProperty("lombokVersion", "1.18.30")
        val lombokMapstructBinding: String = System.getProperty("lombokMapstructBinding", "0.2.0")
        val retrofit2Version: String = System.getProperty("retrofit2Version", "2.9.0")
        val postgresqlVersion: String = System.getProperty("postgresqlVersion", "42.7.1")
        val testContainersVersion: String = System.getProperty("testContainersVersion", "1.19.3")
        val snakeYamlVersion: String = System.getProperty("snakeYamlVersion", "2.2")
        val ioMockkMockkVersion: String = System.getProperty("ioMockkMockkVersion", "1.13.9")
        val mockitoKotlinVersion: String = System.getProperty("mockitoKotlinVersion", "5.2.1")
        val mockitoVersion: String = System.getProperty("mockitoVersion", "5.9.0")
        val jUnitJupiterVersion: String = System.getProperty("jUnitJupiterVersion", "5.10.1")
        val benManesCaffeineVersion: String = System.getProperty("benManesCaffeineVersion", "3.1.8")
        val mapStructVersion: String = System.getProperty("mapStructVersion", "1.5.5.Final")
        val slf4jApiVersion: String = System.getProperty("slf4jApiVersion", "2.0.11")
    }

    object Group {
        val pluginsGroup: String = "com.github.softwareplace.springboot"
        val orgJetbrainsKotlin = "org.jetbrains.kotlin"
        val orgJetbrainsKotlinSpring = "org.jetbrains.kotlin.plugin.spring"
        val orgJetbrainsKotlinJpa = "org.jetbrains.kotlin.plugin.jpa"
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
