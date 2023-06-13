import com.gradle.kts.kotlin.buildsource.jsonLogger
import com.gradle.kts.kotlin.buildsource.mappstruct
import com.gradle.kts.kotlin.buildsource.springWebFlux
import com.gradle.kts.kotlin.buildsource.test
import com.gradle.kts.kotlin.openapi.openapiSettings

plugins {
    id("source-plugin")
    id("openapi-plugin")
}

group = "com.webflux.example"
version = "1.0.0"

openapiSettings(reactive = true)

dependencies {
    implementation(project(":security"))
    springWebFlux()
    mappstruct()
    jsonLogger()
    test()
}

