plugins {
    `maven-publish`
    kotlin("jvm") version System.getProperty("kotlinVersion")
}

val sourceGroup = "com.github.softwareplace.plugin"
group = sourceGroup

publishing {
    publications {
        create<MavenPublication>("springBootPlugins") {
            groupId = sourceGroup
            artifactId = "spring-boot-plugins"
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
    implementation("com.github.softwareplace.plugin:spring-boot-build-configuration:${System.getProperty("pluginsVersion")}")
    implementation("com.github.softwareplace.plugin:spring-boot-source-kotlin:${System.getProperty("pluginsVersion")}")
    implementation("com.github.softwareplace.plugin:spring-boot-source-java:${System.getProperty("pluginsVersion")}")
    implementation("com.github.softwareplace.plugin:spring-boot-openapi-kotlin:${System.getProperty("pluginsVersion")}")
    implementation("com.github.softwareplace.plugin:spring-boot-openapi-java:${System.getProperty("pluginsVersion")}")
    implementation("com.github.softwareplace.plugin:spring-boot-submodule-source-java:${System.getProperty("pluginsVersion")}")
    implementation("com.github.softwareplace.plugin:spring-boot-submodule-source-kotlin:${System.getProperty("pluginsVersion")}")
}

