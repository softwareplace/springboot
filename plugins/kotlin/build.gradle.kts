import com.github.softwareplace.springboot.buildconfiguration.Shared
import com.github.softwareplace.springboot.buildconfiguration.implementation
import com.github.softwareplace.springboot.versions.*
import com.github.softwareplace.springboot.versions.Dependencies
import org.springframework.boot.gradle.tasks.run.BootRun
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

plugins {
    `kotlin-dsl`
    id("java")
    id("idea")
    id("eclipse")
    id("signing")
    id("maven-publish")
    id("com.github.softwareplace.springboot.build-configuration")
    id("biz.aQute.bnd.builder") version System.getProperty("bizAQuteBndBuilderVersion")
    id("net.nemerosa.versioning") version System.getProperty("netNemerosaVersioningVersion")
    id("org.ajoberstar.git-publish") version System.getProperty("orgAjoberstarGitPublishVersion")
    id("io.github.gradle-nexus.publish-plugin") version System.getProperty("ioGithubGradleNexusPublishPluginVersion")
    id("org.jetbrains.kotlin.plugin.jpa") version System.getProperty("kotlinVersion")
    id("org.jetbrains.kotlin.plugin.spring") version System.getProperty("kotlinVersion")
    id("org.springframework.boot") version System.getProperty("springBootVersion")
    id("io.spring.dependency-management") version System.getProperty("springDependencyManagementVersion")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val buildTimeAndDate: OffsetDateTime = OffsetDateTime.now()
val buildDate: String = DateTimeFormatter.ISO_LOCAL_DATE.format(buildTimeAndDate)
val buildTime: String = DateTimeFormatter.ofPattern("HH:mm:ss.SSSZ").format(buildTimeAndDate)
val builtByValue: String =
    project.findProperty("builtBy")?.toString() ?: System.getProperty("defaultBuiltBy").toString()

val isSnapshot = project.version.toString().contains("SNAPSHOT")
val docsVersion = if (isSnapshot) "snapshot" else project.version
val docsDir = File(projectDir, "docs")

description = "@Software Place Spring Plugins"
val moduleSourceDir = file("src/module/java")
val sourceGroup = Dependencies.Group.pluginsGroup
val moduleName = "${sourceGroup}.kotlin"

val tagVersion: String by lazy { project.getTag() }

group = sourceGroup
version = tagVersion

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://jitpack.io")
    maven("https://repo.spring.io/milestone")
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
        }
    }
}

tasks {
    compileKotlin {
        compilerOptions {
            freeCompilerArgs.set(
                listOf(
                    "-Xjsr305=strict",
                    "-Xmx1024m",
                    "-Xopt-in=kotlin.RequiresOptIn"
                )
            )
        }
    }

    named<Jar>("bootJar").configure {
        enabled = false
    }

    named<BootRun>("bootRun").configure {
        enabled = false
    }

    withType<ProcessResources> {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    jar {

        fun normalizeVersion(versionLiteral: String): String {
            val regex = Regex("(\\d+\\.\\d+\\.\\d+).*")
            val match = regex.matchEntire(versionLiteral)
            require(match != null) {
                "Version '$versionLiteral' does not match version pattern, e.g. 1.0.0-QUALIFIER"
            }
            return match.groupValues[1]
        }

        manifest {
            attributes(
                "Created-By" to "${System.getProperty("java.version")} (${System.getProperty("java.vendor")} ${
                    System.getProperty(
                        "java.vm.version"
                    )
                })",
                "Kotlin-Version" to kotlinVersion,
                "Built-By" to builtByValue,
                "Build-Date" to buildDate,
                "Build-Time" to buildTime,
                "Build-Revision" to versioning.info.commit,
                "Specification-Title" to project.name,
                "Specification-Version" to normalizeVersion(tagVersion),
                "Specification-Vendor" to sourceGroup,
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to sourceGroup,
                "Bundle-Name" to project.name,
                "Bundle-Description" to project.description,
                "Bundle-DocURL" to "https://github.com/softwareplace/springboot",
                "Bundle-Vendor" to sourceGroup,
                "-exportcontents" to "${sourceGroup}.kotlin",
                "Bundle-SymbolicName" to moduleName
            )
        }
    }

    javadoc {
        (options as StandardJavadocDocletOptions).apply {
            memberLevel = JavadocMemberLevel.PROTECTED
            isAuthor = true
            header = "@Software Place Spring Plugins"
            addStringOption("Xdoclint:html,syntax,reference", "-quiet")
            links("https://docs.oracle.com/en/java/javase/${jdkVersion}/docs/api/")
        }
    }

    withType<Jar>().configureEach {
        from(rootDir) {
            include("LICENSE")
            into("META-INF")
        }
    }
}

gradlePlugin {
    plugins {
        create("kotlin") {
            id = "$sourceGroup.kotlin"
            implementationClass = "$sourceGroup.kotlin.BuildSourcePlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            artifactId = "kotlin"
            from(components["java"])
            Shared.publishConfig(this, sourceGroup, "kotlin")
        }
    }
}

//if (!isSnapshot) {
//    signing {
//        useGpgCmd()
//    }
//}

nexusPublishing {
    packageGroup.set(group.toString())
    repositories {
        sonatype()
    }
}


gitPublish {
    repoUri.set("https://github.com/softwareplace/springboot.git")
    branch.set("main")

    contents {
        from(docsDir)
        into("docs")
    }

    preserve {
        include("**/*")
        exclude("docs/$docsVersion/**")
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation("com.github.softwareplace.springboot:utils:$tagVersion")
    implementation("com.github.softwareplace.springboot:versions:$tagVersion")
    implementation("com.github.softwareplace.springboot:build-configuration:$tagVersion")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}
