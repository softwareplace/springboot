import java.io.BufferedReader
import java.io.InputStreamReader


plugins {
    id("maven-publish")
    kotlin("jvm") version System.getProperty("kotlinVersion")
}

val sourceGroup = "com.github.softwareplace"
group = sourceGroup
version = getTag()

tasks.getByName<Jar>("jar") {
    archiveClassifier.set("")
}

project.findProperty("version")?.toString()?.let {
    if (it.isNotEmpty()) {
        System.setProperty("pluginsVersion", it)
    }
}

java {
    withJavadocJar()
    withSourcesJar()
    sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
    targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(System.getProperty("jdkVersion")))
    }
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = sourceGroup
            artifactId = "springboot"
            version = getTag()
            java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
            java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
            from(components["java"])
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${System.getProperty("kotlinVersion")}")
}

