import com.github.softwareplace.plugin.kotlinbuildsource.*

plugins {
    id("com.github.softwareplace.plugin.spring-boot-source-kotlin")
    id("com.github.softwareplace.plugin.spring-boot-openapi-kotlin")
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

