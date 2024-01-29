import com.github.softwareplace.springboot.kotlin.*

plugins {
    id("com.github.softwareplace.springboot.kotlin") version "v1.0.0-SNAPSHOT"
    id("com.github.softwareplace.springboot.kotlin-openapi") version "v1.0.0-SNAPSHOT"
}

group = "com.webflux.example"

version = "v1.0.0-SNAPSHOT"

openApiSettings(OpenApiSettings(reactive = true))

dependencies {
    implementation(project(":security"))
    springWebFlux()
    mappstruct()
    jsonLogger()
    test()
}

