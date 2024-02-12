import com.github.softwareplace.springboot.kotlin.kotlinReactive
import com.github.softwareplace.springboot.kotlin.mapStructKaptAnnotationProcessor
import com.github.softwareplace.springboot.kotlin.openapi.kotlinOpenapiSettings
import com.github.softwareplace.springboot.kotlin.springBootKaptAnnotationProcessor
import com.github.softwareplace.springboot.kotlin.testKotlinMockito
import com.github.softwareplace.springboot.utils.jsonLogger
import com.github.softwareplace.springboot.utils.mapStruct
import com.github.softwareplace.springboot.utils.springBootStartWeb
import com.github.softwareplace.springboot.utils.springJettyApi

plugins {
    id("com.github.softwareplace.springboot.kotlin")
}

group = "com.kotlin.example.openapi"
version = "1.0.0"

kotlinOpenapiSettings {
    reactive = false
}

dependencies {
    implementation(project(":security"))
    jsonLogger()

    kotlinReactive()

    springJettyApi()
    springBootStartWeb()
    springBootKaptAnnotationProcessor()

    mapStruct()
    mapStructKaptAnnotationProcessor()

    testKotlinMockito()
}

