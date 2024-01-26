import org.gradle.api.JavaVersion.toVersion

plugins {
    `maven-publish`
    `kotlin-dsl`
    kotlin("jvm") version System.getProperty("kotlinVersion")
}

val sourceGroup = "com.github.softwareplace.plugin"
val currentVersion = System.getProperty("pluginsVersion")

group = sourceGroup
version = currentVersion

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://jitpack.io")
    maven("https://repo.spring.io/milestone")
}

publishing {
    publications {
        create<MavenPublication>("spring-boot-build-configuration") {
            groupId = sourceGroup
            artifactId = "spring-boot-build-configuration"
            version = currentVersion
            java.sourceCompatibility = toVersion(System.getProperty("jdkVersion"))
            java.targetCompatibility = toVersion(System.getProperty("jdkVersion"))

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
        register("spring-boot-build-configuration") {
            id = "spring-boot-build-configuration"
            implementationClass = "$sourceGroup.buildconfiguration.BuildConfigurationPlugin"
            version = currentVersion
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${System.getProperty("kotlinVersion")}")
}


