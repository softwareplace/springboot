import java.io.BufferedReader
import java.io.InputStreamReader


plugins {
    `maven-publish`
    kotlin("jvm") version System.getProperty("kotlinVersion")
}

val sourceGroup = "com.github.softwareplace.springboot"
group = sourceGroup
version = getTag()

project.findProperty("version")?.toString()?.let {
    if (it.isNotEmpty()) {
        System.setProperty("pluginsVersion", it)
    }
}

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

publishing {
    publications {
        create<MavenPublication>("springBootPlugins") {
            groupId = sourceGroup
            artifactId = "plugin"
            version = getTag()
            java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
            java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://jitpack.io")
    maven("https://repo.spring.io/milestone")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${System.getProperty("kotlinVersion")}")

    implementation("com.github.softwareplace.springboot.plugin:build-configuration:${System.getProperty("pluginsVersion")}")
    implementation("com.github.softwareplace.springboot.plugin:java:${System.getProperty("pluginsVersion")}")
    implementation("com.github.softwareplace.springboot.plugin:java-openapi:${System.getProperty("pluginsVersion")}")
    implementation("com.github.softwareplace.springboot.plugin:kotlin:${System.getProperty("pluginsVersion")}")
    implementation("com.github.softwareplace.springboot.plugin:kotlin-openapi:${System.getProperty("pluginsVersion")}")
}

