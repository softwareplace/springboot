import com.github.softwareplace.plugin.buildconfiguration.Dependencies

plugins {
    `maven-publish`
    `kotlin-dsl`
    id("java-source-plugin")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.github.softwareplace.plugin.javaopenapi"
val currentVersion: String = System.getProperty("pluginsVersion")

group = sourceGroup
version = currentVersion

publishing {
    publications {
        create<MavenPublication>("javaOpenapiPlugin") {
            groupId = sourceGroup
            artifactId = "java-openapi-plugin"
            version = currentVersion
            java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
            java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))

            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
    }
}

gradlePlugin {
    plugins {
        register("java-openapi-plugin") {
            id = "java-openapi-plugin"
            implementationClass = "$sourceGroup.OpenApiPlugin"
            version = currentVersion
        }
    }
}

dependencies {
    implementation("com.github.softwareplace.plugin.buildconfiguration:build-configuration:1.0.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


