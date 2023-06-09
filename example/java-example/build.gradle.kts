import com.gradle.kts.java.buildsource.*

plugins {
    id("java-source-plugin")
    id("java-openapi-plugin")
}

group = "com.spring.example.openapi"
version = "1.0.0"

dependencies {
    implementation(project(":security"))
    springWebFlux()
    jsonLogger()
    springDoc()
    lombok()
    test()
}

