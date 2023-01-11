import com.gradle.kts.build.source.springDoc

plugins {
    `maven-publish`
    `kotlin-dsl`
    kotlin("jvm") version "1.7.22"
    id("build-source-plugin")
    id("org.openapi.generator") version "5.3.0"
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
}

java {
    withJavadocJar()
    withSourcesJar()
}

val sourceGroup = "com.gradle.kts.build.spring.openapi"
group = sourceGroup

version = "1.0.0"

//extra["snippetsDir"] = file("build/generated-snippets")

//sourceSets {
//    val targetPath = "${rootProject.buildDir.path.replace("spring-boot-included-builds/spring-openapi/", "")}/generated/src/main/kotlin"
//    println(targetPath)
//    kotlin.sourceSets["main"].kotlin.srcDir("$targetPath")
//}


gradlePlugin {
    plugins {
        register("build-spring-openapi-plugin") {
            id = "build-spring-openapi-plugin"
            implementationClass = "$sourceGroup.OpenApiPlugin"
        }
    }
}

dependencies {
    springDoc()
}

