import com.gradle.kts.build.source.lombok
import com.gradle.kts.build.source.springDoc
import com.gradle.kts.build.source.springJettyApi
import com.gradle.kts.build.source.test

plugins {
    `java-gradle-plugin`
    id("build-source-plugin")
    id("build-spring-openapi-plugin")
}

group = "com.spring.example.openapi"
version = "1.0.0"

dependencies {
    springJettyApi()
    springDoc()
    lombok()
    test()
}

