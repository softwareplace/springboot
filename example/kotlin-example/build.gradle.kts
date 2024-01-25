import com.github.softwareplace.plugin.kotlinbuildsource.*

plugins {
    id("source-plugin")
    id("openapi-plugin")
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

