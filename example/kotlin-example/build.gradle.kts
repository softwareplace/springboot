import com.github.softwareplace.springboot.buildconfiguration.applyGraalvm
import com.github.softwareplace.springboot.kotlin.kotlinMapStruct
import com.github.softwareplace.springboot.kotlin.kotlinReactive
import com.github.softwareplace.springboot.kotlin.openapi.kotlinOpenApiSettings
import com.github.softwareplace.springboot.kotlin.testKotlinMockito
import com.github.softwareplace.springboot.utils.jsonLogger
import com.github.softwareplace.springboot.utils.springBootStartWeb
import com.github.softwareplace.springboot.utils.springBootStarter
import com.github.softwareplace.springboot.utils.springJettyApi

plugins {
    id("com.github.softwareplace.springboot.kotlin")
}

applyGraalvm()

group = "com.kotlin.example.openapi"
version = "1.0.0"

kotlinOpenApiSettings {
    reactive = false
    addCustomFormats = mapOf(
        "user-data" to ("UserData" to "com.example.shared.model")
    )
}

dependencies {
    implementation(project(":security"))
    springBootStartWeb()
    jsonLogger()

    kotlinReactive()
    springBootStarter()
    springJettyApi()
    kotlinMapStruct()

    testKotlinMockito()
}

