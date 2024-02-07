import com.github.softwareplace.springboot.kotlin.*
import com.github.softwareplace.springboot.kotlin.openapi.openApiSettings

plugins {
    id("com.github.softwareplace.springboot.kotlin")
    id("com.github.softwareplace.springboot.kotlin-openapi")
}

group = "com.kotlin.example.openapi"
version = "1.0.0"

openApiSettings {
    reactive = false
}

dependencies {
    implementation(project(":security"))
    kotlinReactive()
    springJettyApi()
    mappstruct()
    jsonLogger()
    test()
}

