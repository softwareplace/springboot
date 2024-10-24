import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

plugins {
    `kotlin-dsl`
    id("java")
    id("maven-publish")
    id("signing")
    id("biz.aQute.bnd.builder") version System.getProperty("bizAQuteBndBuilderVersion")
    id("net.nemerosa.versioning") version System.getProperty("netNemerosaVersioningVersion")
    id("org.ajoberstar.git-publish") version System.getProperty("orgAjoberstarGitPublishVersion")
    id("io.github.gradle-nexus.publish-plugin") version System.getProperty("ioGithubGradleNexusPublishPluginVersion")
    kotlin("jvm") version System.getProperty("kotlinVersion")
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
        }
    }
}

fun loadVersion(): String {
    try {
        val versionRequest: String? = findProperty("version")?.toString()
        if (!versionRequest.isNullOrBlank() && !versionRequest.equals("unspecified", ignoreCase = true)) {
            return versionRequest
        }

        val process = ProcessBuilder("git", "describe", "--tags", "--abbrev=0")
            .redirectErrorStream(true)
            .start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val tag = reader.readLine()

        if (tag.isNotBlank()) {
            return tag
        }
    } catch (err: Throwable) {
        println("Failed to get ${project.name} version")
    }

    return System.getProperty("pluginsVersion")
}

val tagVersion: String by lazy {
    val loadedVersion = loadVersion()
    println("Compiling ${project.name}:${loadedVersion}")
    loadedVersion
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
val sourceGroup = System.getProperty("pluginsGroup").toString()
val moduleName = "${sourceGroup}.versions"

group = sourceGroup
version = tagVersion

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven("https://jitpack.io")
    maven("https://repo.spring.io/milestone")
}

fun updatedPluginVersion() {
    val gradlePropertiesPath = projectDir.path.replace("plugins/versions", "")
    val gradlePropertiesFile = File("${gradlePropertiesPath}gradle.properties")


    if (gradlePropertiesFile.exists()) {
        val properties = Properties().apply {
            FileInputStream(gradlePropertiesFile).use { input ->
                load(input)
            }
        }

        properties.replace("pluginsVersion", tagVersion)

        val buildResourcesDir = File("$projectDir/src/main/resources/")
        buildResourcesDir.mkdirs()
        val updatedPropertiesFile = File("${projectDir}/src/main/resources", "gradle.properties")

        FileOutputStream(updatedPropertiesFile).use { output ->
            properties.store(
                output, """ 
                  ========================================
                  Generated for tag version $tagVersion
                  ========================================
                  Do not edit manually. If you need to override a version specified in the `gradle.properties` file, declare the desired version in the root project's `gradle.properties` file. 
                  For example, if you want to use a different version of `springBootVersion`, add the following line to your root project's `gradle.properties`.
                  ========================================
                 
            """.trimIndent()
            )
        }

        println("gradle.properties updated successfully. Updated file stored in build/resources/gradle.properties")

        generateDependenciesVersion(updatedPropertiesFile)

    } else {
        println("gradle.properties not found.")
    }
}

tasks {
    register("updatedPluginVersion") { updatedPluginVersion() }
    named("assemble") {
        dependsOn("updatedPluginVersion")
    }
    compileKotlin {
        dependsOn("updatedPluginVersion")
        compilerOptions {
            freeCompilerArgs.set(
                listOf(
                "-Xjsr305=strict",
                    "-opt-in=kotlin.RequiresOptIn"
                )
            )
        }
    }

    withType<ProcessResources> {
        dependsOn("updatedPluginVersion")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    withType<Jar> {
        dependsOn("updatedPluginVersion")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    jar {
        dependsOn("updatedPluginVersion")
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
                "Kotlin-Version" to System.getProperty("kotlinVersion"),
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
                "-exportcontents" to "${sourceGroup}.versions",
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
        create("versions") {
            id = "$sourceGroup.versions"
            version = tagVersion
            implementationClass = "$sourceGroup.versions.LoadProperties"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("versions") {
            artifactId = "versions"
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
    implementation("org.jetbrains.kotlin:kotlin-reflect:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${System.getProperty("kotlinVersion")}")
}


fun generateDependenciesVersion(gradlePropertiesFile: File) {
    val ignoringValue = listOf(
        "releaseBranch",
        "defaultBuiltBy",
        "pluginsGroup"
    )

    val dependenceTemplateFile = File("${projectDir.path}/template/Dependencies.mustache")
    val dependenceTargetFile =
        File("${projectDir.path}/src/main/kotlin/com/github/softwareplace/springboot/versions/Dependencies.kt")

    fun formatKey(key: String): String {
        val value = key.split(".", "_")
            .joinToString("") { it -> it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }
        return value.substring(0, 1).lowercase() + value.substring(1)
    }


    val properties = gradlePropertiesFile.readLines()
        .filter { it.isNotBlank() && !it.startsWith("#") }
        .associate {
            val (key, value) = it.split("=")
            key.trim() to value.trim()
        }

    var updatedTemplate = ""


    val template = "val Project.keyVar: String\n" +
            "    get() = properties.getOrDefault(\"keyVar\", \"keyVersion\") as String\n\n"

    properties.forEach { (key, value) ->
        if (!ignoringValue.contains(key)) {
            if ("pluginsVersion".equals(key, ignoreCase = true)) {
                println("PLUGIN:VERSION: Applying version $tagVersion to $key")
                updatedTemplate += template.replace("keyVar", key)
                    .replace("keyVersion", tagVersion)
            } else {
                val formattedValue = value.replace("\"", "\\\"")
                val formattedKey = formatKey(key)
                updatedTemplate += template.replace("keyVar", formattedKey)
                    .replace("keyVersion", formattedValue)
            }
        }
    }
    val templateContent = dependenceTemplateFile.readText()
        .replace("{{dependenciesVersion}}", updatedTemplate)
        .replace(
            "{{comment}}", """
          ** ========================================
          ** Generated for tag version $tagVersion
          ** ========================================
          ** Do not edit manually.
        """.trimIndent()
        )

    dependenceTargetFile.writeText(templateContent)

    println("Template updated successfully.")
}
