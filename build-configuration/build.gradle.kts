plugins {
    `kotlin-dsl`
    id("org.jetbrains.kotlin.jvm") version "1.7.22"
}

val sourceGroup = "com.gradle.kts.build.configuration"
group = sourceGroup
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        register("build-configuration-plugins") {
            id = "build-configuration-plugin"
            implementationClass = "$sourceGroup.BuildConfigurationPlugin"
        }
    }
}


