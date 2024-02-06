package com.github.softwareplace.springboot.versions

object Dependencies {

    object Version {
        val pluginsVersion: String = System.getProperty("pluginsVersion")
        val jdkVersion: String = System.getProperty("jdkVersion")

        val kotlinVersion: String = System.getProperty("kotlinVersion")
        val springBootVersion: String = System.getProperty("springBootVersion")
        val jettyHttp2Server: String = System.getProperty("jettyHttp2Server")
        val springBootSecurityTestVersion: String = System.getProperty("springBootSecurityTestVersion")

        val springdocStarterWebmvc: String = System.getProperty("springdocStarterWebmvc")
        val springRstDocsMockMVCVersion: String = System.getProperty("springRstDocsMockMVCVersion")
        val springDependencyManagementVersion: String = System.getProperty("springDependencyManagementVersion")

        val openApiToolsVersion: String = System.getProperty("openApiToolsVersion")
        val openApiToolsJacksonDatabindNullableVersion: String =
            System.getProperty("openApiToolsJacksonDatabindNullableVersion")

        val graalvmBuildToolsNativeVersion: String = System.getProperty("graalvmBuildToolsNativeVersion")

        val loggBackVersion: String = System.getProperty("loggBackVersion")

        val passayVersion: String = System.getProperty("passayVersion")
        val jsonWebTokenVersion: String = System.getProperty("jsonWebTokenVersion")
        val auth0JavaJwtVersion: String = System.getProperty("auth0JavaJwtVersion")
        val flywaydbVersion: String = System.getProperty("flywaydbVersion")
        val jsonLoggerVersion: String = System.getProperty("jsonLoggerVersion")
        val springBootSecurityUtilVersion: String = System.getProperty("springBootSecurityUtilVersion")

        val logstashLogbackEncoderVersion: String = System.getProperty("logstashLogbackEncoderVersion")
        val jakartaAnnotationVersion: String = System.getProperty("jakartaAnnotationVersion")
        val jacksonVersion: String = System.getProperty("jacksonVersion")
        val rxJavaVersion: String = System.getProperty("rxJavaVersion")
        val modelMapperVersion: String = System.getProperty("modelMapperVersion")
        val lombokVersion: String = System.getProperty("lombokVersion")
        val lombokMapstructBinding: String = System.getProperty("lombokMapstructBinding")
        val retrofit2Version: String = System.getProperty("retrofit2Version")
        val postgresqlVersion: String = System.getProperty("postgresqlVersion")
        val testContainersVersion: String = System.getProperty("testContainersVersion")
        val snakeYamlVersion: String = System.getProperty("snakeYamlVersion")
        val ioMockkMockkVersion: String = System.getProperty("ioMockkMockkVersion")
        val mockitoKotlinVersion: String = System.getProperty("mockitoKotlinVersion")
        val mockitoVersion: String = System.getProperty("mockitoVersion")
        val jUnitJupiterVersion: String = System.getProperty("jUnitJupiterVersion")
        val benManesCaffeineVersion: String = System.getProperty("benManesCaffeineVersion")
        val mapStructVersion: String = System.getProperty("mapStructVersion")
        val slf4jApiVersion: String = System.getProperty("slf4jApiVersion")
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
