import com.github.softwareplace.plugin.buildconfiguration.Dependencies
import com.github.softwareplace.plugin.buildconfiguration.ORG_SPRINGFRAMEWORK_BOOT
import com.github.softwareplace.plugin.buildconfiguration.implementation
import com.github.softwareplace.plugin.buildconfiguration.kotlinDeps
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
    id("com.github.softwareplace.plugin.spring-boot-build-configuration")
    id("org.jetbrains.kotlin.plugin.jpa") version System.getProperty("kotlinVersion")
//    id("org.graalvm.buildtools.native") version System.getProperty("graalvmBuildToolsNativeVersion")
    id("org.jetbrains.kotlin.plugin.spring") version System.getProperty("kotlinVersion")
    id("org.springframework.boot") version System.getProperty("springBootVersion")
    id("io.spring.dependency-management") version System.getProperty("springDependencyManagementVersion")
}

java {
    withJavadocJar()
    withSourcesJar()
}

allprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == ORG_SPRINGFRAMEWORK_BOOT) {
                useVersion(System.getProperty("springBootVersion"))
            }
        }
    }
}

val sourceGroup = "com.github.softwareplace.plugin"

group = sourceGroup

tasks.named<Jar>("bootJar").configure {
    enabled = false
}

tasks.named<BootRun>("bootRun").configure {
    enabled = false
}

gradlePlugin {
    plugins {
        register("spring-boot-java-source-plugin") {
            id = "com.github.softwareplace.plugin.spring-boot-java-source-plugin"
            implementationClass = "$sourceGroup.javabuildsource.BuildSourcePlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("springBootJavaSourcePlugin") {
            groupId = sourceGroup
            artifactId = "spring-boot-java-source-plugin"
            java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
            java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))

            from(components["java"])
        }
    }
}

dependencies {
    kotlinDeps()
    implementation("$ORG_SPRINGFRAMEWORK_BOOT:spring-boot-gradle-plugin:${Dependencies.Version.springBootVersion}")
//    implementation("org.graalvm.buildtools:native-gradle-plugin:${Dependencies.Version.graalvmBuildToolsNativeVersion}")
    implementation("com.github.softwareplace.plugin:spring-boot-build-configuration:${System.getProperty("pluginsVersion")}")
}

