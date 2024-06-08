import com.github.softwareplace.springboot.kotlin.kotlinMapStruct
import com.github.softwareplace.springboot.kotlin.kotlinReactive
import com.github.softwareplace.springboot.kotlin.openapi.kotlinOpenApiSettings
import com.github.softwareplace.springboot.kotlin.testKotlinMockito
import com.github.softwareplace.springboot.utils.jsonLogger
import com.github.softwareplace.springboot.utils.springBootSecurityUtil
import com.github.softwareplace.springboot.utils.springJettyApi
import com.github.softwareplace.springboot.utils.springWebFlux

plugins {
    id("com.github.softwareplace.springboot.kotlin")
}

group = "com.webflux.example"

version = "1.0.0"


kotlinOpenApiSettings {
    reactive = true
    templateDir = "$projectDir/src/main/resources/kotlin-spring"
}

dependencies {
    implementation(project(":security"))
    springBootSecurityUtil("1.0.4")
    kotlinReactive()

    springJettyApi()
    springWebFlux()

    jsonLogger()

    kotlinMapStruct()

    testKotlinMockito()
}
