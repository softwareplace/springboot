import org.gradle.api.JavaVersion.toVersion
import org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask
import java.io.BufferedReader
import java.io.InputStreamReader

plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
    kotlin("jvm") version System.getProperty("kotlinVersion")
}

val sourceGroup = "com.github.softwareplace.springboot"
val tagVersion = getTag()

group = sourceGroup
version = tagVersion

tasks.getByName<Jar>("jar") {
    archiveClassifier.set("")
}

fun getTag(): String {
    try {
        val versionRequest: String? = project.findProperty("version")?.toString()
        if (!versionRequest.isNullOrBlank() && !versionRequest.equals("unspecified", ignoreCase = true)) {
            return versionRequest
        }

        val process = ProcessBuilder("git", "describe", "--tags", "--abbrev=0")
            .redirectErrorStream(true)
            .start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val tag = reader.readLine()

        if (tag.isNotBlank()) {
            return tag
        }
    } catch (err: Throwable) {
        println("Failed to get ${project.name} version")
    }

    return System.getProperty("pluginsVersion")
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://jitpack.io")
    maven("https://repo.spring.io/milestone")
}

gradlePlugin {
    plugins {
        create("build-configuration") {
            id = "$sourceGroup.build-configuration"
            version = tagVersion
            implementationClass = "$sourceGroup.buildconfiguration.BuildConfigurationPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = "build-configuration"
        }
    }
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.withType<KaptWithoutKotlincTask>().configureEach {
    kaptProcessJvmArgs.add("-Xmx1024m")
}

beforeEvaluate {
    java {
        withJavadocJar()
        withSourcesJar()
        sourceCompatibility = toVersion(System.getProperty("jdkVersion"))
        targetCompatibility = toVersion(System.getProperty("jdkVersion"))

        toolchain {
            languageVersion.set(JavaLanguageVersion.of(System.getProperty("jdkVersion")))
        }
    }
}

afterEvaluate {
    allprojects {
        tasks.withType<KaptWithoutKotlincTask>().configureEach {
            kaptProcessJvmArgs.add("-Xmx1024m")
        }

        java {
            withJavadocJar()
            withSourcesJar()
            sourceCompatibility = toVersion(System.getProperty("jdkVersion"))
            targetCompatibility = toVersion(System.getProperty("jdkVersion"))

            toolchain {
                languageVersion.set(JavaLanguageVersion.of(System.getProperty("jdkVersion")))
            }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${System.getProperty("kotlinVersion")}")
}


