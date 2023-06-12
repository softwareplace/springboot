import com.gradle.kts.java.buildsource.jsonLogger
import com.gradle.kts.java.buildsource.lombok
import com.gradle.kts.java.buildsource.springWebFlux
import com.gradle.kts.java.buildsource.test

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
    lombok()
    test()
}

