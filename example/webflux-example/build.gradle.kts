import com.github.softwareplace.springboot.plugin.kotlinopenapi.OpenApiSettings
import com.github.softwareplace.springboot.plugin.kotlinopenapi.openApiSettings
import com.github.softwareplace.springboot.plugin.kotlinsubmodule.jsonLogger
import com.github.softwareplace.springboot.plugin.kotlinsubmodule.mappstruct
import com.github.softwareplace.springboot.plugin.kotlinsubmodule.springWebFlux
import com.github.softwareplace.springboot.plugin.kotlinsubmodule.test


plugins {
    id("com.github.softwareplace.springboot.plugin.kotlin")
    id("com.github.softwareplace.springboot.plugin.kotlin-openapi")
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

