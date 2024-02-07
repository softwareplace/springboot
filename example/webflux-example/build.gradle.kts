import com.github.softwareplace.springboot.kotlin.*
import com.github.softwareplace.springboot.kotlin.openapi.openApiSettings

plugins {
    id("com.github.softwareplace.springboot.kotlin")
    id("com.github.softwareplace.springboot.kotlin-openapi")
}

group = "com.webflux.example"

version = "1.0.0"

openApiSettings {
    reactive = true
    templateDir = "$projectDir/src/main/resources/kotlin-spring"
}

dependencies {
    implementation(project(":security"))
    springWebFlux()
    mappstruct()
    jsonLogger()
    test()
}

