import com.gradle.kts.build.source.mappstruct
import com.gradle.kts.build.source.springDoc
import com.gradle.kts.build.source.springJettyApi
import com.gradle.kts.build.source.test

plugins {
    id("build-source-plugin")
    id("build-kotlin-spring-openapi-plugin")
}

group = "com.spring.example.openapi"
version = "1.0.0"

dependencies {
    springJettyApi()
    mappstruct()
    springDoc()
    test()
}

