import java.io.BufferedReader
import java.io.InputStreamReader


plugins {
    id("maven-publish")
    kotlin("jvm") version System.getProperty("kotlinVersion")
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

val sourceGroup = "com.github.softwareplace.springboot"
val tagVersion = getTag()

group = sourceGroup
version = tagVersion

tasks.getByName<Jar>("jar") {
    archiveClassifier.set("")
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

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://jitpack.io")
    maven("https://repo.spring.io/milestone")
}

publishing {
    publications {
        create<MavenPublication>("plugins") {
            artifactId = "plugins"
            groupId = sourceGroup
            from(components["java"])
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${System.getProperty("kotlinVersion")}")

    implementation("com.github.softwareplace.springboot:kotlin:$tagVersion")
    implementation("com.github.softwareplace.springboot:java:$tagVersion")
}
