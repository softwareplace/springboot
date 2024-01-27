import com.github.softwareplace.springboot.plugin.buildconfiguration.implementation
import com.github.softwareplace.springboot.plugin.kotlin.*


plugins {
    id("com.github.softwareplace.springboot.plugin.kotlin")
    id("com.github.softwareplace.springboot.plugin.kotlin-openapi")
}

group = "com.kotlin.example.openapi"
version = "1.0.0"

dependencies {
    implementation(project(":security"))
    kotlinReactive()
    springJettyApi()
    mappstruct()
    jsonLogger()
    test()
}

