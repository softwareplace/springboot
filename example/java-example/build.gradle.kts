import com.github.softwareplace.springboot.java.javaMapStruct
import com.github.softwareplace.springboot.java.lombok
import com.github.softwareplace.springboot.java.openapi.javaOpenApiSettings
import com.github.softwareplace.springboot.java.testJavaMockito
import com.github.softwareplace.springboot.utils.jsonLogger
import com.github.softwareplace.springboot.utils.springWebFlux


plugins {
    id("com.github.softwareplace.springboot.java")
}

group = "com.java.example"

javaOpenApiSettings()

dependencies {
    implementation(project(":security"))
    springWebFlux()
    jsonLogger()
    lombok()
    javaMapStruct()
    testJavaMockito()
}

