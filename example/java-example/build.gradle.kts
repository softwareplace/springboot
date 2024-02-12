import com.github.softwareplace.springboot.java.lombok
import com.github.softwareplace.springboot.java.openapi.javaOpenApiSettings
import com.github.softwareplace.springboot.utils.jsonLogger
import com.github.softwareplace.springboot.utils.springWebFlux
import com.github.softwareplace.springboot.utils.testMockito


plugins {
    id("com.github.softwareplace.springboot.java")
}

group = "com.java.example"

javaOpenApiSettings {
    overrideAllAdditionalModelTypeAnnotations = true
    additionalModelTypeAnnotations = lombokDataBuilder
}

dependencies {
    implementation(project(":security"))
    springWebFlux()

    jsonLogger()
    lombok()

    testMockito()
}

