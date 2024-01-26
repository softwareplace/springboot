import com.github.softwareplace.plugin.kotlinbuildsource.jsonLogger
import com.github.softwareplace.plugin.kotlinbuildsource.mappstruct
import com.github.softwareplace.plugin.kotlinbuildsource.springWebFlux
import com.github.softwareplace.plugin.kotlinbuildsource.test
import com.github.softwareplace.plugin.kotlinopenapi.OpenApiSettings
import com.github.softwareplace.plugin.kotlinopenapi.openApiSettings


plugins {
    id("spring-boot-source-plugin")
    id("spring-boot-openapi-plugin")
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

