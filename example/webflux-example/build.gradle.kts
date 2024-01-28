import com.github.softwareplace.springboot.kotlinopenapi.OpenApiSettings
import com.github.softwareplace.springboot.kotlinopenapi.openApiSettings
import com.github.softwareplace.springboot.kotlinsubmodule.jsonLogger
import com.github.softwareplace.springboot.kotlinsubmodule.mappstruct
import com.github.softwareplace.springboot.kotlinsubmodule.springWebFlux
import com.github.softwareplace.springboot.kotlinsubmodule.test


plugins {
    id("com.github.softwareplace.springboot.kotlin")
    id("com.github.softwareplace.springboot.kotlin-openapi")
}

group = "com.webflux.example"
version = "1.0.0"

openApiSettings(OpenApiSettings(reactive = true))

dependencies {
    implementation(project(":security"))
    springWebFlux()
    mappstruct()
    jsonLogger()
    test()
}

