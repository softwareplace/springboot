import com.github.softwareplace.springboot.java.jsonLogger
import com.github.softwareplace.springboot.java.lombok
import com.github.softwareplace.springboot.java.openapi.OpenApiSettings
import com.github.softwareplace.springboot.java.openapi.openApiSettings
import com.github.softwareplace.springboot.java.springWebFlux
import com.github.softwareplace.springboot.java.test


plugins {
    id("com.github.softwareplace.springboot.java")
    id("com.github.softwareplace.springboot.java-openapi")
}

group = "com.java.example"

openApiSettings(OpenApiSettings(additionalModelTypeAnnotations = listOf("@lombok.Data", "@lombok.Builder")))

dependencies {
    implementation(project(":security"))
    springWebFlux()
    jsonLogger()
    lombok()
    test()
}

