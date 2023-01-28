package com.gradle.kts.build.configuration

object Dependencies {

    object Version {
        const val jetbrainsKotlinSpring = "1.7.22"
        const val kotlin = "1.7.22"
        const val springDependencyManagement = "1.0.11"
        const val comGradleKtsConfiguration = "1.0.0"
        const val comGradleKtsSource = "1.0.0"
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
        const val comGradleKtsConfiguration = "com.gradle.kts.build.configuration"
        const val comGradleKtsSource = "com.gradle.kts.build.source"
    }

    object TargetLib {
        const val buildConfiguration = "build-configuration"
        const val buildSource = "build-source"
        const val springBootStarter = "spring-boot-starter"
        const val springBootStarterTest = "spring-boot-starter-test"
        const val kotlinReflect = "kotlin-reflect"
        const val kotlinStdlibJdk8 = "kotlin-stdlib-jdk8"
    }

    fun buildDependency(domain: String, targetLig: String): String {
        return "$domain:$targetLig"
    }

    fun buildDependency(domain: String, targetLig: String, version: String): String {
        return "$domain:$targetLig:$version"
    }
}
