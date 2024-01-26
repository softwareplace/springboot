import com.github.softwareplace.springboot.plugin.javabuildsource.jsonLogger
import com.github.softwareplace.springboot.plugin.javabuildsource.lombok
import com.github.softwareplace.springboot.plugin.javabuildsource.springWebFlux
import com.github.softwareplace.springboot.plugin.javabuildsource.test
import com.github.softwareplace.springboot.plugin.javaopenapi.OpenApiSettings
import com.github.softwareplace.springboot.plugin.javaopenapi.openApiSettings


plugins {
    id("com.github.softwareplace.springboot.plugin.java")
    id("com.github.softwareplace.springboot.plugin.java-openapi")
}

group = "com.java.example"
version = "1.0.0"

openApiSettings(OpenApiSettings(additionalModelTypeAnnotations = listOf("@lombok.Data", "@lombok.Builder")))

dependencies {
    implementation(project(":security"))
    springWebFlux()
    jsonLogger()
    lombok()
    test()
}

