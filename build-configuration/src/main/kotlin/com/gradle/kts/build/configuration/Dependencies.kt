package com.gradle.kts.build.configuration

object Dependencies {

    object Version {
        const val jetbrainsKotlinSpring = "1.7.22"
        const val kotlin = "1.7.22"
        const val springDependencyManagement = "1.0.11"
        const val comGradleKtsConfiguration = "1.0.0"
        const val buildApplicationPluginVersion = "1.0.0"
        const val jsonLoggerVersion = "0.0.1"
        const val springBootSecurityUtilVersion = "0.0.5"
        const val comGradleKtsSource = "1.0.0"
        const val log4jApiKotlinVersion = "1.2.0"
        const val springBoot = "2.7.2"
        const val springBootSecurityTest = "5.7.6"

        const val springDocVersion = "1.6.9"
        const val jacksonVersion = "2.13.3"
        const val springBootVersion = "2.7.2"
        const val postgreSqlVersion = "42.3.6"
        const val testContainersVersion = "1.17.3"
    }

    object LibDomain {
        const val orgSpringFrameworkBoot = "org.springframework.boot"
        const val orgJetbrainsKotlin = "org.jetbrains.kotlin"
        const val orgJetbrainsKotlinSpring = "org.jetbrains.kotlin.plugin.spring"
        const val orgJetbrainsKotlinJpa = "org.jetbrains.kotlin.plugin.jpa"
        const val orgJetbrainsKotlinDsl = "org.gradle.kotlin-dsl"
        const val orgApacheLogging = "org.apache.logging.log4j"
        const val comGradleKtsConfiguration = "com.gradle.kts.build.configuration"
        const val comGradleKtsSourceApplication = "com.gradle.kts.build.source"
        const val gitHubEliasMeireles = "com.github.eliasmeireles"
        const val comGradleKtsSource = "com.gradle.kts.build.source"
    }

    object TargetLib {
        const val buildConfiguration = "build-configuration"
        const val buildApplicationPlugin = "build-source-application-plugin"
        const val buildSource = "build-source"
        const val orgJetbrainsKotlinPluginSpring = "org.jetbrains.kotlin.plugin.spring.gradle.plugin"
        const val orgJetbrainsKotlinPluginJpa = "org.jetbrains.kotlin.plugin.jpa.gradle.plugin"
        const val orgJetbrainsKotlinDslPlugin = "gradle-kotlin-dsl"
        const val jsonLogger = "json-logger"
        const val springBootSecurityUtil = "spring-boot-security-util"
        const val log4jApiKotlin = "log4j-api-kotlin"
        const val springBootStarter = "spring-boot-starter"
        const val springBootStarterTest = "spring-boot-starter-test"
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
