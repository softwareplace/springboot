import com.gradle.kts.build.source.*

plugins {
    id("build-source-plugin")
    id("build-kotlin-spring-openapi-plugin")
}

group = "com.spring.example.openapi"
version = "1.0.0"

dependencies {
    springJettyApi()
    mappstruct()
    jsonLogger()
    springDoc()
    test()
}

