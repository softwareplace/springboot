import com.github.softwareplace.plugin.kotlinbuildsource.*

plugins {
    id("spring-boot-source-plugin")
    id("spring-boot-openapi-plugin")
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

