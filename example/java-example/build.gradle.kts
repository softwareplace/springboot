import com.github.softwareplace.springboot.java.jsonLogger
import com.github.softwareplace.springboot.java.lombok
import com.github.softwareplace.springboot.java.openapi.OpenApiSettings
import com.github.softwareplace.springboot.java.openapi.openApiSettings
import com.github.softwareplace.springboot.java.springWebFlux
import com.github.softwareplace.springboot.java.test


plugins {
    id("com.github.softwareplace.springboot.java") version "1.0.0"
    id("com.github.softwareplace.springboot.java-openapi") version "1.0.0"
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

