import com.github.softwareplace.springboot.plugin.kotlinbuildsource.jsonLogger
import com.github.softwareplace.springboot.plugin.kotlinbuildsource.mappstruct
import com.github.softwareplace.springboot.plugin.kotlinbuildsource.springWebFlux
import com.github.softwareplace.springboot.plugin.kotlinbuildsource.test
import com.github.softwareplace.springboot.plugin.kotlinopenapi.OpenApiSettings
import com.github.softwareplace.springboot.plugin.kotlinopenapi.openApiSettings


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

