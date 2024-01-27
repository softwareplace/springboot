plugins {
    `maven-publish`
    kotlin("jvm") version System.getProperty("kotlinVersion")
}

val sourceGroup = "com.github.softwareplace.springboot"
group = sourceGroup

project.findProperty("version")?.toString()?.let {
    if (it.isNotEmpty()) {
        System.setProperty("pluginsVersion", it)
    }
}


publishing {
    publications {
        create<MavenPublication>("springBootPlugins") {
            groupId = sourceGroup
            artifactId = "plugin"
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
}

