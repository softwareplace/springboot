import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

plugins {
    `kotlin-dsl`
    id("java")
    id("eclipse")
    id("idea")
    id("maven-publish")
    id("signing")
    id("biz.aQute.bnd.builder") version "5.3.0"
    id("net.nemerosa.versioning") version "2.14.0"
    id("org.ajoberstar.git-publish") version "3.0.0"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    kotlin("jvm") version System.getProperty("kotlinVersion")
}

val tagVersion: String by lazy {
    try {
        println("[build-configuration] Loading ${project.name} version")
        val versionRequest: String? = findProperty("version")?.toString()
        if (!versionRequest.isNullOrBlank() && !versionRequest.equals("unspecified", ignoreCase = true)) {
            return@lazy versionRequest
        }

        val process = ProcessBuilder("git", "describe", "--tags", "--abbrev=0")
            .redirectErrorStream(true)
            .start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val tag = reader.readLine()

        if (tag.isNotBlank()) {
            return@lazy tag
        }
    } catch (err: Throwable) {
        println("Failed to get ${project.name} version")
    }

    System.getProperty("pluginsVersion")
}

val buildTimeAndDate: OffsetDateTime = OffsetDateTime.now()
val buildDate: String = DateTimeFormatter.ISO_LOCAL_DATE.format(buildTimeAndDate)
val buildTime: String = DateTimeFormatter.ofPattern("HH:mm:ss.SSSZ").format(buildTimeAndDate)
val builtByValue: String =
    project.findProperty("builtBy")?.toString() ?: System.getProperty("default.built.by").toString()

val isSnapshot = project.version.toString().contains("SNAPSHOT")
val docsVersion = if (isSnapshot) "snapshot" else project.version
val docsDir = File(projectDir, "ghpages-docs")

description = "@Software Place Spring Plugins"
val moduleSourceDir = file("src/module/java")
val sourceGroup = System.getProperty("pluginsGroup").toString()
val moduleName = "${sourceGroup}.build-configuration"

group = sourceGroup
version = tagVersion

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://jitpack.io")
    maven("https://repo.spring.io/milestone")
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Xjsr305=strict",
                "-Xmx1024m",
                "-Xopt-in=kotlin.RequiresOptIn"
            )
        }
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
                "-exportcontents" to "${sourceGroup}.buildconfiguration",
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
            links("https://docs.oracle.com/en/java/javase/11/docs/api/")
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
        create("build-configuration") {
            id = "$sourceGroup.build-configuration"
            version = tagVersion
            implementationClass = "$sourceGroup.buildconfiguration.BuildConfigurationPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "build-configuration"
            from(components["java"])
            pom {
                name.set("${project.group}:${project.name}")
                description.set("@Software Place Spring Plugins")
                url.set("https://github.com/softwareplace/springboot")
                scm {
                    connection.set("scm:git:git://github.com/softwareplace/springboot.git")
                    developerConnection.set("scm:git:git://github.com/softwareplace/springboot.git")
                    url.set("https://github.com/softwareplace/springboot")
                }
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("build-configuration")
                        name.set("@Software Place Spring Plugins")
                        email.set("eliasmflilico@gmail.com")
                    }
                }
            }
        }
    }
}

if (!isSnapshot) {
    signing {
        sign(publishing.publications)
    }
}

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
    implementation("org.jetbrains.kotlin:kotlin-reflect:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${System.getProperty("kotlinVersion")}")
}
