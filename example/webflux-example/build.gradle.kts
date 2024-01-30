import com.github.softwareplace.springboot.buildconfiguration.getTag
import com.github.softwareplace.springboot.kotlin.*

plugins {
    id("com.github.softwareplace.springboot.kotlin") version "1.0.0"
    id("com.github.softwareplace.springboot.kotlin-openapi") version "1.0.0"
}

group = "com.webflux.example"

version = "1.0.0"

openApiSettings(OpenApiSettings(reactive = true))

dependencies {
    getTag()
    implementation(project(":security"))
    springWebFlux()
    mappstruct()
    jsonLogger()
    test()
}

