import com.github.softwareplace.springboot.buildconfiguration.Shared
import com.github.softwareplace.springboot.utils.jsonLogger
import com.github.softwareplace.springboot.utils.springBootStartWeb
import com.github.softwareplace.springboot.utils.submoduleConfig
import com.github.softwareplace.springboot.versions.Dependencies
import com.github.softwareplace.springboot.versions.getTag
import com.github.softwareplace.springboot.versions.jdkVersion
import com.github.softwareplace.springboot.versions.kotlinVersion
import org.springframework.boot.gradle.tasks.run.BootRun
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

plugins {
    `kotlin-dsl`
    id("java")
    id("signing")
    id("maven-publish")
    id("com.github.softwareplace.springboot.kotlin")
    id("biz.aQute.bnd.builder") version System.getProperty("bizAQuteBndBuilderVersion")
    id("net.nemerosa.versioning") version System.getProperty("netNemerosaVersioningVersion")
    id("org.ajoberstar.git-publish") version System.getProperty("orgAjoberstarGitPublishVersion")
    id("io.github.gradle-nexus.publish-plugin") version System.getProperty("ioGithubGradleNexusPublishPluginVersion")
}

val appArtifactId: String = "starter"
val buildTimeAndDate: OffsetDateTime = OffsetDateTime.now()
val buildDate: String = DateTimeFormatter.ISO_LOCAL_DATE.format(buildTimeAndDate)
val buildTime: String = DateTimeFormatter.ofPattern("HH:mm:ss.SSSZ").format(buildTimeAndDate)
val builtByValue: String =
    project.findProperty("builtBy")?.toString() ?: System.getProperty("defaultBuiltBy").toString()

val isSnapshot = project.version.toString().contains("SNAPSHOT")
val docsVersion = if (isSnapshot) "snapshot" else project.version
val docsDir = File(projectDir, "docs")

description = "@Software Place Spring Libs"
val sourceGroup = Dependencies.Group.pluginsGroup
val moduleName = "${sourceGroup}.security"

val tagVersion: String by lazy { project.getTag() }

group = sourceGroup
version = tagVersion

submoduleConfig()
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
                    "-opt-in=kotlin.RequiresOptIn"
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
                "-exportcontents" to "${sourceGroup}.data",
                "Bundle-SymbolicName" to moduleName
            )
        }
    }

    javadoc {
        (options as StandardJavadocDocletOptions).apply {
            memberLevel = JavadocMemberLevel.PROTECTED
            isAuthor = true
            header = "@Software Place Spring Libs"
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


publishing {
    publications {
        create<MavenPublication>(appArtifactId) {
            artifactId = appArtifactId
            from(components["java"])
            Shared.publishConfig(this, sourceGroup, appArtifactId)
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
    springBootStartWeb()
    jsonLogger()
}
