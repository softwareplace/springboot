import com.github.softwareplace.plugin.buildconfiguration.Dependencies


plugins {
    `maven-publish`
    `kotlin-dsl`
    id("spring-boot-source-plugin")
    id("org.openapi.generator") version System.getProperty("openApiToolsVersion")
}

val sourceGroup = "com.github.softwareplace.plugin"
val currentVersion: String = System.getProperty("pluginsVersion")

group = sourceGroup
version = currentVersion

publishing {
    publications {
        create<MavenPublication>("springBootOpenapiPlugin") {
            groupId = sourceGroup
            artifactId = "spring-boot-openapi-plugin"
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
        register("spring-boot-openapi-plugin") {
            id = "spring-boot-openapi-plugin"
            implementationClass = "$sourceGroup.kotlinopenapi.OpenApiPlugin"
            version = currentVersion
        }
    }
}

dependencies {
    System.setProperty("kotlin-spring", "${projectDir}/src/main/resources/kotlin-spring")
    implementation("com.github.softwareplace.plugin:spring-boot-source-plugin:${System.getProperty("pluginsVersion")}")
    implementation("com.github.softwareplace.plugin:spring-boot-build-configuration:${System.getProperty("pluginsVersion")}")
    implementation("org.openapitools:openapi-generator-gradle-plugin:${Dependencies.Version.openApiToolsVersion}") {
        exclude("com.fasterxml.jackson.core", "jackson-databind")
    }
}


