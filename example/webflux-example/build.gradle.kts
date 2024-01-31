import com.github.softwareplace.springboot.buildconfiguration.getTag
import com.github.softwareplace.springboot.kotlin.*

plugins {
    id("com.github.softwareplace.springboot.kotlin")
    id("com.github.softwareplace.springboot.kotlin-openapi")
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

