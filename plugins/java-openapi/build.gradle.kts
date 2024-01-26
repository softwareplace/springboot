import com.github.softwareplace.springboot.plugin.buildconfiguration.Dependencies

plugins {
    `maven-publish`
    `kotlin-dsl`
    id("com.github.softwareplace.springboot.plugin.java")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.github.softwareplace.springboot.plugin"

group = sourceGroup

publishing {
    publications {
        create<MavenPublication>("springBootJavaOpenapiPlugin") {
            groupId = sourceGroup
            artifactId = "java-openapi"
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
        register("java-openapi") {
            id = "com.github.softwareplace.springboot.plugin.java-openapi"
            implementationClass = "$sourceGroup.javaopenapi.OpenApiPlugin"
        }
    }
}

dependencies {
    implementation("com.github.softwareplace.springboot.plugin:build-configuration:${System.getProperty("pluginsVersion")}")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


