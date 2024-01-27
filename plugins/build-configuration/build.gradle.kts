import org.gradle.api.JavaVersion.toVersion
import org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask
import java.io.BufferedReader
import java.io.InputStreamReader

plugins {
    `maven-publish`
    `kotlin-dsl`
    kotlin("jvm") version System.getProperty("kotlinVersion")
}

val sourceGroup = "com.github.softwareplace.springboot.plugin"

fun getTag(): String {
    try {
        val versionRequest: String? = project.findProperty("version")?.toString()
        if (!versionRequest.isNullOrBlank() && !versionRequest.equals("unspecified", ignoreCase = true)) {
            println("Current request tag $versionRequest")
            return versionRequest
        }

        val process = ProcessBuilder("git", "describe", "--tags", "--abbrev=0")
            .redirectErrorStream(true)
            .start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val tag = reader.readLine()

        if (tag.isNotBlank()) {
            println("Current app tag $tag")
            return tag
        }
    } catch (err: Throwable) {
        println("Failed to get current tag")
    }

    val tag = System.getProperty("pluginsVersion")
    println("Current default tag $tag")
    return tag
}

group = sourceGroup
version = getTag()

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://jitpack.io")
    maven("https://repo.spring.io/milestone")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = sourceGroup
            artifactId = "build-configuration"
            java.sourceCompatibility = toVersion(System.getProperty("jdkVersion"))
            java.targetCompatibility = toVersion(System.getProperty("jdkVersion"))
            version = getTag()

            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
    }
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
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

gradlePlugin {
    plugins {
        register("build-configuration") {
            id = "com.github.softwareplace.springboot.plugin.build-configuration"
            version = getTag()
            implementationClass = "$sourceGroup.buildconfiguration.BuildConfigurationPlugin"
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${System.getProperty("kotlinVersion")}")
}


