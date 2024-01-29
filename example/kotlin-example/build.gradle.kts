import com.github.softwareplace.springboot.kotlin.*

plugins {
    id("com.github.softwareplace.springboot.kotlin") version "v1.0.0-SNAPSHOT"
    id("com.github.softwareplace.springboot.kotlin-openapi") version "v1.0.0-SNAPSHOT"
}

group = "com.kotlin.example.openapi"
version = "v1.0.0-SNAPSHOT"

dependencies {
    implementation(project(":security"))
    kotlinReactive()
    springJettyApi()
    mappstruct()
    jsonLogger()
    test()
}

